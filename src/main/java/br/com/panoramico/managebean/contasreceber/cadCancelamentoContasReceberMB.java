/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contasreceber;

import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.CrCancelamentoDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Crcancelamento;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class cadCancelamentoContasReceberMB implements Serializable{
    
    @Inject
     private UsuarioLogadoMB usuarioLogadoMB;
    private Contasreceber contasreceber;
    private String mensagem = "";
    private Crcancelamento crcancelamento;
    @EJB
    private CrCancelamentoDao crCanlamentoDao;
    @EJB
    private ContasReceberDao contasReceberDao;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasreceber = (Contasreceber) session.getAttribute("contasreceber");
        session.removeAttribute("contasreceber");
        if (contasreceber == null) {
            Mensagem.lancarMensagemInfo("",  " Conta a receber não selecionada");
            RequestContext.getCurrentInstance().closeDialog(new Crcancelamento());
        }else{
            crcancelamento = new Crcancelamento();
            crcancelamento.setData(new Date());
        }
        retornarHoraAtual();
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }
    

    public Crcancelamento getCrcancelamento() {
        return crcancelamento;
    }

    public void setCrcancelamento(Crcancelamento crcancelamento) {
        this.crcancelamento = crcancelamento;
    }

    public CrCancelamentoDao getCrCanlamentoDao() {
        return crCanlamentoDao;
    }

    public void setCrCanlamentoDao(CrCancelamentoDao crCanlamentoDao) {
        this.crCanlamentoDao = crCanlamentoDao;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Crcancelamento());
    }
    
    public void salvar(){
        crcancelamento.setUsuario(usuarioLogadoMB.getUsuario());
        crcancelamento.setContasreceber(contasreceber);
        String mensagem = validarDados(crcancelamento);
        if (mensagem.length() < 5) {
            crcancelamento = crCanlamentoDao.update(crcancelamento);
            contasreceber.setSituacao("CANCELADO");
            contasReceberDao.update(contasreceber);
            RequestContext.getCurrentInstance().closeDialog(crcancelamento);
        }
    }
    
    public String validarDados(Crcancelamento cancelamento){
        String msg = "";
        if (cancelamento.getMotivo().equalsIgnoreCase("")) {
            msg = msg + " você não informou o motivo do cancelamento \r\n";
        }
        return msg;
    }
    
    public void retornarHoraAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date hora = Calendar.getInstance().getTime();
        crcancelamento.setHora(sdf.format(hora));
    }
}
