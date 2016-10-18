/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoEmpresaDao;
import br.com.panoramico.dao.EmpresaDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Associadoempresa;
import br.com.panoramico.model.Empresa;
import br.com.panoramico.uil.Mensagem;
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
public class CadAssociadoEmpresaMB implements Serializable{
    
    
    @EJB
    private AssociadoEmpresaDao associadoEmpresaDao;
    private Associadoempresa associadoempresa;
    @EJB
    private EmpresaDao empresaDao;
    private List<Empresa> listarEmpresa;
    private Empresa empresa;
    private Associado associado;
    private String botaoAssociar = "";
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        associado = (Associado) session.getAttribute("associado");
        associadoempresa = (Associadoempresa) session.getAttribute("associadoempresa");
        gerarListaEmpresa();
        if (associado != null) {
            if (associadoempresa == null) {
                associadoempresa = new Associadoempresa();
                empresa = new Empresa();
                botaoAssociar = "Associar a Empresa";
            }else{
                empresa = associadoempresa.getEmpresa();
                botaoAssociar = "Associado a Empresa";
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

    public String getBotaoAssociar() {
        return botaoAssociar;
    }

    public void setBotaoAssociar(String botaoAssociar) {
        this.botaoAssociar = botaoAssociar;
    }
    
    
    
    public void gerarListaEmpresa(){
        listarEmpresa = empresaDao.list("Select e from Empresa e");
        if (listarEmpresa == null) {
            listarEmpresa = new ArrayList<Empresa>();
        }
    }
    
    public void salvar(){
        associadoempresa.setEmpresa(empresa);
        associadoempresa.setAssociado(associado);
        associadoempresa = associadoEmpresaDao.update(associadoempresa);
    }
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Associadoempresa());
    }
    
    public void verificarAssociadoEmpresa(){
        if (associadoempresa == null || associadoempresa.getIdassociadoempresa() == null) {
            salvar();
            botaoAssociar = "Associado a Empresa";
            associadoempresa = null;
        }else{
            desvincularAssociado();
            associadoempresa = new Associadoempresa();
            gerarListaEmpresa();
            empresa = null;
            botaoAssociar = "Associar a Empresa";
        }
    }
    
    public void desvincularAssociado(){
        associadoEmpresaDao.remove(associadoempresa.getIdassociadoempresa());
    }
    
}
