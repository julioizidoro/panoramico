/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.AssociadoEmpresaDao;
import br.com.panoramico.dao.DependenteDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Associadoempresa;
import br.com.panoramico.model.Dependente;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AssociadoMB implements Serializable{
    
    @EJB
    private AssociadoDao associadoDao;
    private List<Associado> listaAssociado;
    @EJB
    private AssociadoEmpresaDao associadoEmpresaDao;
    private Associadoempresa associadoempresa;
    @EJB
    private DependenteDao dependenteDao;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String sql;
    private String matricula;
    
    @PostConstruct
    public void init(){
       FacesContext fc = FacesContext.getCurrentInstance();
       HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
       sql = (String) session.getAttribute("sql");
       if (sql!=null){
           gerarListaAssociado();
       }else {
           sql = "Select a from Associado a";
           gerarListaAssociado();
       }
       session.removeAttribute("sql");
    }

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public List<Associado> getListaAssociado() {
        return listaAssociado;
    }

    public void setListaAssociado(List<Associado> listaAssociado) {
        this.listaAssociado = listaAssociado;
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

    public DependenteDao getDependenteDao() {
        return dependenteDao;
    }

    public void setDependenteDao(DependenteDao dependenteDao) {
        this.dependenteDao = dependenteDao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    
    public void gerarListaAssociado(){
        listaAssociado = associadoDao.list(sql);
        if (listaAssociado == null) {
            listaAssociado = new ArrayList<Associado>();
        }
    }
    
    public String novoCadastroAssociado() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("sql", sql);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 580);
        RequestContext.getCurrentInstance().openDialog("cadAssociado", options, null);
        return "";
    }
    
    
    public void retornoDialogNovo(SelectEvent event){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        sql = (String) session.getAttribute("sql");
        session.removeAttribute("sql");
        Associado associado = (Associado) event.getObject();
        if (associado.getIdassociado()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de Associado realizado com sucesso");
        }
        gerarListaAssociado();
    }
    
     public void retornoDialogAlteracao(SelectEvent event){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        sql = (String) session.getAttribute("sql");
        session.removeAttribute("sql");
        Associado associado = (Associado) event.getObject();
        if (associado.getIdassociado()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração do Associado realizado com sucesso");
        }
        gerarListaAssociado();
    }
    
    
    public void editar(Associado associado){
        if (associado != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("associado", associado);
            session.setAttribute("sql", sql);
            options.put("contentWidth", 580);
            RequestContext.getCurrentInstance().openDialog("cadAssociado", options, null);
        }
    }
    
    
    public void associarEmpresa(Associado associado){
        if (associado != null) {
            try {
                associadoempresa = associadoEmpresaDao.consultar("Select a from Associadoempresa a where a.associado.idassociado=" + associado.getIdassociado());
            } catch (SQLException ex) {
                Logger.getLogger(AssociadoMB.class.getName()).log(Level.SEVERE, null, ex);
            } 
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("associado", associado);
            session.setAttribute("associadoempresa", associadoempresa);
            options.put("contentWidth", 300);
            RequestContext.getCurrentInstance().openDialog("cadAssociadoEmpresa", options, null);
        }
    }
    
    public void excluir(Associado associado){
        List<Dependente> listaDependente = dependenteDao.list("Select d from Dependente d where d.associado.idassociado="+ associado.getIdassociado());
        if (listaDependente == null || listaDependente.isEmpty()) {
            associadoDao.remove(associado.getIdassociado());
            Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
            gerarListaAssociado();
        }else{
            Mensagem.lancarMensagemInfo("Atenção", "este associado não pode ser excluido");
        }
    }
    
    public String limpar(){
        sql = "Select a from Associado a";
        gerarListaAssociado();
        matricula="";
        nome="";
        cpf="";
        email="";
        telefone="";
        return "";
    }
    
    public String pesquisar(){
        sql = "Select a from Associado a where a.cliente.nome like '%" + nome + "%' ";
        if (cpf.length()>0){
            sql = sql + " and a.cliente.cpf='" + cpf + "' ";
        }
        if (email.length()>0){
            sql = sql + " and a.cliente.email='" + email + "' ";
        }
        if (telefone.length()>0){
            sql = sql + " and a.cliente.telefone='" + telefone + "' ";
        }
        if (matricula.length()>0){
            sql = sql + " and a.matricula='" + matricula + "' ";
        }
        sql = sql + " order by a.cliente.nome";
        gerarListaAssociado();
        return "";
    }
}
