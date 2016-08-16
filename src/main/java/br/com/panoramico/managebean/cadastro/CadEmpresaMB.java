/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.EmpresaDao;
import br.com.panoramico.dao.PlanoDao;
import br.com.panoramico.model.Empresa;
import br.com.panoramico.model.Plano;
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
public class CadEmpresaMB implements  Serializable{
    
    
    @EJB
    private EmpresaDao empresaDao;
    private Empresa empresa;
    private Plano plano;
    private List<Plano> listaPlano;
    @EJB
    private PlanoDao planoDao;
    
    
    @PostConstruct
    public void init(){
         FacesContext fc = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
         empresa =  (Empresa) session.getAttribute("empresa");
         session.removeAttribute("empresa");
        if (empresa == null) {
            empresa = new Empresa();
        }else{
            plano = empresa.getPlano();
        }
        gerarListaPlano();
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

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public List<Plano> getListaPlano() {
        return listaPlano;
    }

    public void setListaPlano(List<Plano> listaPlano) {
        this.listaPlano = listaPlano;
    }

    public PlanoDao getPlanoDao() {
        return planoDao;
    }

    public void setPlanoDao(PlanoDao planoDao) {
        this.planoDao = planoDao;
    }
    
    
    
    
     public void salvar(){
         String msg = validarDaodos();
         if (msg.length() < 5) {
            empresa.setPlano(plano);
            empresa = empresaDao.update(empresa);
            RequestContext.getCurrentInstance().closeDialog(empresa);
         }else{
             Mensagem.lancarMensagemInfo("", msg);
         }
    }
     
     public void cancelar(){
         RequestContext.getCurrentInstance().closeDialog(empresa);
     }
     
     
     public String validarDaodos(){
         String mensagem = "";
         if (empresa.getCnpj().equalsIgnoreCase("")) {
             mensagem = mensagem + " Você não digitou o cnpj \r\n";
         }
         
         if (empresa.getRazaosocial().equalsIgnoreCase("")) {
             mensagem = mensagem + " Você não informou a razão social \r\n";
         }
         return mensagem;
     }
     
     public void gerarListaPlano(){
         listaPlano = planoDao.list("Select p from Plano p");
         if (listaPlano == null || listaPlano.isEmpty()) {
             listaPlano = new ArrayList<Plano>();
         }
     }
    
}
