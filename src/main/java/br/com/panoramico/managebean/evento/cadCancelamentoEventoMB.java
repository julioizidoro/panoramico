/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.evento;

import br.com.panoramico.dao.EventoCancelamentoDao;
import br.com.panoramico.dao.EventoDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Evento;
import br.com.panoramico.model.Eventocancelamento;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
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
 * @author Kamilla Rodrigues
 */

@Named
@ViewScoped
public class cadCancelamentoEventoMB implements Serializable{
    
    private Eventocancelamento eventocancelamento;
    private Evento evento;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private EventoCancelamentoDao eventoCancelamentoDao;
    @EJB
    private EventoDao eventoDao;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        evento = (Evento) session.getAttribute("evento");
        session.removeAttribute("evento");
        if (eventocancelamento == null) {
            eventocancelamento = new Eventocancelamento();
        }
    }

    public Eventocancelamento getEventocancelamento() {
        return eventocancelamento;
    }

    public void setEventocancelamento(Eventocancelamento eventocancelamento) {
        this.eventocancelamento = eventocancelamento;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public EventoCancelamentoDao getEventoCancelamentoDao() {
        return eventoCancelamentoDao;
    }

    public void setEventoCancelamentoDao(EventoCancelamentoDao eventoCancelamentoDao) {
        this.eventoCancelamentoDao = eventoCancelamentoDao;
    }

    public EventoDao getEventoDao() {
        return eventoDao;
    }

    public void setEventoDao(EventoDao eventoDao) {
        this.eventoDao = eventoDao;
    }
    
    
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Eventocancelamento());
    }
    
    public void salvar(){
        String msg = validarDados(eventocancelamento);
        if (msg.length() < 5) {
            evento.setSituacao("C");
            evento = eventoDao.update(evento);
            eventocancelamento.setUsuario(usuarioLogadoMB.getUsuario());
            eventocancelamento.setEvento(evento); 
            eventocancelamento = eventoCancelamentoDao.update(eventocancelamento);
            RequestContext.getCurrentInstance().closeDialog(eventocancelamento);
        }else{
            Mensagem.lancarMensagemInfo("", msg);
        }
    }
    
    public String validarDados(Eventocancelamento cancelamento){
        String mensagem = "";
        if (cancelamento.getMotivo().equalsIgnoreCase("")) {
            mensagem = mensagem + " motivo do cancelamento não informado \r\n";
        }
        return mensagem;
    }
}
