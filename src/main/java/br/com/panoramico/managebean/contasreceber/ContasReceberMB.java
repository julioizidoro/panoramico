package br.com.panoramico.managebean.contasreceber;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.BancoDao;
import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.dao.CobrancasParcelasDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.ProprietarioDao;
import br.com.panoramico.managebean.RelatorioErroBean;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.managebean.boleto.DadosBoletoBean;
import br.com.panoramico.managebean.boleto.LerRetornoItauBean;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Banco;
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
import org.jrimum.bopepo.Boleto;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class ContasReceberMB implements Serializable {

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
    private String tipoDocumento;
    private String funcaoBotaoBoleto;
    private boolean comboBoleto = false;
    private boolean habilitarComboBoleto = true;
    private boolean btnEnviarBoleto = false;
    private boolean btnGerarSegundaVia = false;
    private boolean btnGerarBoleto = false;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private AssociadoDao associadoDao;
    private boolean habilitarVoltarFinanceiro = false;
    private String tipo;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        associado = (Associado) session.getAttribute("associado");
        if (associado == null) {
        } else {
            habilitarVoltarFinanceiro = (boolean) session.getAttribute("habilitarVoltarFinanceiro");
            session.removeAttribute("habilitarVoltarFinanceiro");
        }
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

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getFuncaoBotaoBoleto() {
        return funcaoBotaoBoleto;
    }

    public void setFuncaoBotaoBoleto(String funcaoBotaoBoleto) {
        this.funcaoBotaoBoleto = funcaoBotaoBoleto;
    }

    public boolean isComboBoleto() {
        return comboBoleto;
    }

    public void setComboBoleto(boolean comboBoleto) {
        this.comboBoleto = comboBoleto;
    }

    public boolean isHabilitarComboBoleto() {
        return habilitarComboBoleto;
    }

    public void setHabilitarComboBoleto(boolean habilitarComboBoleto) {
        this.habilitarComboBoleto = habilitarComboBoleto;
    }

    public boolean isBtnEnviarBoleto() {
        return btnEnviarBoleto;
    }

    public void setBtnEnviarBoleto(boolean btnEnviarBoleto) {
        this.btnEnviarBoleto = btnEnviarBoleto;
    }

    public boolean isBtnGerarSegundaVia() {
        return btnGerarSegundaVia;
    }

    public void setBtnGerarSegundaVia(boolean btnGerarSegundaVia) {
        this.btnGerarSegundaVia = btnGerarSegundaVia;
    }

    public boolean isBtnGerarBoleto() {
        return btnGerarBoleto;
    }

    public void setBtnGerarBoleto(boolean btnGerarBoleto) {
        this.btnGerarBoleto = btnGerarBoleto;
    }

    public BancoDao getBancoDao() {
        return bancoDao;
    }

    public void setBancoDao(BancoDao bancoDao) {
        this.bancoDao = bancoDao;
    }

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public boolean isHabilitarVoltarFinanceiro() {
        return habilitarVoltarFinanceiro;
    }

    public void setHabilitarVoltarFinanceiro(boolean habilitarVoltarFinanceiro) {
        this.habilitarVoltarFinanceiro = habilitarVoltarFinanceiro;
    }

    public void gerarListaContasReceber() {
        if (associado == null || associado.getIdassociado() == null) {
            listaContasReceber = contasReceberDao.list("Select c from Contasreceber c where c.situacao='PAGAR' order by c.datavencimento");
        } else {
            listaContasReceber = contasReceberDao.list("Select c from Contasreceber c where c.situacao<>'CANCELADO' and c.situacao<>'PAGO'" + " and c.cliente.idcliente=" + associado.getCliente().getIdcliente() + " order by c.datavencimento");
        }
        if (listaContasReceber == null) {
            listaContasReceber = new ArrayList<Contasreceber>();
        }
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("Select c from Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }
    }

    public String novoCadastroContasReceber() {
        if (associado != null && associado.getIdassociado() != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("cliente", associado.getCliente());
        }
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 580);
        RequestContext.getCurrentInstance().openDialog("cadContasReceber", options, null);
        return "";
    }

    public void retornoDialogNovo(SelectEvent event) {
        Contasreceber conta = (Contasreceber) event.getObject();
        if (conta.getIdcontasreceber() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de uma conta a receber realizado com sucesso");
        }
        gerarListaContasReceber();
    }

    public void retornoDialogCancelamento(SelectEvent event) {
        Crcancelamento crcancelamento = (Crcancelamento) event.getObject();
        if (crcancelamento.getIdcrcancelamento() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cancelamento de uma conta a receber realizado com sucesso");
        }
        gerarListaContasReceber();
    }

    public void retornoDialogRecebimento(SelectEvent event) {
        Recebimento recebimento = (Recebimento) event.getObject();
        if (recebimento.getIdrecebimento() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Recebimento de uma conta a receber realizado com sucesso");
        }
        gerarListaContasReceber();
    }

    public void retornoDialogAlteracao(SelectEvent event) {
        Contasreceber contasreceber = (Contasreceber) event.getObject();
        if (contasreceber.getIdcontasreceber() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração de uma conta a receber realizado com sucesso");
        }
        gerarListaContasReceber();
    }

    public void editar(Contasreceber contasreceber) {
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

    public void filtrar() {
        btnGerarBoleto = false;
        btnGerarSegundaVia = false;
        btnEnviarBoleto = false;
        String sql = "Select c from Contasreceber c";
        if (!situacao.equalsIgnoreCase("sn") && (dataInicial == null && dataFinal == null)) {
            Mensagem.lancarMensagemInfo("Forneça um periodo na pesquisa", "");
        } else {
            if (cliente.getIdcliente() != null || !situacao.equalsIgnoreCase("sn") || dataInicial != null || dataFinal != null || (tipoDocumento != null && !tipoDocumento.equalsIgnoreCase("Selecione"))) {
                sql = sql + " where";
            }
            if (cliente.getIdcliente() != null) {
                sql = sql + " c.cliente.idcliente=" + cliente.getIdcliente();
                if (!situacao.equalsIgnoreCase("sn") || dataInicial != null || dataFinal != null || (tipoDocumento != null && !tipoDocumento.equalsIgnoreCase("Selecione"))) {
                    sql = sql + " and";
                }
            }
            if (!situacao.equalsIgnoreCase("sn")) {
                if (situacao.equalsIgnoreCase("VENCER")) {
                    sql = sql + " c.situacao='PAGAR' and c.datavencimento>='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
                } else if (situacao.equalsIgnoreCase("VENCIDOS")) {
                    if (dataFinal.before(new Date())) {
                        sql = sql + " c.situacao='PAGAR' and c.datavencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and c.datavencimento<'" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
                    } else {
                        sql = sql + " c.situacao='PAGAR' and c.datavencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and c.datavencimento<'" + Formatacao.ConvercaoDataSql(new Date()) + "'";
                    }
                    if ((tipoDocumento != null && !tipoDocumento.equalsIgnoreCase("Selecione"))) {
                        sql = sql + " and";
                    }
                } else {
                    sql = sql + " c.situacao='" + situacao + "' and c.datavencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and c.datavencimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
                    if ((tipoDocumento != null && !tipoDocumento.equalsIgnoreCase("Selecione"))) {
                        sql = sql + " and";
                    }
                }
            } else if ((dataInicial != null && dataFinal != null)) {
                sql = sql + " c.datavencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and c.datavencimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
                if ((tipoDocumento != null && !tipoDocumento.equalsIgnoreCase("Selecione"))) {
                    sql = sql + " and";
                }
            }
            if (!tipoDocumento.equalsIgnoreCase("Selecione")) {

                sql = sql + " c.tipopagamento='" + tipoDocumento + "'";
                if (tipoDocumento.equalsIgnoreCase("Boleto")) {
                    if (funcaoBotaoBoleto == null || funcaoBotaoBoleto.equalsIgnoreCase("Selecione")) {
                        Mensagem.lancarMensagemInfo("Atenção", "Selecione a função desejada.");
                    } else {
                        if (funcaoBotaoBoleto.equalsIgnoreCase("Gerar")) {
                            sql = sql + " and c.nossonumero='0' and c.situacao='PAGAR' and c.situacaoboleto='Novo' and c.datavencimento>='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
                            btnGerarBoleto = true;
                            btnGerarSegundaVia = false;
                            btnEnviarBoleto = false;
                        } else if (funcaoBotaoBoleto.equalsIgnoreCase("2º Via")) {
                            sql = sql + " and c.situacaoboleto='enviado' and c.situacao='PAGAR' and c.datavencimento>='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
                            btnGerarSegundaVia = true;
                            btnGerarBoleto = false;
                            btnEnviarBoleto = false;
                        } else if (funcaoBotaoBoleto.equalsIgnoreCase("Enviar")) {
                            sql = sql + " and c.nossonumero<>'0' and c.situacaoboleto='Gerado' and c.situacao='PAGAR' and c.datavencimento>='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
                            btnEnviarBoleto = true;
                            btnGerarBoleto = false;
                            btnGerarSegundaVia = false;
                        }
                    }
                }
            }
            sql = sql + " order by c.datavencimento";
            listaContasReceber = contasReceberDao.list(sql);
            Mensagem.lancarMensagemInfo("", "Filtrado com sucesso");
        }
    }

    public void limparFiltro() {
        cliente = null;
        situacao = null;
        dataFinal = null;
        dataInicial = null;
        btnGerarBoleto = false;
        btnGerarSegundaVia = false;
        btnEnviarBoleto = false;
        tipoDocumento = null;
        funcaoBotaoBoleto = null;
        comboBoleto = false;
        gerarListaCliente();
        gerarListaContasReceber();
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
        return "consTotalPagar";
    }

    public void cobranca(Contasreceber contasreceber) {
        Map<String, Object> options = new HashMap<String, Object>();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contasreceber", contasreceber);
        RequestContext.getCurrentInstance().openDialog("cobranca", options, null);
    }

    public Integer numeroCob(int contasreceber) {
        Integer cob = 0;
        String sql = "Select c From Cobrancasparcelas c Where c.contasreceber.idcontasreceber=" + contasreceber;
        List<Cobrancasparcelas> listaCobranca = cobrancasParcelasDao.list(sql);
        if (listaCobranca.size() > 0) {
            cob = listaCobranca.size();
        } else {
            cob = 0;
        }
        return cob;
    }

    public void retornoDialogCob(SelectEvent event) {
        gerarListaContasReceber();
    }

    public boolean habilitarPesquisa() {
        if (associado == null || associado.getIdassociado() == null) {
            return true;
        } else {
            return false;
        }
    }

    public void verificarComboBoleto() {
        if (tipoDocumento != null && tipoDocumento.equalsIgnoreCase("Boleto")) {
            comboBoleto = true;
            habilitarComboBoleto = false;
            btnGerarBoleto = false;
            btnGerarSegundaVia = false;
            btnEnviarBoleto = false;
        } else {
            habilitarComboBoleto = true;
            comboBoleto = false;
            btnGerarBoleto = false;
            btnGerarSegundaVia = false;
            btnEnviarBoleto = false;
        }
    }

    public String gerarBoleto() {
        List<Contasreceber> listaSelecionada = new ArrayList<>();
        for (int i = 0; i < listaContasReceber.size(); i++) {
            if ((listaContasReceber.get(i).isSelecionado())
                    && (listaContasReceber.get(i).getTipopagamento().equalsIgnoreCase("Boleto"))) {
                if (listaContasReceber.get(i).getSituacaoboleto().equalsIgnoreCase("Novo")) {
                    listaSelecionada.add(listaContasReceber.get(i));
                }
            }
        }
        if (listaSelecionada != null && listaSelecionada.size() > 0) {
            List<Boleto> listaBoletos = new ArrayList<Boleto>();
            if (listaSelecionada != null) {
                for (int i = 0; i < listaSelecionada.size(); i++) {
                    if (listaSelecionada.get(i).getTipopagamento().equalsIgnoreCase("Boleto")) {
                        listaBoletos.add(gerarClasseBoleto(listaSelecionada.get(i)));
                    }

                }
            }
            if (listaBoletos.size() > 0) {
                DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
                dadosBoletoBean.gerarPDFS(listaBoletos);
            }
        } else {
            Mensagem.lancarMensagemInfo("Atenção!", "Selecione uma conta a receber.");
            RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
            relatorioErroBean.iniciarRelatorioErro("Selecione uma conta a receber.");
        }
        return "";
    }

    public Boleto gerarClasseBoleto(Contasreceber conta) {
        associado = pegarEndereco(conta);
        proprietario = proprietarioDao.find(1);
        Banco banco = bancoDao.find("Select b From Banco b Where b.proprietario.idproprietario=" + proprietario.getIdproprietario());
        DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
        dadosBoletoBean.setAgencias(banco.getAgencia());
        dadosBoletoBean.setCarteiras(banco.getCarteira());
        dadosBoletoBean.setCnpjCedente(proprietario.getCnpj());
        dadosBoletoBean.setDataDocumento(new Date());
        dadosBoletoBean.setDigitoAgencias(banco.getDigitoagencia());
        dadosBoletoBean.setDigitoContas(banco.getDigitoconta());
        dadosBoletoBean.setDataVencimento(conta.getDatavencimento());
        dadosBoletoBean.setNomeCedente(proprietario.getRazaosocial());
        dadosBoletoBean.setNomeSacado(conta.getCliente().getNome());
        dadosBoletoBean.setNumeroContas(banco.getConta());
        dadosBoletoBean.setNumeroDocumentos(Formatacao.gerarNumeroDocumentoBoleto(conta.getNumerodocumento(), String.valueOf(conta.getNumeroparcela())));
        dadosBoletoBean.setValor(Formatacao.converterFloatBigDecimal(conta.getValorconta()));
        dadosBoletoBean.setNossoNumeros(dadosBoletoBean.getNumeroDocumentos());
        dadosBoletoBean.setEnderecoSacado(new Endereco());
        if (associado == null) {
        } else {
            dadosBoletoBean.getEnderecoSacado().setBairro(associado.getBairro());
            dadosBoletoBean.getEnderecoSacado().setCep(associado.getCep());
            dadosBoletoBean.getEnderecoSacado().setComplemento(associado.getComplemento());
            dadosBoletoBean.getEnderecoSacado().setLocalidade(associado.getCidade());
            dadosBoletoBean.getEnderecoSacado().setLogradouro(associado.getTipologradouro() + " " + associado.getLogradouro());
            dadosBoletoBean.getEnderecoSacado().setNumero(associado.getNumero());
            dadosBoletoBean.getEnderecoSacado().setUF(UnidadeFederativa.valueOfSigla(associado.getEstado()));
            if (associado.getDescotomensalidade() > 0) {
                dadosBoletoBean.setInstrucao1("ATE O VENCIMENTO DESCONTO DE R$ " + Formatacao.foramtarFloatString(associado.getDescotomensalidade()));
                dadosBoletoBean.setInstrucao2(banco.getObservacao1());
                dadosBoletoBean.setInstrucao3(banco.getObservacao2());
                dadosBoletoBean.setInstrucao4(banco.getObservacao3());
            } else {
                dadosBoletoBean.setInstrucao1(banco.getObservacao1());
                dadosBoletoBean.setInstrucao2(banco.getObservacao2());
                dadosBoletoBean.setInstrucao2(banco.getObservacao3());
            }
        }
        String juros = Formatacao.converterValorFloatReal(banco.getValorjuros());
        String multa = Formatacao.converterValorFloatReal(banco.getValormulta());
        dadosBoletoBean.criarBoleto(juros, multa);
        conta.setNossonumero(dadosBoletoBean.getNossoNumeros());
        conta.setSituacaoboleto("Gerado");
        contasReceberDao.update(conta);
        return dadosBoletoBean.getBoleto();
    }

    public Associado pegarEndereco(Contasreceber contasreceber) {
        Associado associadoo;
        List<Associado> listaAssociado = associadoDao.list("Select a from Associado a where a.cliente.idcliente=" + contasreceber.getCliente().getIdcliente());
        for (int i = 0; i < listaAssociado.size(); i++) {
            associadoo = listaAssociado.get(i);
            return associadoo;
        }
        return null;
    }

    public void gerarBoletoSegundaVia() {
        List<Contasreceber> listaSelecionada = new ArrayList<>();
        for (int i = 0; i < listaContasReceber.size(); i++) {
            if ((listaContasReceber.get(i).isSelecionado())
                    && (listaContasReceber.get(i).getTipopagamento().equalsIgnoreCase("Boleto"))) {
                if (listaContasReceber.get(i).getSituacaoboleto().equalsIgnoreCase("enviado")) {
                    listaSelecionada.add(listaContasReceber.get(i));
                }
            }
        }
        if (listaSelecionada != null && listaSelecionada.size() > 0) {
            List<Boleto> listaBoletos = new ArrayList<Boleto>();
            for (int i = 0; i < listaSelecionada.size(); i++) {
                listaBoletos.add(gerarClasseBoletoSegundaVia(listaSelecionada.get(i)));
            }
            if (listaBoletos.size() > 0) {
                DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
                dadosBoletoBean.gerarPDFS(listaBoletos);
            }
        } else {
            Mensagem.lancarMensagemInfo("Atenção!", "Selecione uma conta a receber.");
            RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
            relatorioErroBean.iniciarRelatorioErro("Selecione uma conta a receber.");
        }
    }

    public Boleto gerarClasseBoletoSegundaVia(Contasreceber conta) {
        proprietario = proprietarioDao.find(1);
        Banco banco = bancoDao.find("Select b From Banco b Where b.proprietario.idproprietario=" + proprietario.getIdproprietario());
        DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
        dadosBoletoBean.setAgencias(banco.getAgencia());
        dadosBoletoBean.setCarteiras(banco.getCarteira());
        dadosBoletoBean.setCnpjCedente(proprietario.getCnpj());
        dadosBoletoBean.setDataDocumento(new Date());
        dadosBoletoBean.setDigitoAgencias(banco.getDigitoagencia());
        dadosBoletoBean.setDigitoContas(banco.getDigitoconta());
        dadosBoletoBean.setDataVencimento(conta.getDatavencimento());
        dadosBoletoBean.setNomeCedente(proprietario.getRazaosocial());
        dadosBoletoBean.setNomeSacado(conta.getCliente().getNome());
        dadosBoletoBean.setNumeroContas(banco.getConta());
        dadosBoletoBean.setNumeroDocumentos(Formatacao.gerarNumeroDocumentoBoleto(conta.getNumerodocumento(), String.valueOf(conta.getNumeroparcela())));
        dadosBoletoBean.setValor(Formatacao.converterFloatBigDecimal(conta.getValorconta()));
        dadosBoletoBean.setNossoNumeros(dadosBoletoBean.getNumeroDocumentos());
        dadosBoletoBean.setEnderecoSacado(new Endereco());
        if (associado == null) {
        } else {
            dadosBoletoBean.getEnderecoSacado().setBairro(associado.getBairro());
            dadosBoletoBean.getEnderecoSacado().setCep(associado.getCep());
            dadosBoletoBean.getEnderecoSacado().setComplemento(associado.getComplemento());
            dadosBoletoBean.getEnderecoSacado().setLocalidade(associado.getCidade());
            dadosBoletoBean.getEnderecoSacado().setLogradouro(associado.getTipologradouro() + " " + associado.getLogradouro());
            dadosBoletoBean.getEnderecoSacado().setNumero(associado.getNumero());
            dadosBoletoBean.getEnderecoSacado().setUF(UnidadeFederativa.valueOfSigla(associado.getEstado()));
        }
        String juros = Formatacao.converterValorFloatReal(banco.getValorjuros());
        String multa = Formatacao.converterValorFloatReal(banco.getValormulta());
        dadosBoletoBean.criarBoleto(juros, multa);
        conta.setNossonumero(dadosBoletoBean.getNossoNumeros());
        conta.setSituacaoboleto("Gerado");
        contasReceberDao.update(conta);
        return dadosBoletoBean.getBoleto();
    }

    public String dialogBoletos() {
        List<Contasreceber> lista = new ArrayList<>();
        for (int i = 0; i < listaContasReceber.size(); i++) {
            if ((listaContasReceber.get(i).getTipopagamento().equalsIgnoreCase("Boleto")
                    && listaContasReceber.get(i).isSelecionado())) {
                lista.add(listaContasReceber.get(i));
            }
        }
        if (lista != null && lista.size() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            session.setAttribute("listaContas", lista);
            RequestContext.getCurrentInstance().openDialog("boletos");
        } else {
            Mensagem.lancarMensagemInfo("Atenção!", "Selecione uma conta a receber.");
        }
        return "";
    }

    public String pegarRecebimento(Contasreceber contasreceber) {
        String pago = "NÃO";
        if (contasreceber.getSituacao().equalsIgnoreCase("PAGO")) {
            pago = "SIM";
        }
        return pago;
    }

    public String voltarAssociado() {
        return "consAssociado";
    }

    public void uploadRetorno(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Sucesso! ", event.getFile().getFileName() + " carregado");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        UploadedFile uFile = event.getFile();
        lerRetorno(uFile);
    }

    public String lerRetorno(UploadedFile retorno) {
        try {
            LerRetornoItauBean lerRetornoItauBean = new LerRetornoItauBean(
                    Formatacao.converterUploadedFileToFile(retorno), retorno.getFileName());
        } catch (Exception ex) {
            Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void boletosRemessaRetorno(String tipo){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        session.setAttribute("tipo", tipo);
        RequestContext.getCurrentInstance().openDialog("relatorioRemessaRetorno", options, null);
    }
}
