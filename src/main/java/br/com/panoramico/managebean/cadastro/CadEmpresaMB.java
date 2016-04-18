/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.EmpresaDao;
import br.com.panoramico.model.Empresa;
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
public class CadEmpresaMB implements  Serializable{
    
    
    @EJB
    private EmpresaDao empresaDao;
    private Empresa empresa;
    
    
    @PostConstruct
    public void init(){
         FacesContext fc = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
         empresa =  (Empresa) session.getAttribute("empresa");
         session.removeAttribute("empresa");
        if (empresa == null) {
            empresa = new Empresa();
        }
    }

    public EmpresaDao getEmpresaDao() {
        return empresaDao;
    }

    public void setEmpresaDao(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    
    
     public void salvar(){
        empresa = empresaDao.update(empresa);
        RequestContext.getCurrentInstance().closeDialog(empresa);
    }
    
}
