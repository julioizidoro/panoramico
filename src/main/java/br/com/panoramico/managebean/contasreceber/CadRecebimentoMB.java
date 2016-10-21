/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contasreceber;

import br.com.panoramico.dao.BancoDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.RecebimentoDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Banco;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Recebimento;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class CadRecebimentoMB implements Serializable{
    
    private Contasreceber contasreceber;
    private Recebimento recebimento;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Float juros = 0.0f;
    private Float desagio = 0.0f;
    private Float valorTotalRecebido = 0.0f;
    private Float valorReceber = 0.0f;
    @EJB
    private RecebimentoDao recebimentoDao;
    @EJB
    private ContasReceberDao conasReceberDao;
    private Banco banco;
    private List<Banco> listaBanco;
    @EJB
    private BancoDao bancoDao;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasreceber = (Contasreceber) session.getAttribute("contasreceber");
        session.removeAttribute("contasreceber");
        if (recebimento == null) {
            recebimento = new Recebimento();
        }
        if (contasreceber != null) {
            valorTotalRecebido = contasreceber.getValorconta();
            valorReceber = contasreceber.getValorconta();
        }
        gerarListaBanco();
    }

    public RecebimentoDao getRecebimentoDao() {
        return recebimentoDao;
    }

    public void setRecebimentoDao(RecebimentoDao recebimentoDao) {
        this.recebimentoDao = recebimentoDao;
    }

    
    
    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public Recebimento getRecebimento() {
        return recebimento;
    }

    public void setRecebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Float getJuros() {
        return juros;
    }

    public void setJuros(Float juros) {
        this.juros = juros;
    }

    public Float getDesagio() {
        return desagio;
    }

    public void setDesagio(Float desagio) {
        this.desagio = desagio;
    }

    public Float getValorTotalRecebido() {
        return valorTotalRecebido;
    }

    public void setValorTotalRecebido(Float valorTotalRecebido) {
        this.valorTotalRecebido = valorTotalRecebido;
    }

    public Float getValorReceber() {
        return valorReceber;
    }

    public void setValorReceber(Float valorReceber) {
        this.valorReceber = valorReceber;
    }

    public ContasReceberDao getConasReceberDao() {
        return conasReceberDao;
    }

    public void setConasReceberDao(ContasReceberDao conasReceberDao) {
        this.conasReceberDao = conasReceberDao;
    }
    
    
    public void calcularTotal(){
        valorTotalRecebido = (valorReceber + juros) - desagio;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Banco> getListaBanco() {
        return listaBanco;
    }

    public void setListaBanco(List<Banco> listaBanco) {
        this.listaBanco = listaBanco;
    }

    public BancoDao getBancoDao() {
        return bancoDao;
    }

    public void setBancoDao(BancoDao bancoDao) {
        this.bancoDao = bancoDao;
    }
    
    
    
    public void salvar(){
        Float totalRecebido = 0.0f;
        boolean dados = validarDados();
        if(dados){
            if (valorTotalRecebido > contasreceber.getValorconta() && juros == 0.0f) {
                Mensagem.lancarMensagemInfo("Atenção", "valor total a receber acima do valor da conta sem constar o valor de juros");
            }else{
                if (valorTotalRecebido >= contasreceber.getValorconta()) {
                    totalRecebido = totalRecebido + valorTotalRecebido;
                    List<Recebimento> listaRecebimento = recebimentoDao.list("Select r from Recebimento r where r.contasreceber.idcontasreceber="+ contasreceber.getIdcontasreceber());
                    for (int i = 0; i < listaRecebimento.size(); i++) {
                        totalRecebido = totalRecebido + listaRecebimento.get(i).getValorrecebido();
                    }
                    contasreceber.setValorconta(totalRecebido);
                    contasreceber.setSituacao("PAGO");
                }else{
                    contasreceber.setValorconta(contasreceber.getValorconta() - valorTotalRecebido);
                }
                conasReceberDao.update(contasreceber);
                recebimento.setBanco(banco);
                recebimento.setJuros(juros);
                recebimento.setDesagio(desagio);
                recebimento.setValorrecebido(valorTotalRecebido);
                recebimento.setUsuario(usuarioLogadoMB.getUsuario());
                recebimento.setContasreceber(contasreceber);
                recebimento = recebimentoDao.update(recebimento);
                RequestContext.getCurrentInstance().closeDialog(recebimento);
            }
        }
    }
    
    public boolean validarDados(){ 
        if(recebimento.getFormarecebimento()==null || recebimento.getFormarecebimento().length()==0){
            Mensagem.lancarMensagemInfo("Atenção", "Forma de Recebimento não informada.");
            return false;
        }
        if(recebimento.getDatarecebimento()==null){
            Mensagem.lancarMensagemInfo("Atenção", "Data não informada.");
            return false;
        }
        if (banco == null || banco.getIdbanco() == null) {
            Mensagem.lancarMensagemInfo("Atenção", "banco não selecionado");
            return false;
        }
        return true;
    }
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Recebimento());
    }
    
    public void gerarListaBanco(){
        listaBanco = bancoDao.list("Select b From Banco b");
        if (listaBanco == null || listaBanco.isEmpty()) {
            listaBanco = new ArrayList<>();
        }
    }
}
