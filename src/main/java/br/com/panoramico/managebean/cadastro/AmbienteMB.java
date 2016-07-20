/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AmbienteDao;
import br.com.panoramico.model.Ambiente;
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
public class AmbienteMB implements  Serializable{
    
    @EJB
    private AmbienteDao ambienteDao;
    private Ambiente ambiente;
    private List<Ambiente> listaAmbiente;
    
    
    @PostConstruct
    public void init(){
        gerarListaAmbiente();
    }

    public AmbienteDao getAmbienteDao() {
        return ambienteDao;
    }

    public void setAmbienteDao(AmbienteDao ambienteDao) {
        this.ambienteDao = ambienteDao;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public List<Ambiente> getListaAmbiente() {
        return listaAmbiente;
    }

    public void setListaAmbiente(List<Ambiente> listaAmbiente) {
        this.listaAmbiente = listaAmbiente;
    }
    
    public void gerarListaAmbiente(){
        listaAmbiente = ambienteDao.list("Select c from Ambiente c");
        if (listaAmbiente == null) {
            listaAmbiente = new ArrayList<Ambiente>();
        }
    }
    
    
    public String novoCadastroAmbiente() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("cadAmbiente", options, null);
        return "";
    }
    
    
    public void retornoDialogNovo(SelectEvent event){
        Ambiente ambiente = (Ambiente) event.getObject();
        if (ambiente.getIdambiente()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de Ambiente realizado com sucesso");
        }
        gerarListaAmbiente();
    }
    
    public void retornoDialogAlteracao(SelectEvent event){
        Ambiente ambiente = (Ambiente) event.getObject();
        if (ambiente.getIdambiente()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração do Ambiente realizado com sucesso");
        }
        gerarListaAmbiente();
    }
    
    
    public void editar(Ambiente ambiente){
        if (ambiente != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("ambiente", ambiente);
            options.put("contentWidth", 600);
            RequestContext.getCurrentInstance().openDialog("cadAmbiente", options, null);
        }
    }
    
    
    public void excluir(Ambiente ambiente){
        ambienteDao.remove(ambiente.getIdambiente());
        Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
        gerarListaAmbiente();
    }
}
