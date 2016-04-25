/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AcessoDao;
import br.com.panoramico.dao.PerfilDao;
import br.com.panoramico.model.Acesso;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Perfil;
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
public class CadPerfilMB implements Serializable{
    
    @EJB
    private PerfilDao perfilDao;
    private Perfil perfil;
    @EJB
    private AcessoDao acessoDao;
    private Acesso acesso;
    
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        perfil = (Perfil) session.getAttribute("perfil");
        session.removeAttribute("perfil");
        if (perfil == null) {
            perfil = new Perfil();
        }
    }

    public AcessoDao getAcessoDao() {
        return acessoDao;
    }

    public void setAcessoDao(AcessoDao acessoDao) {
        this.acessoDao = acessoDao;
    }

    public Acesso getAcesso() {
        return acesso;
    }

    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }
    
    

    public PerfilDao getPerfilDao() {
        return perfilDao;
    }

    public void setPerfilDao(PerfilDao perfilDao) {
        this.perfilDao = perfilDao;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    
    public void salvar(){
        if (perfil.getAcesso() == null) {
            acesso = acessoDao.find(1L);
           perfil.setAcesso(acesso); 
           
        }
        perfil = perfilDao.update(perfil);
        RequestContext.getCurrentInstance().closeDialog(perfil);
    }
}
