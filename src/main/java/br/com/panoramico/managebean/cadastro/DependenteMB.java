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
public class DependenteMB implements Serializable {

    @EJB
    private DependenteDao dependenteDao;
    private Dependente dependente;
    private List<Dependente> listaDependente;
    @EJB
    private AssociadoDao associadoDao;
    private Associado associado;
    private String nome;
    private String nomeassociado;
    private String matricula;
    private String email;
    private String telefone;
    private boolean habilitarVoltar = false;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        associado = (Associado) session.getAttribute("associado");
        if (associado == null) {
        }else{
            habilitarVoltar = (boolean) session.getAttribute("habilitarVoltar");
            session.removeAttribute("habilitarVoltar");
        }
        session.removeAttribute("associado");
        gerarListaDependente();
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

    public List<Dependente> getListaDependente() {
        return listaDependente;
    }

    public void setListaDependente(List<Dependente> listaDependente) {
        this.listaDependente = listaDependente;
    }

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public String getNomeassociado() {
        return nomeassociado;
    }

    public void setNomeassociado(String nomeassociado) {
        this.nomeassociado = nomeassociado;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHabilitarVoltar() {
        return habilitarVoltar;
    }

    public void setHabilitarVoltar(boolean habilitarVoltar) {
        this.habilitarVoltar = habilitarVoltar;
    }
    
    

    public String novoCadastroDependente() {
        if(associado!=null && associado.getIdassociado()!=null){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("associado", associado);
        }
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 550);
        RequestContext.getCurrentInstance().openDialog("cadDependente", options, null);
        return "";
    }

    public void retornoDialogNovo(SelectEvent event) {
        Dependente dependente = (Dependente) event.getObject();
        if (dependente.getIddependente() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de Dependente realizado com sucesso");
        }
        gerarListaDependente();
    }

    public void retornoDialogAlteracao(SelectEvent event) {
        Dependente dependente = (Dependente) event.getObject();
        if (dependente.getIddependente() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração do Dependente realizado com sucesso");
        }
        gerarListaDependente();
    }

    public void editar(Dependente dependente) {
        if (dependente != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("dependente", dependente);
            options.put("contentWidth", 550);
            RequestContext.getCurrentInstance().openDialog("cadDependente", options, null);
        }
    }

    public void gerarListaDependente() {
        if (associado != null && associado.getIdassociado()!=null) {
            listaDependente = dependenteDao.list("Select c from Dependente c where c.situacao='Ativo' "
                    + " and c.associado.idassociado=" + associado.getIdassociado() + " order by c.nome");
        } else {
            listaDependente = dependenteDao.list("Select c from Dependente c where c.situacao='Ativo' "
                    + " order by c.nome");
        }
        if (listaDependente == null) {
            listaDependente = new ArrayList<Dependente>();
        }
    }

    public void excluir(Dependente dependente) {
        dependenteDao.remove(dependente.getIddependente());
        Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
        gerarListaDependente();
    }

    public void pesquisar() {
        String sql = "Select c from Dependente c where c.nome like '%" + nome + "%'";
        if (nomeassociado != null && nomeassociado.length() > 0) {
            sql = sql + " and c.associado.cliente.nome like '%" + associado + "%'";
        }
        if (email != null && email.length() > 0) {
            sql = sql + " and c.email like '%" + email + "%'";
        }
        if (telefone != null && telefone.length() > 0) {
            sql = sql + " and c.telefone='" + telefone + "'";
        }
        if (matricula != null && matricula.length() > 0) {
            sql = sql + " and c.matricula='" + matricula + "'";
        }
        sql = sql + " order by c.nome";
        listaDependente = dependenteDao.list(sql);
        if (listaDependente == null) {
            listaDependente = new ArrayList<Dependente>();
        }
    }

    public void limpar() {
        nome = "";
        email = null;
        telefone = null;
        associado = null;
        matricula = null;
        gerarListaDependente();
    }
    
    public boolean habilitarPesquisa(){
        if(associado!=null && associado.getIdassociado()!=null){
            return false;
        }else{
            return true;
        }
    }
    
    public String voltarAssociado(){
        return "consAssociado";
    }
}
