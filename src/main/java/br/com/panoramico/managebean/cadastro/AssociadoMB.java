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
import br.com.panoramico.model.Contasreceber;
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
public class AssociadoMB implements Serializable {

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
    private String situacao;
    private boolean habilitarVoltar = false;
    private boolean habilitarVoltarFinanceiro = false;
    private boolean temsql = false;
    private int idAssociado=0;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        sql = (String) session.getAttribute("sql");
        Integer idA = (Integer) session.getAttribute("idAssociado");
        if (idA == null) {
            idA = 0;
        }
        idAssociado = idA;
        if (idAssociado>0){
            sql = null;
             gerarListaAssociado();
        }else if (sql != null) {
            temsql = true;
            gerarListaAssociado();
        }
        session.removeAttribute("sql");
        session.removeAttribute("idAssociado");
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

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public boolean isHabilitarVoltar() {
        return habilitarVoltar;
    }

    public void setHabilitarVoltar(boolean habilitarVoltar) {
        this.habilitarVoltar = habilitarVoltar;
    }

    public boolean isHabilitarVoltarFinanceiro() {
        return habilitarVoltarFinanceiro;
    }

    public void setHabilitarVoltarFinanceiro(boolean habilitarVoltarFinanceiro) {
        this.habilitarVoltarFinanceiro = habilitarVoltarFinanceiro;
    }

    public boolean isTemsql() {
        return temsql;
    }

    public void setTemsql(boolean temsql) {
        this.temsql = temsql;
    }
    
    

    public void gerarListaAssociado() {
        if (!temsql) {
            if (idAssociado>0){
                sql = "Select a From Associado a where a.cliente.idcliente>" + (idAssociado-5) + " order by a.cliente.idcliente DESC";
                listaAssociado = associadoDao.list(sql);
            }
        }else {
            listaAssociado = associadoDao.list(sql);
        }
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
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("cadAssociado", options, null);
        return "";
    }

    public void retornoDialogNovo(SelectEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        sql = (String) session.getAttribute("sql");
        Integer idA = (Integer) session.getAttribute("idAssociado");
        if (idA == null) {
            idA = 0;
        }
        idAssociado = idA;
        session.removeAttribute("sql");
        session.removeAttribute("idAssociado");
        Associado associado = (Associado) event.getObject();
        if (associado.getIdassociado() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de Associado realizado com sucesso");
        }
        gerarListaAssociado();
    }

    public void retornoDialogAlteracao(SelectEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        sql = (String) session.getAttribute("sql");
        session.removeAttribute("sql");
        idAssociado=0;
        Associado associado = (Associado) event.getObject();
        if (associado.getIdassociado() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração do Associado realizado com sucesso");
        }
        gerarListaAssociado();
    }

    public void editar(Associado associado) {
        if (associado != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("associado", associado);
            session.setAttribute("sql", sql);
            options.put("contentWidth", 580);
            options.put("closable", false);
            RequestContext.getCurrentInstance().openDialog("cadAssociado", options, null);
        }
    }

    public void associarEmpresa(Associado associado) {
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
            options.put("closable", false);
            RequestContext.getCurrentInstance().openDialog("cadAssociadoEmpresa", options, null);
        }
    }

    public void excluir(Associado associado) {
        List<Dependente> listaDependente = dependenteDao.list("Select d from Dependente d where d.associado.idassociado=" + associado.getIdassociado());
        if (listaDependente == null || listaDependente.isEmpty()) {
            associadoDao.remove(associado.getIdassociado());
            Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
            listaAssociado.remove(associado);
        } else {
            Mensagem.lancarMensagemInfo("Atenção", "este associado não pode ser excluido");
        }
    }

    public void limpar() {
        temsql = false;
        matricula = "";
        nome = "";
        cpf = "";
        email = "";
        telefone = "";
        situacao="";
        listaAssociado = new ArrayList<>();
        gerarListaAssociado();
    }

    public String pesquisar() {
        sql = "Select a from Associado a where a.cliente.nome like '%" + nome + "%' ";
        temsql= true;
        if (cpf!=null && cpf.length() > 0) {
            sql = sql + " and a.cliente.cpf='" + cpf + "' ";
        }
        if (email!=null && email.length() > 0) {
            sql = sql + " and a.cliente.email='" + email + "' ";
        }
        if (telefone!=null && telefone.length() > 0) {
            sql = sql + " and a.cliente.telefone='" + telefone + "' ";
        }
        if (matricula!=null && matricula.length() > 0) {
            sql = sql + " and a.matricula='" + matricula + "' ";
        }
        if (situacao!=null && situacao.length() > 0) {
            sql = sql + " and a.situacao='" + situacao + "'";
        }
        sql = sql + " order by a.cliente.nome";
        gerarListaAssociado();
        return "";
    }
 
    public String dependentes(Associado associado) {
        habilitarVoltar = true;
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("associado", associado);
        session.setAttribute("habilitarVoltar", habilitarVoltar);
        return "consDependente";
    }
    
    public String financeiro(Associado associado) {
        habilitarVoltarFinanceiro = true;
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("associado", associado);
        session.setAttribute("habilitarVoltarFinanceiro", habilitarVoltarFinanceiro);
        return "consContasReceber";
    }
    
    public void lancarContaReceber(Associado associado){
        if (associado != null) {
            Contasreceber contasreceber = new Contasreceber();
//            contasreceber.setValorconta(associado.getPlano().getValor() + gerarContaDependentes(associado));
            contasreceber.setCliente(associado.getCliente());
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contasreceber", contasreceber);
            options.put("contentWidth", 580);
            RequestContext.getCurrentInstance().openDialog("cadContasReceber", options, null);
        }
    }
    
//    public float gerarContaDependentes(Associado associado){
//        float valorTotal = 0.0f;
//        List<Dependente> lista = dependenteDao.list("Select d From Dependente d Where d.associado.idassociado=" + associado.getIdassociado());
//        if (lista == null || lista.isEmpty()) {
//            lista = new ArrayList<>();
//        }
//        for (int i = 0; i < lista.size(); i++) {
//            valorTotal = valorTotal + lista.get(i).getPlano().getValor();
//        }
//        return valorTotal;
//    }
    
    public String pegarIcone(Associado associado){
        if (associado.getSituacao().equalsIgnoreCase("Ativo")) {
            return "fa fa-toggle-on";
        }else{
            return "fa fa-toggle-off";
        }
    }
    
    public String retornarSituacao(Associado associado){
        if (associado.getSituacao().equalsIgnoreCase("Ativo")) {
            return "Associado Ativo";
        }else{
            return "Associado Inativo";
        }
    }
    
    public void desativarAssociado(Associado associado) {
        if (associado.getSituacao().equalsIgnoreCase("Ativo")) {
            associado.setSituacao("Inativo");
            Mensagem.lancarMensagemInfo("Desativado", "com sucesso");
        }else{
            associado.setSituacao("Ativo");
            Mensagem.lancarMensagemInfo("Ativado", "com sucesso");
        }
        associadoDao.update(associado);
        gerarListaAssociado();
    }
}
