/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.EventoDao;
import br.com.panoramico.dao.TipoEventoDao;
import br.com.panoramico.model.Evento;
import br.com.panoramico.model.Tipoenvento;
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
public class TipoEventoMB implements Serializable{
    
    @EJB
    private TipoEventoDao tipoEventoDao;
    private Tipoenvento tipoEnvento;
    private List<Tipoenvento> listaTipoEvento;
    @EJB
    private EventoDao eventoDao;
    
    
    @PostConstruct
    public void init(){
        gerarListaTipoEvento();
    }

    public TipoEventoDao getTipoEventoDao() {
        return tipoEventoDao;
    }

    public void setTipoEventoDao(TipoEventoDao tipoEventoDao) {
        this.tipoEventoDao = tipoEventoDao;
    }

    public Tipoenvento getTipoEnvento() {
        return tipoEnvento;
    }

    public void setTipoEnvento(Tipoenvento tipoEnvento) {
        this.tipoEnvento = tipoEnvento;
    }

    public List<Tipoenvento> getListaTipoEvento() {
        return listaTipoEvento;
    }

    public void setListaTipoEvento(List<Tipoenvento> listaTipoEvento) {
        this.listaTipoEvento = listaTipoEvento;
    }
    
    
    
    
    public void gerarListaTipoEvento(){
        listaTipoEvento = tipoEventoDao.list("Select t from Tipoenvento t");
        if (listaTipoEvento == null) {
            listaTipoEvento = new ArrayList<Tipoenvento>();
        }
    }
    
    public String novoCadastroTipoEvento() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 400);
        RequestContext.getCurrentInstance().openDialog("cadTipoEvento", options, null);
        return "";
    }
    
    
    public void retornoDialogNovo(SelectEvent event){
        Tipoenvento tipoenvento = (Tipoenvento) event.getObject();
        if (tipoenvento.getIdtipoenvento()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de cliente realizado com sucesso");
        }
        gerarListaTipoEvento();
    }
    
    public void retornoDialogAlteracao(SelectEvent event){
        Tipoenvento tipoenvento = (Tipoenvento) event.getObject();
        if (tipoenvento.getIdtipoenvento()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração de cliente realizado com sucesso");
        }
        gerarListaTipoEvento();
    }
    
    
    public void editar(Tipoenvento tipoenvento){
        if (tipoenvento != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("tipoenvento", tipoenvento);
            options.put("contentWidth", 400);
            RequestContext.getCurrentInstance().openDialog("cadTipoEvento", options, null);
        }
    }
    
    
    public void excluir(Tipoenvento tipoenvento){
        List<Evento> listaEvento = eventoDao.list("Select e From Evento e Where e.tipoenvento.idtipoenvento=" + tipoenvento.getIdtipoenvento());
        if (listaEvento == null || listaEvento.isEmpty()) {
            tipoEventoDao.remove(tipoenvento.getIdtipoenvento());
            Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
            gerarListaTipoEvento();
        }else{
            Mensagem.lancarMensagemInfo("Atenção", " este tipo de evento não pode ser excluido");
        }
    }
}
