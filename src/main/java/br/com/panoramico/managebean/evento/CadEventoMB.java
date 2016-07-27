/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.evento;

import br.com.panoramico.dao.AmbienteDao;
import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.dao.EventoDao;
import br.com.panoramico.dao.TipoEventoDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Ambiente;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Evento;
import br.com.panoramico.model.Tipoenvento;
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

/**
 *
 * @author Kamilla Rodrigues
 */

@Named
@ViewScoped
public class CadEventoMB implements Serializable{
    
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Evento evento;
    private Ambiente ambiente;
    private Tipoenvento tipoevento;
    private Cliente cliente;
    private List<Ambiente> listaAmbiente;
    private List<Tipoenvento> listaTipoEvento;
    private List<Cliente> listaCliente;
    @EJB
    private EventoDao eventoDao;
    @EJB
    private AmbienteDao ambienteDao;
    @EJB
    private TipoEventoDao tipoEventoDao;
    @EJB
    private ClienteDao clienteDao;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        evento = (Evento) session.getAttribute("evento");
        session.removeAttribute("evento");
        if (evento == null) {
            evento = new Evento();
        }else{
            tipoevento = evento.getTipoenvento();
            ambiente = evento.getAmbiente();
            cliente = evento.getCliente();
        }
        gerarListaAmbiente();
        gerarListaResponsavel();
        gerarListaTipoEvento();
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

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public Tipoenvento getTipoevento() {
        return tipoevento;
    }

    public void setTipoevento(Tipoenvento tipoevento) {
        this.tipoevento = tipoevento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Ambiente> getListaAmbiente() {
        return listaAmbiente;
    }

    public void setListaAmbiente(List<Ambiente> listaAmbiente) {
        this.listaAmbiente = listaAmbiente;
    }

    public List<Tipoenvento> getListaTipoEvento() {
        return listaTipoEvento;
    }

    public void setListaTipoEvento(List<Tipoenvento> listaTipoEvento) {
        this.listaTipoEvento = listaTipoEvento;
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public EventoDao getEventoDao() {
        return eventoDao;
    }

    public void setEventoDao(EventoDao eventoDao) {
        this.eventoDao = eventoDao;
    }

    public AmbienteDao getAmbienteDao() {
        return ambienteDao;
    }

    public void setAmbienteDao(AmbienteDao ambienteDao) {
        this.ambienteDao = ambienteDao;
    }

    public TipoEventoDao getTipoEventoDao() {
        return tipoEventoDao;
    }

    public void setTipoEventoDao(TipoEventoDao tipoEventoDao) {
        this.tipoEventoDao = tipoEventoDao;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }
    
    
    public void gerarListaAmbiente(){
        listaAmbiente = ambienteDao.list("Select a from Ambiente a");
        if (listaAmbiente == null) {
            listaAmbiente = new ArrayList<Ambiente>();
        }
    }
    
    public void gerarListaTipoEvento(){
        listaTipoEvento = tipoEventoDao.list("Select t from Tipoenvento t ");
        if (listaTipoEvento == null) {
            listaTipoEvento = new ArrayList<Tipoenvento>();
        }
    }
    
    public void gerarListaResponsavel(){
        listaCliente = clienteDao.list("Select c from Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }
    }
    
    
    public void salvar(){
        evento.setAmbiente(ambiente);
        evento.setCliente(cliente);
        evento.setTipoenvento(tipoevento);
        evento.setUsuario(usuarioLogadoMB.getUsuario());
        evento = eventoDao.update(evento);
        RequestContext.getCurrentInstance().closeDialog(evento);
    }
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Evento());
    }
}