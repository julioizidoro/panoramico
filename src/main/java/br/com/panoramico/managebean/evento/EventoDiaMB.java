package br.com.panoramico.managebean.evento;
 
import br.com.panoramico.dao.EventoDao; 
import br.com.panoramico.managebean.UsuarioLogadoMB; 
import br.com.panoramico.model.Evento; 
import br.com.panoramico.uil.Formatacao; 
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

/**
 *
 * @author Kamilla Rodrigues
 */

@Named
@ViewScoped
public class EventoDiaMB implements Serializable{
    
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

    public EventoDao getEventoDao() {
        return eventoDao;
    }

    public void setEventoDao(EventoDao eventoDao) {
        this.eventoDao = eventoDao;
    }
  
    public void gerarListaEventos(){
        Date data= new Date(); 
        listaEvento = eventoDao.list("Select e from Evento e where e.data='" + Formatacao.ConvercaoDataSql(data) + "'  and e.situacao='A'");
        if (listaEvento == null) {
            listaEvento = new ArrayList<Evento>();
        }
    }
     
     public String listaConvidados(Evento evento) {
        if (evento != null) {
           Map<String, Object> options = new HashMap<String, Object>();
           options.put("contentWidth", 650);
           FacesContext fc = FacesContext.getCurrentInstance();
           HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
           session.setAttribute("evento", evento);
           RequestContext.getCurrentInstance().openDialog("listaConvidadosEvento", options, null);
        }
        return "";
    }  
    
    public void realizadoEvento(Evento evento){
        if (evento != null) {
            evento.setSituacao("R");
            eventoDao.update(evento);
            gerarListaEventos();
        }
    } 
    
    public String possuiPiscina(Evento evento){
        if(evento.isPiscina()){
            return "Sim";
        }else return "Não";
    }
    
    public String voltarAcesso(){
        return "consAcesso";
    }
}
