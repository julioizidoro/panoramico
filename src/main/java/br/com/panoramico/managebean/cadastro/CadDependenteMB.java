/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.DependenteDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Dependente;
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
 * @author Julio
 */

@Named
@ViewScoped
public class CadDependenteMB implements Serializable{
    
    @EJB
    private AssociadoDao associadoDao;
    private Associado associado;
    private List<Associado> listaAssociado;
    @EJB
    private DependenteDao dependenteDao;
    private Dependente dependente;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        dependente = (Dependente) session.getAttribute("dependente");
        session.removeAttribute("dependente");
        gerarListaAssociado();
        if (dependente == null) {
            dependente = new Dependente();
            if (associado == null) {
                associado = new Associado();
            }
        }else{
            associado = dependente.getAssociado();
        }
    }
    
    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public List<Associado> getListaAssociado() {
        return listaAssociado;
    }

    public void setListaAssociado(List<Associado> listaAssociado) {
        this.listaAssociado = listaAssociado;
    }

    public DependenteDao getDependenteDao() {
        return dependenteDao;
    }

    public void setDependenteDao(DependenteDao dependenteDao) {
        this.dependenteDao = dependenteDao;
    }

    public Dependente getDependente() {
        return dependente;
    }

    public void setDependente(Dependente dependente) {
        this.dependente = dependente;
    }
    
    
    public void salvar(){
        Integer numeroAssociadoProvisorio = 1;
        associado = associadoDao.find(numeroAssociadoProvisorio);
        dependente.setAssociado(associado);
        dependente = dependenteDao.update(dependente);
        RequestContext.getCurrentInstance().closeDialog(dependente);
    }
    
    public void gerarListaAssociado(){
        listaAssociado = associadoDao.list("Select a from Associado a");
        if (listaAssociado == null) {
            listaAssociado = new ArrayList<Associado>();
        }
    }
}
