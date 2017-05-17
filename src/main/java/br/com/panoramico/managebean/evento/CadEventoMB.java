/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.evento;

import br.com.panoramico.dao.AmbienteDao;
import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.EventoDao;
import br.com.panoramico.dao.ParametrosDao;
import br.com.panoramico.dao.PlanoContaDao;
import br.com.panoramico.dao.TipoEventoDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Ambiente;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Evento;
import br.com.panoramico.model.Parametros;
import br.com.panoramico.model.Planoconta;
import br.com.panoramico.model.Tipoenvento;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadEventoMB implements Serializable {

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Evento evento;
    private Ambiente ambiente;
    private Tipoenvento tipoevento;
    private Cliente cliente;
    private Planoconta planoconta;
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
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private PlanoContaDao planoContaDao;
    private Parametros parametros;
    @EJB
    private ParametrosDao parametrosDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        evento = (Evento) session.getAttribute("evento");
        session.removeAttribute("evento");
        if (evento == null) {
            evento = new Evento();
        } else {
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

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public PlanoContaDao getPlanoContaDao() {
        return planoContaDao;
    }

    public void setPlanoContaDao(PlanoContaDao planoContaDao) {
        this.planoContaDao = planoContaDao;
    }

    public Planoconta getPlanoconta() {
        return planoconta;
    }

    public void setPlanoconta(Planoconta planoconta) {
        this.planoconta = planoconta;
    }

    public void gerarListaAmbiente() {
        listaAmbiente = ambienteDao.list("Select a from Ambiente a");
        if (listaAmbiente == null) {
            listaAmbiente = new ArrayList<Ambiente>();
        }
    }

    public void gerarListaTipoEvento() {
        listaTipoEvento = tipoEventoDao.list("Select t from Tipoenvento t ");
        if (listaTipoEvento == null) {
            listaTipoEvento = new ArrayList<Tipoenvento>();
        }
    }

    public void gerarListaResponsavel() {
        listaCliente = clienteDao.list("Select c from Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }
    }

    public void salvar() {
        String msg = validarDados();
        if (msg.length() < 1) {
            evento.setAmbiente(ambiente);
            evento.setCliente(cliente);
            evento.setTipoenvento(tipoevento);
            evento.setUsuario(usuarioLogadoMB.getUsuario());
            if (evento.getIdevento() == null) {
                evento = eventoDao.update(evento);
                lancarContasReceber();
            } else {
                evento = eventoDao.update(evento);
            }
            RequestContext.getCurrentInstance().closeDialog(evento);
        } else {
            Mensagem.lancarMensagemInfo("Atenção", msg);
        }
    }

    public void lancarContasReceber() {
        Contasreceber contasreceber = new Contasreceber();
        contasreceber.setDatalancamento(new Date());
        contasreceber.setCliente(cliente);
        contasreceber.setNumeroparcela("1");
        contasreceber.setNumerodocumento("" + evento.getIdevento());
        contasreceber.setValorconta(evento.getValor());
        contasreceber.setUsuario(usuarioLogadoMB.getUsuario());
        contasreceber.setEnviado(false);
        contasreceber.setSituacao("PAGAR");
        parametros = parametrosDao.find(1);
        planoconta = planoContaDao.find(parametros.getPlanocontaevento());
        contasreceber.setPlanoconta(planoconta);
        contasReceberDao.update(contasreceber);
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(new Evento());
    }

    public String validarDados() {
        String mensagem = "";
        List<Evento> listaEventos = eventoDao.list("Select e from Evento e where e.ambiente.idambiente=" + ambiente.getIdambiente()
                + " and e.data='" + Formatacao.ConvercaoDataSql(evento.getData()) + "' and e.idevento<>" + evento.getIdevento());
        if (listaEventos == null | listaEventos.isEmpty()) {
        } else {
            mensagem = mensagem + " neste dia ja tem evento neste ambiente \r\n";
        }
        if (ambiente == null) {
            mensagem = mensagem + " Ambiente não selecionado \r\n";
        }
        if (tipoevento == null) {
            mensagem = mensagem + " Tipo de Evento não selecionado \r\n";
        }
        if (cliente == null) {
            mensagem = mensagem + " Cliente não selecionado";
        }    
        return mensagem;
    }
}
