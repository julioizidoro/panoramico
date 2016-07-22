/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.ProprietarioDao;
import br.com.panoramico.model.Plano;
import br.com.panoramico.model.Proprietario;
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
 * @author Kamilla Rodrigues
 */

@Named
@ViewScoped
public class ProprietarioMB implements Serializable{
    
    private List<Proprietario> listaProprietario;
    private Proprietario proprietario;
    @EJB
    private ProprietarioDao proprietarioDao;
    
    @PostConstruct
    public void init(){
        gerarListaProprietario();
    }

    public List<Proprietario> getListaProprietario() {
        return listaProprietario;
    }

    public void setListaProprietario(List<Proprietario> listaProprietario) {
        this.listaProprietario = listaProprietario;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public ProprietarioDao getProprietarioDao() {
        return proprietarioDao;
    }

    public void setProprietarioDao(ProprietarioDao proprietarioDao) {
        this.proprietarioDao = proprietarioDao;
    }

    
    
    public void gerarListaProprietario() {
        listaProprietario = proprietarioDao.list("Select p from Proprietario p");
        if (listaProprietario == null) {
            listaProprietario = new ArrayList<Proprietario>();
        }
    }
    
    
    public String novoCadastroProprietario() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 570);
        RequestContext.getCurrentInstance().openDialog("cadProprietario", options, null);
        return "";
    }
    
    
    public void retornoDialogNovo(SelectEvent event){
        Proprietario proprietario = (Proprietario) event.getObject();
        if (proprietario.getIdproprietario()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de Proprietario realizado com sucesso");
        }
        gerarListaProprietario();
    }
    
    public void retornoDialogAlteracao(SelectEvent event){
        Proprietario proprietario = (Proprietario) event.getObject();
        if (proprietario.getIdproprietario()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração de Proprietario realizado com sucesso");
        }
        gerarListaProprietario();
    }
    
    
    public void editar(Proprietario proprietario){
        if (proprietario != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("proprietario", proprietario);
            options.put("contentWidth", 570);
            RequestContext.getCurrentInstance().openDialog("cadProprietario", options, null);
        }
    }
    
    
    public void excluir(Proprietario proprietario){
        proprietarioDao.remove(proprietario.getIdproprietario());
        Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
        gerarListaProprietario();
    }
}
