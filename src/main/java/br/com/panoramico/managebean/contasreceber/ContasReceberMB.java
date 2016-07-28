/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contasreceber;

import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.PlanoContaDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Crcancelamento;
import br.com.panoramico.model.Evento;
import br.com.panoramico.model.Planoconta;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class ContasReceberMB implements Serializable{
    
    private Contasreceber contasreceber;
    private List<Contasreceber> listaContasReceber;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private ContasReceberDao contasReceberDao;
    private Cliente cliente;
    private Planoconta planoconta;
    private List<Planoconta> listaPlanoContas;
    private List<Cliente> listaCliente;
    @EJB
    private PlanoContaDao planoContaDao;
    @EJB
    private ClienteDao clienteDao;
    private Date dataInicial;
    private Date dataFinal;
    
    
    @PostConstruct
    public void init(){
        gerarListaContasReceber();
        gerarListaCliente();
        gerarListaPlanoConta();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public PlanoContaDao getPlanoContaDao() {
        return planoContaDao;
    }

    public void setPlanoContaDao(PlanoContaDao planoContaDao) {
        this.planoContaDao = planoContaDao;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
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

    
    
    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public List<Contasreceber> getListaContasReceber() {
        return listaContasReceber;
    }

    public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
        this.listaContasReceber = listaContasReceber;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }
    
    
    public void gerarListaContasReceber(){
        listaContasReceber = contasReceberDao.list("Select c from Contasreceber c");
        if (listaContasReceber == null) {
            listaContasReceber = new ArrayList<Contasreceber>();
        }
    }
    
    public void gerarListaPlanoConta(){
        listaPlanoContas = planoContaDao.list("Select p from Planoconta p");
        if (listaPlanoContas == null) {
            listaPlanoContas = new ArrayList<Planoconta>();
        }
    }
    
    public void gerarListaCliente(){
        listaCliente = clienteDao.list("Select c from Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }
    }
    
    public String novoCadastroContasReceber() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 580);
        RequestContext.getCurrentInstance().openDialog("cadContasReceber", options, null);
        return "";
    }
    
    public void retornoDialogNovo(SelectEvent event){
        Contasreceber conta = (Contasreceber) event.getObject();
        if (conta.getIdcontasreceber()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de uma conta a receber realizado com sucesso");
        }
        gerarListaContasReceber();
    }
    
    public void retornoDialogCancelamento(SelectEvent event){
        Crcancelamento crcancelamento = (Crcancelamento) event.getObject();
        if (crcancelamento.getIdcrcancelamento()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cancelamento de uma conta a receber realizado com sucesso");
        }
        gerarListaContasReceber();
    }
    
    public void retornoDialogAlteracao(SelectEvent event){
        Contasreceber contasreceber = (Contasreceber) event.getObject();
        if (contasreceber.getIdcontasreceber()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração de uma conta a receber realizado com sucesso");
        }
        gerarListaContasReceber();
    }
}
