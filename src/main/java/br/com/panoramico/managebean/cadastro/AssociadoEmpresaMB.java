/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.AssociadoEmpresaDao;
import br.com.panoramico.dao.EmpresaDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Associadoempresa;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Empresa;
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

@Named
@ViewScoped
public class AssociadoEmpresaMB implements Serializable {

    private Empresa empresa;
    private Associado associado;
    private Associadoempresa associadoempresa;
    @EJB
    private EmpresaDao empresaDao;
    @EJB
    private AssociadoDao associadoDao;
    @EJB
    private AssociadoEmpresaDao associadoEmpresaDao;
    private List<Associadoempresa> listaAssociadoEmpresa;
    private String nome;
    private String telefone;
    private String matricula;
    private String email;
    private String cpf;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        empresa = (Empresa) session.getAttribute("empresa");
        session.removeAttribute("empresa");
        gerarListaAssociadoEmpresa();
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

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public AssociadoEmpresaDao getAssociadoEmpresaDao() {
        return associadoEmpresaDao;
    }

    public void setAssociadoEmpresaDao(AssociadoEmpresaDao associadoEmpresaDao) {
        this.associadoEmpresaDao = associadoEmpresaDao;
    }

    public List<Associadoempresa> getListaAssociadoEmpresa() {
        return listaAssociadoEmpresa;
    }

    public void setListaAssociadoEmpresa(List<Associadoempresa> listaAssociadoEmpresa) {
        this.listaAssociadoEmpresa = listaAssociadoEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void gerarListaAssociadoEmpresa() {
        listaAssociadoEmpresa = associadoEmpresaDao.list("select ae from Associadoempresa ae where ae.empresa.idempresa=" + empresa.getIdempresa()
                + " order by ae.idassociadoempresa");
        if (listaAssociadoEmpresa == null || listaAssociadoEmpresa.isEmpty()) {
            listaAssociadoEmpresa = new ArrayList<>();
        }
    }

    public String voltar() {
        return "consEmpresa";
    }

    public String pesquisar() {
        String sql = "";
        sql = "select ae from Associadoempresa ae where ae.empresa.idempresa=" + empresa.getIdempresa() + " and ae.associado.cliente.nome like '%" + nome + "%' ";
        if (cpf.length() > 0) {
            sql = sql + " and ae.associado.cliente.cpf='" + cpf + "' ";
        }
        if (email.length() > 0) {
            sql = sql + " and ae.associado.cliente.email='" + email + "' ";
        }
        if (telefone.length() > 0) {
            sql = sql + " and ae.associado.cliente.telefone='" + telefone + "' ";
        }
        if (matricula.length() > 0) {
            sql = sql + " and ae.associado.matricula='" + matricula + "' ";
        }
        sql = sql + " order by ae.idassociadoempresa";
        listaAssociadoEmpresa = associadoEmpresaDao.list(sql);
        if (listaAssociadoEmpresa == null || listaAssociadoEmpresa.isEmpty()) {
            listaAssociadoEmpresa = new ArrayList<>();
        }
        return "";
    }

    public String limpar() {
        gerarListaAssociadoEmpresa();
        matricula = "";
        nome = "";
        cpf = "";
        email = "";
        telefone = "";
        return "";
    }

    public String dependentes(Associado associado) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("associado", associado);
        return "consDependente";
    }

    public String financeiro(Associado associado) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("associado", associado);
        return "consContasReceber";
    }

    public void lancarContaReceber(Associado associado) {
        if (associado != null) {
            Contasreceber contasreceber = new Contasreceber();
            contasreceber.setCliente(associado.getCliente());
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contasreceber", contasreceber);
            options.put("contentWidth", 580);
            RequestContext.getCurrentInstance().openDialog("cadContasReceber", options, null);
        }
    }

}
