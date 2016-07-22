/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.PlanoDao;
import br.com.panoramico.model.Empresa;
import br.com.panoramico.model.Plano;
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
public class CadPlanoMB implements Serializable{
    
    @EJB
    private PlanoDao planoDao;
    private Plano plano;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
         plano =  (Plano) session.getAttribute("plano");
         session.removeAttribute("plano");
        if (plano == null) {
            plano = new Plano();
        } 
    }

    public PlanoDao getPlanoDao() {
        return planoDao;
    }

    public void setPlanoDao(PlanoDao planoDao) {
        this.planoDao = planoDao;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }
    
    
    public void salvar(){
        plano = planoDao.update(plano);
        RequestContext.getCurrentInstance().closeDialog(plano);
    }
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(plano);
    }
}
