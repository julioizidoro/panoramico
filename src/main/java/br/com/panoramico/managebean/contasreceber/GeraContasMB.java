/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contasreceber;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.PlanoDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Plano;
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
public class GeraContasMB implements Serializable{
    
    private Associado associado;
    private Contasreceber contasreceber;
    private List<Associado> listaAssociado;
    private Plano plano;
    private List<Plano> listaPlano;
    @EJB
    private AssociadoDao associadoDao;
    @EJB
    private PlanoDao planoDao;
    @EJB
    private ContasReceberDao contasReceberDao;
    private Integer mesReferencia = 0;
    private String nome;
    private String matricula;
    private String cpf;
    private String anoReferencia;
    private boolean habilitarVoltarFinanceiro;
    
    
    @PostConstruct
    public void init(){
        gerarListaAssociado();
    }

    
    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public List<Associado> getListaAssociado() {
        return listaAssociado;
    }

    public void setListaAssociado(List<Associado> listaAssociado) {
        this.listaAssociado = listaAssociado;
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

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public PlanoDao getPlanoDao() {
        return planoDao;
    }

    public void setPlanoDao(PlanoDao planoDao) {
        this.planoDao = planoDao;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public Integer getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(Integer mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAnoReferencia() {
        return anoReferencia;
    }

    public void setAnoReferencia(String anoReferencia) {
        this.anoReferencia = anoReferencia;
    }

    public boolean isHabilitarVoltarFinanceiro() {
        return habilitarVoltarFinanceiro;
    }

    public void setHabilitarVoltarFinanceiro(boolean habilitarVoltarFinanceiro) {
        this.habilitarVoltarFinanceiro = habilitarVoltarFinanceiro;
    }
    
    
    
    
    
    public void gerarListaAssociado(){
        String sql = "Select a From Associado a Where a.situacao<>'Inativo' order by a.idassociado";
        listaAssociado = associadoDao.list(sql);
        if (listaAssociado == null || listaAssociado.isEmpty()) {
            listaAssociado = new ArrayList<>();
        }
    }
    
    
    public float pegarValorManutencao(Associado associado){
        float valorAssociado = associado.getPlano().getValor();
        float valorDependente = 0.0f;
        float valorTotal = 0.0f;
        valorTotal = (valorAssociado + valorDependente) - associado.getDescotomensalidade();
        return valorTotal;
    }
    
    
    public void lancarContaReceber(Associado associado){
        if (associado != null) {
            Contasreceber contasreceber = new Contasreceber();
            contasreceber.setValorconta(pegarValorManutencao(associado));
            contasreceber.setCliente(associado.getCliente());
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contasreceber", contasreceber);
            options.put("contentWidth", 580);
            RequestContext.getCurrentInstance().openDialog("cadContasReceber", options, null);
        }
    }
    
    
    public String pesquisar() {
        String sql = "";
        sql = "Select a from Associado a where a.cliente.nome like '%" + nome + "%' ";
        if (cpf.length() > 0) {
            sql = sql + " and a.cliente.cpf='" + cpf + "' ";
        }
        if (mesReferencia > 0) {
            sql = sql + " and a.mes='" + mesReferencia + "' ";
        }
        if (anoReferencia.length() > 0) {
            sql = sql + " and a.ano='" + anoReferencia + "' ";
        }
        if (matricula.length() > 0) {
            sql = sql + " and a.matricula='" + matricula + "' ";
        }
        sql = sql + " order by a.cliente.nome";
        listaAssociado = associadoDao.list(sql);
        if (listaAssociado == null || listaAssociado.isEmpty()) {
            listaAssociado = new ArrayList<>();
        }
        return "";
    }
    
    
    public String limpar() {
        gerarListaAssociado();
        matricula = "";
        nome = "";
        cpf = "";
        mesReferencia = 0;
        anoReferencia = "";
        return "";
    }
      
    public String financeiro(Associado associado) {
        habilitarVoltarFinanceiro = false;
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("associado", associado);
        session.setAttribute("habilitarVoltarFinanceiro", habilitarVoltarFinanceiro);
        return "consContasReceber";
    }
    
}
