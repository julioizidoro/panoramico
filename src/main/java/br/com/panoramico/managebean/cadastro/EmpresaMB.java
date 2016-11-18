/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoEmpresaDao;
import br.com.panoramico.dao.EmpresaDao;
import br.com.panoramico.model.Associadoempresa;
import br.com.panoramico.model.Empresa;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Julio
 */

@Named
@ViewScoped
public class EmpresaMB implements  Serializable{
    
    @EJB
    private EmpresaDao empresaDao;
    private Empresa empresa;
    private List<Empresa> listaEmpresa;
    @EJB
    private AssociadoEmpresaDao associadoEmpresaDao;
    
    
    @PostConstruct
    public void init(){
        gerarListaEmpresa();
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

    public List<Empresa> getListaEmpresa() {
        return listaEmpresa;
    }

    public void setListaEmpresa(List<Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
    }
    
    
    
   public String novoCadastroEmpresa() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 570);
        RequestContext.getCurrentInstance().openDialog("cadEmpresa", options, null);
        return "";
    }
    
    
    public void retornoDialogNovo(SelectEvent event){
        Empresa empresa = (Empresa) event.getObject();
        if (empresa.getIdempresa()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de empresa realizado com sucesso");
        }
        gerarListaEmpresa();
    }
    
     public void retornoDialogAlteracao(SelectEvent event){
        Empresa empresa = (Empresa) event.getObject();
        if (empresa.getIdempresa()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração da empresa realizada com sucesso");
        }
        gerarListaEmpresa();
    }
    
    
    public void editar(Empresa empresa){
        if (empresa != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("empresa", empresa);
            options.put("contentWidth", 570);
            RequestContext.getCurrentInstance().openDialog("cadEmpresa", options, null);
        }
    }

    public void gerarListaEmpresa() {
        listaEmpresa = empresaDao.list("Select e from Empresa e");
        if (listaEmpresa == null) {
            listaEmpresa = new ArrayList<Empresa>();
        }
    }
    
    
    public void excluir(Empresa empresa){
        List<Associadoempresa> listaAssociadoEmpresa = associadoEmpresaDao.list("Select ae From Associadoempresa ae Where ae.empresa.idempresa=" + empresa.getIdempresa());
        if (listaAssociadoEmpresa == null || listaAssociadoEmpresa.isEmpty()) {
            empresaDao.remove(empresa.getIdempresa());
            Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
            gerarListaEmpresa();
        }else{
            Mensagem.lancarMensagemInfo("Atenção", " esta empresa não pode ser excluido");
        }
    }
    
    public String consAssociadoEmpresa(Empresa empresa){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("empresa", empresa);
        return "consAssociadoEmpresa";
    }
}
