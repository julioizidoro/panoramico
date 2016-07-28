/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.evento;

import br.com.panoramico.dao.EventoDao;
import br.com.panoramico.model.Evento;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;



@Named
@ViewScoped
public class alterarSituacaoEventoMB implements Serializable{
    
    private Evento evento;
    @EJB
    private EventoDao eventoDao;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        evento = (Evento) session.getAttribute("evento");
        session.removeAttribute("evento");
        if (evento == null) {
            Mensagem.lancarMensagemInfo("", "Evento não selecionado");
            RequestContext.getCurrentInstance().closeDialog(new Evento());
        }
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public EventoDao getEventoDao() {
        return eventoDao;
    }

    public void setEventoDao(EventoDao eventoDao) {
        this.eventoDao = eventoDao;
    }
    
    
    
    public void salvar() {
        if (evento.getSituacao().equalsIgnoreCase("sn")) {
            Mensagem.lancarMensagemInfo("Atenção", "você selecionou uma situação para o evento");
        }else{
            evento = eventoDao.update(evento);
            RequestContext.getCurrentInstance().closeDialog(evento);
        }
    }
    
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Evento());
    }
}
