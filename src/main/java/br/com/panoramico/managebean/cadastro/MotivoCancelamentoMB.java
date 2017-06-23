/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.CCancelamentoDao;
import br.com.panoramico.dao.MotivoCancelamentoDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Ccancelamento;
import br.com.panoramico.model.Motivocancelamento;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Anderson
 */

@Named
@ViewScoped
public class MotivoCancelamentoMB implements Serializable{
    
    private Motivocancelamento motivocancelamento;
    private List<Motivocancelamento> listaMotivoCancelamento;
    @EJB
    private MotivoCancelamentoDao motivoCancelamentoDao;
    @EJB
    private CCancelamentoDao ccancelamentoDao;
    
    
    @PostConstruct
    public void init(){
        gerarListaMotivo();
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
    
    
    
    
    public void gerarListaMotivo(){
        listaMotivoCancelamento = motivoCancelamentoDao.list("Select m From Motivocancelamento m");
        if (listaMotivoCancelamento == null) {
            listaMotivoCancelamento = new ArrayList<>();
        }
    }
    
    
    
     public String novoCadastroMotivoCancelamento() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 400);
        RequestContext.getCurrentInstance().openDialog("cadMotivoCancelamento", options, null);
        return "";
    }
    
    
    public void retornoDialogNovo(SelectEvent event){
        Motivocancelamento motivocancelamento = (Motivocancelamento) event.getObject();
        if (motivocancelamento.getIdmotivocancelamento()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de Motivo Cancelamento realizado com sucesso");
        }
        gerarListaMotivo();
    }
    
     public void retornoDialogAlteracao(SelectEvent event){
        Motivocancelamento motivocancelamento = (Motivocancelamento) event.getObject();
        if (motivocancelamento.getIdmotivocancelamento()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração de motivo cancelamento realizada com sucesso");
        }
        gerarListaMotivo();
    }
    
    
    public void editar(Motivocancelamento motivocancelamento){
        if (motivocancelamento != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("motivocancelamento", motivocancelamento);
            options.put("contentWidth", 400);
            RequestContext.getCurrentInstance().openDialog("cadMotivoCancelamento", options, null);
        }
    }

    
    public void excluir(Motivocancelamento motivocancelamento){
        List<Ccancelamento> lista = ccancelamentoDao.list("Select c From Ccancelamento c Where c.motivocancelamento.idmotivocancelamento="+
                motivocancelamento.getIdmotivocancelamento());
        if (lista == null || lista.isEmpty()) {
            motivoCancelamentoDao.remove(motivocancelamento.getIdmotivocancelamento());
            Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
            gerarListaMotivo();
        }else{
            Mensagem.lancarMensagemInfo("Atenção", " este motivo cancelamento não pode ser excluido");
        }
    }
    
}
