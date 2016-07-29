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
import br.com.panoramico.model.Eventocancelamento;
import br.com.panoramico.model.Tipoenvento;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
    private Ambiente ambiente;
    private Tipoenvento tipoenvento;
    private Cliente cliente;
    private List<Tipoenvento> listaTipoEvento;
    private List<Ambiente> listaAmbiente;
    private List<Cliente> listaCliente;
    @EJB
    private TipoEventoDao tipoEventoDao;
    @EJB
    private AmbienteDao ambienteDao;
    @EJB
    private ClienteDao clienteDao;
    private String situacao;
    private Date dataInicio;
    private Date dataFinal;
    private List<BolinhaBean> listaBolinhas;
    
    
    @PostConstruct
    public void init(){
        gerarListaEventos();
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

    public List<Evento> getListaEvento() {
        return listaEvento;
    }

    public void setListaEvento(List<Evento> listaEvento) {
        this.listaEvento = listaEvento;
    }

    public EventoDao getEventoDao() {
        return eventoDao;
    }

    public void setEventoDao(EventoDao eventoDao) {
        this.eventoDao = eventoDao;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public Tipoenvento getTipoenvento() {
        return tipoenvento;
    }

    public void setTipoenvento(Tipoenvento tipoenvento) {
        this.tipoenvento = tipoenvento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Tipoenvento> getListaTipoEvento() {
        return listaTipoEvento;
    }

    public void setListaTipoEvento(List<Tipoenvento> listaTipoEvento) {
        this.listaTipoEvento = listaTipoEvento;
    }

    public List<Ambiente> getListaAmbiente() {
        return listaAmbiente;
    }

    public void setListaAmbiente(List<Ambiente> listaAmbiente) {
        this.listaAmbiente = listaAmbiente;
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public TipoEventoDao getTipoEventoDao() {
        return tipoEventoDao;
    }

    public void setTipoEventoDao(TipoEventoDao tipoEventoDao) {
        this.tipoEventoDao = tipoEventoDao;
    }

    public AmbienteDao getAmbienteDao() {
        return ambienteDao;
    }

    public void setAmbienteDao(AmbienteDao ambienteDao) {
        this.ambienteDao = ambienteDao;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public List<BolinhaBean> getListaBolinhas() {
        return listaBolinhas;
    }

    public void setListaBolinhas(List<BolinhaBean> listaBolinhas) {
        this.listaBolinhas = listaBolinhas;
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
     
     public String novoConvidados(Evento e) {
        if (e != null) {
           Map<String, Object> options = new HashMap<String, Object>();
           options.put("contentWidth", 600);
           FacesContext fc = FacesContext.getCurrentInstance();
           HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
           session.setAttribute("evento", e);
           RequestContext.getCurrentInstance().openDialog("cadConvidadosEvento", options, null);
        }
        return "";
    }
     
    public void retornoDialogConvidados(SelectEvent event){
        String mensagem = (String) event.getObject();
        if (mensagem.length() > 5) {
            Mensagem.lancarMensagemInfo("", mensagem);
        }
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
    
    
    public String novoCancelamento(Evento e) {
        if (e != null) {
           Map<String, Object> options = new HashMap<String, Object>();
           options.put("contentWidth", 600);
           FacesContext fc = FacesContext.getCurrentInstance();
           HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
           session.setAttribute("evento", e);
           RequestContext.getCurrentInstance().openDialog("cadCancelamentoEvento", options, null);
        }
        return "";
    }
    
    public void retornoDialogCancelamento(SelectEvent event){
        Eventocancelamento eventocancelamento = (Eventocancelamento) event.getObject();
        if (eventocancelamento.getIdeventocancelamento() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cancelamento do Evento realizado com sucesso");
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
    
    public String verificarSituacaoCancelada(Evento evento){
        if (evento.getSituacao().equalsIgnoreCase("C")) {
            return "true";
        }else{
            return "false";
        }
    }
    
    public String verificarSituacaoRealizada(Evento evento){
        if (evento.getSituacao().equalsIgnoreCase("R")) {
            return "true";
        }else{
            return "false";
        }
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
    
    public void filtrar(){
        String sql = "Select e from Evento e";
        if (cliente.getIdcliente() != null || ambiente.getIdambiente() != null || tipoenvento.getIdtipoenvento() != null || !situacao.equalsIgnoreCase("sn") || dataInicio != null || dataFinal != null) {
                sql = sql + " where";
        }
        if (cliente.getIdcliente() != null) {
            sql = sql + " e.cliente.idcliente=" + cliente.getIdcliente();
            if (ambiente.getIdambiente() != null || tipoenvento.getIdtipoenvento() != null || !situacao.equalsIgnoreCase("sn") || dataInicio != null || dataFinal != null) {
                sql = sql + " and";
            }
        }
        if (ambiente.getIdambiente() != null) {
            sql = sql + " e.ambiente.idambiente=" + ambiente.getIdambiente();
            if (tipoenvento.getIdtipoenvento() != null || !situacao.equalsIgnoreCase("sn") || dataInicio != null || dataFinal != null) {
                sql = sql + " and";
            }
        }
        if (tipoenvento.getIdtipoenvento() != null) {
            sql = sql + " e.tipoenvento.idtipoenvento=" + tipoenvento.getIdtipoenvento();
            if (!situacao.equalsIgnoreCase("sn") || dataInicio != null || dataFinal != null) {
                sql = sql + " and";
            }
        }
        if (!situacao.equalsIgnoreCase("sn")) {
            sql = sql + " e.situacao='" + situacao + "'";
            if (dataInicio != null || dataFinal != null) {
                sql = sql + " and";
            }
        }
        if (dataInicio != null && dataFinal != null) {
            sql = sql + " e.data>='" + Formatacao.ConvercaoDataSql(dataInicio) + "' and e.data<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
        }
        listaEvento = eventoDao.list(sql);
        Mensagem.lancarMensagemInfo("", "Filtrado com sucesso");
    }
    
    public void limparFiltro(){
        cliente = null;
        ambiente = null;
        tipoenvento = null;
        situacao = "";
        gerarListaAmbiente();
        gerarListaEventos();
        gerarListaResponsavel();
        gerarListaTipoEvento();
        dataInicio = null;
        dataFinal = null;
    }
    
    public String verSituacao(Evento evento){
        if (evento.getSituacao().equalsIgnoreCase("A")) {
            return "../../resources/img/bolaVerde.png";
        }else if (evento.getSituacao().equalsIgnoreCase("C")) {
            return "../../resources/img/bolaVermelha.png";
        }else{
            return "../../resources/img/bolaAzul.png";
        }
    }
    
    public String nomeSituacao(Evento evento){
        if (evento.getSituacao().equalsIgnoreCase("A")) {
            return "Situação: Agendado";
        }else if (evento.getSituacao().equalsIgnoreCase("C")) {
            return "Situação: Cancelado";
        }else{
            return "Situação: Realizado";
        }
    }
    
    public void realizadoEvento(Evento evento){
        if (evento != null) {
            evento.setSituacao("R");
            eventoDao.update(evento);
        }
    }
}
