package br.com.panoramico.managebean.evento;

import br.com.panoramico.dao.EventoConvidadosDao;
import br.com.panoramico.model.Evento;
import br.com.panoramico.model.Eventoconvidados;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
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
public class ListaConvidadosEventoMB implements Serializable {

    private List<Eventoconvidados> listaConvidados;
    private Evento evento;
    @EJB
    private EventoConvidadosDao eventoConvidadosDao;
    private String nome;
    private String tipoConvidado;
    private boolean pendentes;
    private boolean presentes;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        evento = (Evento) session.getAttribute("evento");
        session.removeAttribute("evento");
        tipoConvidado = "Pendentes";
        pendentes = true;
        presentes = false;
        nome = "";
        gerarListaConvidados();
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoConvidado() {
        return tipoConvidado;
    }

    public void setTipoConvidado(String tipoConvidado) {
        this.tipoConvidado = tipoConvidado;
    }

    public boolean isPendentes() {
        return pendentes;
    }

    public void setPendentes(boolean pendentes) {
        this.pendentes = pendentes;
    }

    public boolean isPresentes() {
        return presentes;
    }

    public void setPresentes(boolean presentes) {
        this.presentes = presentes;
    }
 

    public void confirmarPresencaConvidado(Eventoconvidados eventoconvidados) {
        eventoconvidados.setSituacao("S");
        eventoConvidadosDao.update(eventoconvidados);
        gerarListaConvidados();
    }

    public void cancelarConvidado(Eventoconvidados eventoconvidados) {
        eventoconvidados.setSituacao("N");
        eventoConvidadosDao.update(eventoconvidados);
        gerarListaConvidados();
    }

    public void gerarListaConvidados() {
        if (tipoConvidado.equalsIgnoreCase("Pendentes")) {
            listaConvidados = eventoConvidadosDao.list("Select e from Eventoconvidados e where e.situacao='N'"
                    + " and e.evento.idevento=" + evento.getIdevento()
                    + " and e.nome like '" + nome + "%'");
            if (listaConvidados == null) {
                Mensagem.lancarMensagemInfo("", "Nenhum convidado pendente.");
            }
            pendentes = true;
            presentes = false;
            
        } else {
            listaConvidados = eventoConvidadosDao.list("Select e from Eventoconvidados e where e.situacao='S'"
                    + " and e.evento.idevento=" + evento.getIdevento()
                    + " and e.nome like '" + nome + "%'");
            if (listaConvidados == null) {
                Mensagem.lancarMensagemInfo("", "Nenhum convidado presente.");
            }
            pendentes = false;
            presentes = true;
        }
    }
 
    public void botaoMudarLista() {
        if (tipoConvidado.equalsIgnoreCase("Pendentes")) {
            tipoConvidado = "Presentes";
        } else {
            tipoConvidado = "Pendentes";
        }
        gerarListaConvidados();
    }
    
    
    public void fechar() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }
}
