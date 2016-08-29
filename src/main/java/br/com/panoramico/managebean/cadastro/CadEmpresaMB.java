/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.BancoDao;
import br.com.panoramico.dao.EmpresaDao;
import br.com.panoramico.dao.PlanoDao;
import br.com.panoramico.model.Banco;
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
    private Banco banco;
    private List<Banco> listaBanco;
    @EJB
    private BancoDao bancoDao;
    
    
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
            banco = empresa.getBanco();
        }
        gerarListaPlano();
        gerarListaBanco();
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

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Banco> getListaBanco() {
        return listaBanco;
    }

    public void setListaBanco(List<Banco> listaBanco) {
        this.listaBanco = listaBanco;
    }

    public BancoDao getBancoDao() {
        return bancoDao;
    }

    public void setBancoDao(BancoDao bancoDao) {
        this.bancoDao = bancoDao;
    }
    
    
    
    
     public void salvar(){
         String msg = validarDaodos();
         if (msg.length() < 5) {
            empresa.setPlano(plano);
            empresa.setBanco(banco);
            empresa = empresaDao.update(empresa);
            RequestContext.getCurrentInstance().closeDialog(empresa);
         }else{
             Mensagem.lancarMensagemInfo("", msg);
         }
    }
     
     public void cancelar(){
         RequestContext.getCurrentInstance().closeDialog(new Empresa());
     }
     
     
     public String validarDaodos(){
         String mensagem = "";
         
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
     
     public void gerarListaBanco(){
         listaBanco = bancoDao.list("Select b from Banco b");
         if (listaBanco == null || listaBanco.isEmpty()) {
             listaBanco = new ArrayList<Banco>();
         }
     }
    
}
