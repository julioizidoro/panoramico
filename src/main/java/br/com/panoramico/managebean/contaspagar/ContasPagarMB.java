/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contaspagar;

import br.com.panoramico.dao.ContasPagarDao;
import br.com.panoramico.dao.PlanoContaDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Contaspagar;
import br.com.panoramico.model.Pagamento;
import br.com.panoramico.model.Planoconta;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class ContasPagarMB implements Serializable{
    
    private Contaspagar contaspagar;
    @EJB
    private ContasPagarDao contasPagarDao;
    private List<Contaspagar> listaContasPagar;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Planoconta planoconta;
    private List<Planoconta> listaPlanoContas;
    @EJB
    private PlanoContaDao planoContaDao;
    private Date dataInicial;
    private Date dataFinal;
    
    
    @PostConstruct
    public void init(){
        gerarListaContasPagar();
        gerarListaPlanoContas();
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }
    
    public Planoconta getPlanoconta() {
        return planoconta;
    }

    public void setPlanoconta(Planoconta planoconta) {
        this.planoconta = planoconta;
    }

    public List<Planoconta> getListaPlanoContas() {
        return listaPlanoContas;
    }

    public void setListaPlanoContas(List<Planoconta> listaPlanoContas) {
        this.listaPlanoContas = listaPlanoContas;
    }

    public PlanoContaDao getPlanoContaDao() {
        return planoContaDao;
    }

    public void setPlanoContaDao(PlanoContaDao planoContaDao) {
        this.planoContaDao = planoContaDao;
    }
    
    public Contaspagar getContaspagar() {
        return contaspagar;
    }

    public void setContaspagar(Contaspagar contaspagar) {
        this.contaspagar = contaspagar;
    }

    public ContasPagarDao getContasPagarDao() {
        return contasPagarDao;
    }

    public void setContasPagarDao(ContasPagarDao contasPagarDao) {
        this.contasPagarDao = contasPagarDao;
    }

    public List<Contaspagar> getListaContasPagar() {
        return listaContasPagar;
    }

    public void setListaContasPagar(List<Contaspagar> listaContasPagar) {
        this.listaContasPagar = listaContasPagar;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }
    
    public void gerarListaContasPagar(){
        listaContasPagar = contasPagarDao.list("Select c from Contaspagar c");
        if (listaContasPagar == null) {
            listaContasPagar = new ArrayList<Contaspagar>();
        }
    }
    
    public void gerarListaPlanoContas(){
        listaPlanoContas = planoContaDao.list("Select p from Planoconta p");
        if (listaPlanoContas == null) {
            listaPlanoContas = new ArrayList<Planoconta>();
        }
    }
    
    public void retornoDialogNovo(SelectEvent event){
        Contaspagar conta = (Contaspagar) event.getObject();
        if (conta.getIdcontaspagar()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de uma conta a receber realizado com sucesso");
        }
        gerarListaContasPagar();
    }
    
    public void retornoDialogPagamento(SelectEvent event){
        Pagamento pagamento = (Pagamento) event.getObject();
        if (pagamento.getIdpagamento()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Pagamento de uma conta a pagar realizado com sucesso");
        }
        gerarListaContasPagar();
    }
    
    public void retornoDialogAlteracao(SelectEvent event){
        Contaspagar contaspagar = (Contaspagar) event.getObject();
        if (contaspagar.getIdcontaspagar()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração de uma conta a pagar realizado com sucesso");
        }
        gerarListaContasPagar();
    }
    
    
    public void editar(Contaspagar contaspagar){
        if (contaspagar != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contaspagar", contaspagar);
            options.put("contentWidth", 580);
            RequestContext.getCurrentInstance().openDialog("cadContasPagar", options, null);
        }
    }

    
    public String novoPagamento(Contaspagar contaspagar) {
        if (contaspagar != null) {
           Map<String, Object> options = new HashMap<String, Object>();
           options.put("contentWidth", 500);
           FacesContext fc = FacesContext.getCurrentInstance();
           HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
           session.setAttribute("contaspagar", contaspagar);
           RequestContext.getCurrentInstance().openDialog("cadPagamentos", options, null);
        }
        return "";
    }
    
    public String novoCadastroContasPagar() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 580);
        RequestContext.getCurrentInstance().openDialog("cadContasPagar", options, null);
        return "";
    }
    
    public String visualizarPagamento(Contaspagar contaspagar) {
        if (contaspagar != null) {
           Map<String, Object> options = new HashMap<String, Object>();
           options.put("contentWidth", 500);
           FacesContext fc = FacesContext.getCurrentInstance();
           HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
           session.setAttribute("contaspagar", contaspagar);
           RequestContext.getCurrentInstance().openDialog("consPagamentos", options, null);
        }
        return "";
    }
    
    public void excluir(Contaspagar contaspagar){
        contasPagarDao.remove(contaspagar.getIdcontaspagar());
        Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
        gerarListaContasPagar();
    }
    
    public void filtrar(){
        String sql = "Select c from Contaspagar c";
        if (planoconta.getIdplanoconta() != null || dataInicial != null || dataFinal != null) {
            sql = sql + " where";
        }
        if (planoconta.getIdplanoconta() != null) {
            sql = sql + " c.planoconta.idplanoconta=" + planoconta.getIdplanoconta();
            if (dataInicial != null || dataFinal != null) {
                sql = sql + " and";
            }
        }
        if (dataInicial != null && dataFinal != null) {
            sql = sql + " c.datavencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and c.datavencimento<='"
                    + Formatacao.ConvercaoDataSql(dataFinal) + "'";
        }
        listaContasPagar = contasPagarDao.list(sql);
        Mensagem.lancarMensagemInfo("", "Filtrado com sucesso");
    }
    
    public void limparFiltro(){
        planoconta = null;
        dataFinal = null;
        dataInicial = null;
        gerarListaContasPagar();
        gerarListaPlanoContas();
    }

}
