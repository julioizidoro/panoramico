/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contasreceber;

import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.dao.CobrancasParcelasDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.ProprietarioDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.managebean.boleto.LerRetornoItauBean;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Cobrancasparcelas;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Crcancelamento;
import br.com.panoramico.model.Proprietario;
import br.com.panoramico.model.Recebimento;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

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
    private List<Cliente> listaCliente;
    @EJB
    private ClienteDao clienteDao;
    private Date dataInicial;
    private Date dataFinal;
    private String situacao;
    private List<Contasreceber> listaContasSelecionadas;
    @EJB
    private ProprietarioDao proprietarioDao;
    private Proprietario proprietario;
    @EJB
    private CobrancasParcelasDao cobrancasParcelasDao;
    private Cobrancasparcelas cobrancasparcelas;
    private Associado associado;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        associado = (Associado) session.getAttribute("associado");
        session.removeAttribute("associado");
        gerarListaContasReceber();
        gerarListaCliente();
        proprietario = proprietarioDao.find(1);
        proprietario.setCnpj("20.350.192/0001-73");
        proprietarioDao.update(proprietario);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
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

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public List<Contasreceber> getListaContasSelecionadas() {
        return listaContasSelecionadas;
    }

    public void setListaContasSelecionadas(List<Contasreceber> listaContasSelecionadas) {
        this.listaContasSelecionadas = listaContasSelecionadas;
    }

    public ProprietarioDao getProprietarioDao() {
        return proprietarioDao;
    }

    public void setProprietarioDao(ProprietarioDao proprietarioDao) {
        this.proprietarioDao = proprietarioDao;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }


    public CobrancasParcelasDao getCobrancasParcelasDao() {
        return cobrancasParcelasDao;
    }

    public void setCobrancasParcelasDao(CobrancasParcelasDao cobrancasParcelasDao) {
        this.cobrancasParcelasDao = cobrancasParcelasDao;
    }

    public Cobrancasparcelas getCobrancasparcelas() {
        return cobrancasparcelas;
    }

    public void setCobrancasparcelas(Cobrancasparcelas cobrancasparcelas) {
        this.cobrancasparcelas = cobrancasparcelas;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }
    
    
    
    public void gerarListaContasReceber(){
        if(associado==null || associado.getIdassociado()==null){
            listaContasReceber = contasReceberDao.list("Select c from Contasreceber c where c.situacao='PAGAR' order by c.datavencimento");
        }else{
            listaContasReceber = contasReceberDao.list("Select c from Contasreceber c where c.situacao<>'CANCELADO' and c.situacao<>'PAGO'"
                    + " and c.cliente.idcliente="+associado.getCliente().getIdcliente() + " order by c.datavencimento");
        } 
        if (listaContasReceber == null) {
            listaContasReceber = new ArrayList<Contasreceber>();
        }
    }
    
    
    public void gerarListaCliente(){
        listaCliente = clienteDao.list("Select c from Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }
    }
     
    public String novoCadastroContasReceber() {
        if(associado!=null && associado.getIdassociado()!=null){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("cliente", associado.getCliente());
        }
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
    
    public void retornoDialogRecebimento(SelectEvent event){
        Recebimento recebimento = (Recebimento) event.getObject();
        if (recebimento.getIdrecebimento()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Recebimento de uma conta a receber realizado com sucesso");
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
    
    
    public void editar(Contasreceber contasreceber){
        if (contasreceber != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contasreceber", contasreceber);
            options.put("contentWidth", 580);
            RequestContext.getCurrentInstance().openDialog("cadContasReceber", options, null);
        }
    }
    
    
    public String novoCancelamento(Contasreceber contasreceber) {
        if (contasreceber != null) {
           Map<String, Object> options = new HashMap<String, Object>();
           options.put("contentWidth", 400);
           FacesContext fc = FacesContext.getCurrentInstance();
           HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
           session.setAttribute("contasreceber", contasreceber);
           RequestContext.getCurrentInstance().openDialog("cadCancelamentoContasReceber", options, null);
        }
        return "";
    }
    
    public String novoRecebimento(Contasreceber contasreceber) {
        if (contasreceber != null) {
           Map<String, Object> options = new HashMap<String, Object>();
           options.put("contentWidth", 500);
           FacesContext fc = FacesContext.getCurrentInstance();
           HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
           session.setAttribute("contasreceber", contasreceber);
           RequestContext.getCurrentInstance().openDialog("cadRecebimento", options, null);
        }
        return "";
    }
    
    public String visualizarRecebimento(Contasreceber contasreceber) {
        if (contasreceber != null) {
           Map<String, Object> options = new HashMap<String, Object>();
           options.put("contentWidth", 500);
           FacesContext fc = FacesContext.getCurrentInstance();
           HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
           session.setAttribute("contasreceber", contasreceber);
           RequestContext.getCurrentInstance().openDialog("consRecebimentos", options, null);
        }
        return "";
    }
    
    public void filtrar(){
        String sql = "Select c from Contasreceber c";
        if (!situacao.equalsIgnoreCase("sn") && (dataInicial == null && dataFinal == null)) {
            Mensagem.lancarMensagemInfo("Forneça um periodo na pesquisa", "");
        }else{
            if (cliente.getIdcliente() != null  || !situacao.equalsIgnoreCase("sn") || dataInicial != null || dataFinal != null) {
                sql = sql + " where";
            }
            if (cliente.getIdcliente() != null) {
                sql = sql + " c.cliente.idcliente=" + cliente.getIdcliente();
                if (!situacao.equalsIgnoreCase("sn") || dataInicial != null || dataFinal != null) {
                    sql = sql + " and";
                }
            }
            if (!situacao.equalsIgnoreCase("sn")) {
                if (situacao.equalsIgnoreCase("VENCER")) {
                    sql = sql + " c.situacao='PAGAR' and c.datavencimento>='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
                }else if(situacao.equalsIgnoreCase("VENCIDOS")){
                    if (dataFinal.before(new Date())) {
                        sql = sql + " c.situacao='PAGAR' and c.datavencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and c.datavencimento<'" +
                         Formatacao.ConvercaoDataSql(dataFinal) + "'";
                    }else{
                        sql = sql + " c.situacao='PAGAR' and c.datavencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and c.datavencimento<'" +
                            Formatacao.ConvercaoDataSql(new Date()) + "'";
                    }
                }else{
                    sql = sql + " c.situacao='" + situacao + "' and c.datavencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and c.datavencimento<='" 
                        + Formatacao.ConvercaoDataSql(dataFinal) + "'";
                }
            }else if ((dataInicial != null && dataFinal != null)) {
                sql = sql + " c.datavencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and c.datavencimento<='"
                        + Formatacao.ConvercaoDataSql(dataFinal) + "'";
            }
            sql = sql + " order by c.datavencimento";
            listaContasReceber = contasReceberDao.list(sql);
            Mensagem.lancarMensagemInfo("", "Filtrado com sucesso");
        }
    }
    
    public void limparFiltro(){
        cliente = null;
        situacao = null;
        dataFinal = null;
        dataInicial = null;
        gerarListaCliente();
        gerarListaContasReceber();
    }
    
    public String consBoleto() {
        listaContasSelecionadas = new ArrayList<Contasreceber>();
        for (int i = 0; i < listaContasReceber.size(); i++) {
            if (listaContasReceber.get(i).isSelecionado()) {
                listaContasSelecionadas.add(listaContasReceber.get(i));
            }
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        session.setAttribute("listaContasSelecionadas", listaContasSelecionadas);
        RequestContext.getCurrentInstance().openDialog("boletos", options, null);
        return "";
    }
    
    public String uploadBoleto() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 500);
        RequestContext.getCurrentInstance().openDialog("uploadBoleto", options, null);
        return "";
    }
    
     public String uploadRetorno(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Sucesso! ", event.getFile().getFileName() + " carregado");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        UploadedFile uFile = event.getFile();
        lerRetorno(uFile);
        RequestContext.getCurrentInstance().closeDialog(null);
        return "consContasReceber";
    }
    
    public String lerRetorno(UploadedFile retorno) {
        try {
            LerRetornoItauBean lerRetornoItauBean = new LerRetornoItauBean(
                    Formatacao.converterUploadedFileToFile(retorno));
        } catch (Exception ex) {
            Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }  
    
    public String voltar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return "consContasReceber";
    }
    
    
    public String novoRelatorio() { 
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 580);
        RequestContext.getCurrentInstance().openDialog("imprimirContasRecebidas", options, null);
        return "";
    }
    
    
    public String totalPagar() {
        RequestContext.getCurrentInstance().openDialog("consTotalPagar");
        return "";
    }
    
    
    public void cobranca(Contasreceber contasreceber) {
        Map<String, Object> options = new HashMap<String, Object>();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contasreceber", contasreceber);
        RequestContext.getCurrentInstance().openDialog("cobranca", options, null);
    }
    
    public Integer numeroCob(int contasreceber){
        Integer cob = 0;
        String sql = "Select c From Cobrancasparcelas c Where c.contasreceber.idcontasreceber=" + contasreceber;
        List<Cobrancasparcelas> listaCobranca = cobrancasParcelasDao.list(sql);
        if (listaCobranca.size() > 0) {
            cob = listaCobranca.size();
        }else{
            cob = 0;
        }
        return cob;
    }
    
    public void retornoDialogCob(SelectEvent event){
        gerarListaContasReceber();
    }
    
    
    public boolean habilitarPesquisa(){
        if(associado==null ||  associado.getIdassociado()==null){
            return true;
        }else{
            return false;
        }
    }
}
