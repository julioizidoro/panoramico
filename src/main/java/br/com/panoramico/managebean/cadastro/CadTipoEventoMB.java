/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.TipoEventoDao;
import br.com.panoramico.model.Tipoenvento;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Julio
 */


@Named
@ViewScoped
public class CadTipoEventoMB implements Serializable{
    
    @EJB
    private TipoEventoDao tipoEventoDao;
    private Tipoenvento tipoenvento;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        tipoenvento = (Tipoenvento) session.getAttribute("tipoenvento");
        session.removeAttribute("tipoenvento");
        if (tipoenvento == null) {
            tipoenvento = new Tipoenvento();
        }
    }

    public TipoEventoDao getTipoEventoDao() {
        return tipoEventoDao;
    }

    public void setTipoEventoDao(TipoEventoDao tipoEventoDao) {
        this.tipoEventoDao = tipoEventoDao;
    }

    public Tipoenvento getTipoenvento() {
        return tipoenvento;
    }

    public void setTipoenvento(Tipoenvento tipoenvento) {
        this.tipoenvento = tipoenvento;
    }
    

    public void salvar(){
        tipoenvento = tipoEventoDao.update(tipoenvento);
        RequestContext.getCurrentInstance().closeDialog(tipoenvento);
    }
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(tipoenvento);
    }
}
