/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.evento;

import br.com.panoramico.dao.EventoConvidadosDao;
import br.com.panoramico.model.Evento;
import br.com.panoramico.model.Eventoconvidados;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kamilla Rodrigues
 */

@Named
@ViewScoped
public class cadConvidadosEventoMB implements Serializable{
    
    private Eventoconvidados eventoconvidados;
    private List<Eventoconvidados> listaConvidados;
    private Evento evento;
    @EJB
    private EventoConvidadosDao eventoConvidadosDao;
    private String mensagem;
    private Integer totalConvidados = 0;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        evento = (Evento) session.getAttribute("evento");
        session.removeAttribute("evento");
        gerarListaConvidados();
        if (eventoconvidados == null) {
            eventoconvidados = new Eventoconvidados();
        }
    }

    public Eventoconvidados getEventoconvidados() {
        return eventoconvidados;
    }

    public void setEventoconvidados(Eventoconvidados eventoconvidados) {
        this.eventoconvidados = eventoconvidados;
    }

    public List<Eventoconvidados> getListaConvidados() {
        return listaConvidados;
    }

    public void setListaConvidados(List<Eventoconvidados> listaConvidados) {
        this.listaConvidados = listaConvidados;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public EventoConvidadosDao getEventoConvidadosDao() {
        return eventoConvidadosDao;
    }

    public void setEventoConvidadosDao(EventoConvidadosDao eventoConvidadosDao) {
        this.eventoConvidadosDao = eventoConvidadosDao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getTotalConvidados() {
        return totalConvidados;
    }

    public void setTotalConvidados(Integer totalConvidados) {
        this.totalConvidados = totalConvidados;
    }
    
    
    public void salvar(){
        mensagem = "";
        Eventoconvidados convidados = new Eventoconvidados();
        for(int i = 0; i < listaConvidados.size(); i++) {
            listaConvidados.get(i).setSituacao("N");
            listaConvidados.get(i).setEvento(evento);
            convidados = eventoConvidadosDao.update(listaConvidados.get(i));
        }
        if (convidados.getIdeventoconvidados() != null) {
            mensagem = "Convidados cadastrados com sucesso!!";
        }
        RequestContext.getCurrentInstance().closeDialog(mensagem);
    }
    
    public void cancelar(){
        mensagem = "";
        RequestContext.getCurrentInstance().closeDialog(mensagem);
    }
    
    public String validarDados(Eventoconvidados convidados){
        String msg = "";
        if (convidados.getNome().equalsIgnoreCase("")) {
            msg = msg + " Nome do convidado não informado \r\n";
        }
        return msg;
    }
    
    public void adicionarConvidado(){
        String msgem = "";
        msgem = validarDados(eventoconvidados);
        if (listaConvidados == null) {
            listaConvidados = new ArrayList<Eventoconvidados>();
        }
        if (msgem.length() < 5) {
            listaConvidados.add(eventoconvidados);
            eventoconvidados = new Eventoconvidados();
            Mensagem.lancarMensagemInfo("Adicionou", "convidado cadastrado");
        }
    }
    
    public void excluirConvidado(Eventoconvidados excluirConvidado){
        listaConvidados.remove(excluirConvidado);
        eventoConvidadosDao.remove(excluirConvidado.getIdeventoconvidados());
    }
     
    public void gerarListaConvidados(){
        listaConvidados = eventoConvidadosDao.list("Select c from Eventoconvidados c where c.evento.idevento=" + evento.getIdevento());
        if (listaConvidados == null) {
            listaConvidados = new ArrayList<Eventoconvidados>();
        }
    }
}
