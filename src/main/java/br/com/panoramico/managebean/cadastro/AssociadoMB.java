/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Cliente;
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
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Julio
 */

@Named
@ViewScoped
public class AssociadoMB implements Serializable{
    
    @EJB
    private AssociadoDao associadoDao;
    private Associado associado;
    private List<Associado> listaAssociado;
    
    
    @PostConstruct
    public void init(){
        gerarListaAssociado();
    }

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public List<Associado> getListaAssociado() {
        return listaAssociado;
    }

    public void setListaAssociado(List<Associado> listaAssociado) {
        this.listaAssociado = listaAssociado;
    }
    
    
    
    public void gerarListaAssociado(){
        listaAssociado = associadoDao.list("Select a from Associado a");
        if (listaAssociado == null) {
            listaAssociado = new ArrayList<Associado>();
        }
    }
    
    
    public String novoCadastroAssociado() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("cadAssociado", options, null);
        return "";
    }
    
    
    public void retornoDialogNovo(SelectEvent event){
        Associado associado = (Associado) event.getObject();
        if (associado.getIdassociado()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de Associado realizado com sucesso");
        }
        gerarListaAssociado();
    }
    
    
    public void editar(Associado associado){
        if (associado != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("associado", associado);
            options.put("contentWidth", 600);
            RequestContext.getCurrentInstance().openDialog("cadAssociado", options, null);
        }
    }
    
    
    public void associarEmpresa(Associado associado){
        if (associado != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("associado", associado);
            options.put("contentWidth", 600);
            RequestContext.getCurrentInstance().openDialog("cadAssociadoEmpresa", options, null);
        }
    }
    
    
}
