/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoEmpresaDao;
import br.com.panoramico.dao.EmpresaDao;
import br.com.panoramico.model.Acesso;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Associadoempresa;
import br.com.panoramico.model.Empresa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Julio
 */

@Named
@ViewScoped
public class CadAssociadoEmpresaMB implements Serializable{
    
    
    @EJB
    private AssociadoEmpresaDao associadoEmpresaDao;
    private Associadoempresa associadoempresa;
    @EJB
    private EmpresaDao empresaDao;
    private List<Empresa> listarEmpresa;
    private Empresa empresa;
    private Associado associado;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        associado = (Associado) session.getAttribute("associado");
        gerarListaEmpresa();
        if (associado != null) {
            associadoempresa = new Associadoempresa();
            if (associadoempresa == null) {
                empresa = new Empresa();
            }else{
                empresa = associadoempresa.getEmpresa();
            }
            
        }
    }

    public AssociadoEmpresaDao getAssociadoEmpresaDao() {
        return associadoEmpresaDao;
    }

    public void setAssociadoEmpresaDao(AssociadoEmpresaDao associadoEmpresaDao) {
        this.associadoEmpresaDao = associadoEmpresaDao;
    }

    public Associadoempresa getAssociadoempresa() {
        return associadoempresa;
    }

    public void setAssociadoempresa(Associadoempresa associadoempresa) {
        this.associadoempresa = associadoempresa;
    }

    public EmpresaDao getEmpresaDao() {
        return empresaDao;
    }

    public void setEmpresaDao(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao;
    }

    public List<Empresa> getListarEmpresa() {
        return listarEmpresa;
    }

    public void setListarEmpresa(List<Empresa> listarEmpresa) {
        this.listarEmpresa = listarEmpresa;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }
    
    
    
    public void gerarListaEmpresa(){
        listarEmpresa = empresaDao.list("Select e from Empresa e");
        if (listarEmpresa == null) {
            listarEmpresa = new ArrayList<Empresa>();
        }
    }
    
}
