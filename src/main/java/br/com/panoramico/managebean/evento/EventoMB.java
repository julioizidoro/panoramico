/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.evento;

import br.com.panoramico.dao.EventoDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Evento;
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
 * @author Kamilla Rodrigues
 */

@Named
@ViewScoped
public class EventoMB implements Serializable{
    
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Evento evento;
    private List<Evento> listaEvento;
    @EJB
    private EventoDao eventoDao;
    
    
    @PostConstruct
    public void init(){
        gerarListaEventos();
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<Evento> getListaEvento() {
        return listaEvento;
    }

    public void setListaEvento(List<Evento> listaEvento) {
        this.listaEvento = listaEvento;
    }
    
    
    public void gerarListaEventos(){
        listaEvento = eventoDao.list("Select e from Evento e");
        if (listaEvento == null) {
            listaEvento = new ArrayList<Evento>();
        }
    }
    
     public String novoCadastroEvento() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 580);
        RequestContext.getCurrentInstance().openDialog("cadEventos", options, null);
        return "";
    }
     
     public String novoConvidados() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("cadConvidadosEvento", options, null);
        return "";
    }
   
    
    public void retornoDialogNovo(SelectEvent event){
        Evento evento = (Evento) event.getObject();
        if (evento.getIdevento() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de Evento realizado com sucesso");
        }
        gerarListaEventos();
    }
    
    public void retornoDialogAlteracao(SelectEvent event){
        Evento evento = (Evento) event.getObject();
        if (evento.getIdevento() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração do Ambiente realizado com sucesso");
        }
        gerarListaEventos();
    }
    
    
    public void editar(Evento evento){
        if (evento != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("evento", evento);
            options.put("contentWidth", 580);
            RequestContext.getCurrentInstance().openDialog("cadEventos", options, null);
        }
    }
    
    public String verificarSituacao(Evento evento){
        if (evento.getSituacao().equalsIgnoreCase("A")) {
            return " color:green;";
        }else if (evento.getSituacao().equalsIgnoreCase("C")) {
            return " color:red;";
        }else{
            return " color:blue;";
        }
    }
}
