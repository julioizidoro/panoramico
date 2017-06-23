/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.CCancelamentoDao;
import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.DependenteDao;
import br.com.panoramico.dao.MotivoCancelamentoDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Ccancelamento;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Crcancelamento;
import br.com.panoramico.model.Motivocancelamento;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Anderson
 */

@Named
@ViewScoped
public class CadCancelamentoClienteMB implements Serializable{
    
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private String mensagem = "";
    private Cliente cliente;
    private Ccancelamento ccancelamento;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private CCancelamentoDao cCancelamentoDao;
    private List<Contasreceber> listaContasReceber;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private DependenteDao dependenteDao;
    @EJB
    private AssociadoDao associadoDao;
    private Motivocancelamento motivocancelamento;
    private List<Motivocancelamento> listaMotivoCancelamento;
    @EJB
    private MotivoCancelamentoDao motivoCancelamentoDao;
    
    
    
    @PostConstruct
    public void init(){
         FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        cliente = (Cliente) session.getAttribute("cliente");
        session.removeAttribute("cliente");
        gerarListaMotivoCancelamento();
        if (cliente == null) {
            Mensagem.lancarMensagemInfo("",  " Cliente não selecionado");
            RequestContext.getCurrentInstance().closeDialog(new Ccancelamento());
        }else{
            ccancelamento = new Ccancelamento();
            ccancelamento.setData(new Date());
        }
        retornarHoraAtual();
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Ccancelamento getCcancelamento() {
        return ccancelamento;
    }

    public void setCcancelamento(Ccancelamento ccancelamento) {
        this.ccancelamento = ccancelamento;
    }

    public List<Contasreceber> getListaContasReceber() {
        return listaContasReceber;
    }

    public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
        this.listaContasReceber = listaContasReceber;
    }

    public Motivocancelamento getMotivocancelamento() {
        return motivocancelamento;
    }

    public void setMotivocancelamento(Motivocancelamento motivocancelamento) {
        this.motivocancelamento = motivocancelamento;
    }

    public List<Motivocancelamento> getListaMotivoCancelamento() {
        return listaMotivoCancelamento;
    }

    public void setListaMotivoCancelamento(List<Motivocancelamento> listaMotivoCancelamento) {
        this.listaMotivoCancelamento = listaMotivoCancelamento;
    }
    
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Ccancelamento());
    }
    
    public void salvar(){
        ccancelamento.setUsuario(usuarioLogadoMB.getUsuario());
        ccancelamento.setCliente(cliente);
        String mensagem = validarDados(ccancelamento);
        if (mensagem.length() < 5) {
            ccancelamento.setMotivocancelamento(motivocancelamento);
            ccancelamento = cCancelamentoDao.update(ccancelamento);
            cliente.setSituacao("Inativo");
            cliente = clienteDao.update(cliente);
            if (cliente.getAssociado() != null) {
                cliente.getAssociado().setSituacao("Inativo");
                associadoDao.update(cliente.getAssociado());
                
                            
                if (cliente.getAssociado().getDependenteList() != null && cliente.getAssociado().getDependenteList().size() > 0) {
                    for (int i = 0; i < cliente.getAssociado().getDependenteList().size(); i++) {
                        cliente.getAssociado().getDependenteList().get(i).setSituacao("Inativo");
                        dependenteDao.update(cliente.getAssociado().getDependenteList().get(i));
                    }
                }  
            }
 
            
            listaContasReceber = contasReceberDao.list("Select c From Contasreceber c Where c.cliente.idcliente=" + cliente.getIdcliente());
            if (listaContasReceber == null) {
                listaContasReceber = new ArrayList<>();
            }
            
            for (int i = 0; i < listaContasReceber.size(); i++) {
               listaContasReceber.get(i).setSituacao("CANCELADO");
               contasReceberDao.update(listaContasReceber.get(i));
            }
            
            RequestContext.getCurrentInstance().closeDialog(ccancelamento);
        }
    }
    
    public String validarDados(Ccancelamento ccancelamento){
        String msg = "";
        if (ccancelamento.getMotivo().equalsIgnoreCase("")) {
            msg = msg + " você não informou o motivo do cancelamento \r\n";
        }
        
        if (motivocancelamento == null) {
            msg = msg + " Selecione o motivo do cancelamento \r\n";
        }
        return msg;
    }
    
    public void retornarHoraAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date hora = Calendar.getInstance().getTime();
        ccancelamento.setHora(sdf.format(hora));
    }
    
    public void gerarListaMotivoCancelamento(){
        listaMotivoCancelamento = motivoCancelamentoDao.list("Select m From Motivocancelamento m");
        if (listaMotivoCancelamento == null) {
            listaMotivoCancelamento = new ArrayList<>();
        }
    }
    
    
    
}
