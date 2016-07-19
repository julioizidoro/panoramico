/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AmbienteDao;
import br.com.panoramico.model.Ambiente;
import br.com.panoramico.model.Cliente;
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
public class CadAmbienteMB implements Serializable{
    
    @EJB
    private AmbienteDao ambienteDao;
    private Ambiente ambiente;
    
    
    @PostConstruct
    public void init(){
         FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        ambiente = (Ambiente) session.getAttribute("ambiente");
        session.removeAttribute("ambiente");
        if (ambiente == null) {
            ambiente = new Ambiente();
        }
    }

    public AmbienteDao getAmbienteDao() {
        return ambienteDao;
    }

    public void setAmbienteDao(AmbienteDao ambienteDao) {
        this.ambienteDao = ambienteDao;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }
    
    public void salvar(){
        ambiente = ambienteDao.update(ambiente);
        RequestContext.getCurrentInstance().closeDialog(ambiente);
    }
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(ambiente);
    }
}
