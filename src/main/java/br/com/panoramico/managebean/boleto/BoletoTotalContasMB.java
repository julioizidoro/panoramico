/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.boleto;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.AssociadoEmpresaDao;
import br.com.panoramico.dao.BancoDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.EmpresaDao;
import br.com.panoramico.dao.FtpDadosDao;
import br.com.panoramico.dao.ProprietarioDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Associadoempresa;
import br.com.panoramico.model.Banco;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Empresa;
import br.com.panoramico.model.Ftpdados;
import br.com.panoramico.model.Proprietario;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.Ftp;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.jrimum.bopepo.Boleto;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@ViewScoped
public class BoletoTotalContasMB implements Serializable {

    private Contasreceber contasreceber;
    private List<Contasreceber> listaContasReceber;
    @EJB
    private ContasReceberDao contasReceberDao;
    private boolean selecionadoTodos;
    private List<Contasreceber> listaSelecionadas;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private ProprietarioDao proprietarioDao;
    private Proprietario proprietario;
    private List<Empresa> listaEmpresa;
    private StreamedContent stream;
    private Associado associado;
    private List<Associado> listaAssociado;
    @EJB
    private AssociadoDao associadoDao;
    @EJB
    private EmpresaDao empresaDao;
    @EJB
    private BancoDao bancoDao;
    private Banco banco;
    private boolean habilitar2via = false;
    private boolean habilitarGerarBoleto = true;
    private boolean habilitarEmpresa;
    private Empresa empresa;
    private float valorTotalEmpresa;
    private Ftpdados ftpDados;
    @EJB
    private FtpDadosDao ftpDadosDao;
    private String nomearquivo;
    private String nomeFtp;
    private Ftp ftp;
    private StreamedContent file;
    @EJB
    private AssociadoEmpresaDao associadoEmpresaDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        listaContasReceber = (List<Contasreceber>) session.getAttribute("listaContasReceber");
        habilitarEmpresa = (boolean) session.getAttribute("empresa");
        valorTotalEmpresa = (float) session.getAttribute("valorTotalEmpresa");
        session.removeAttribute("listaContasReceber");
        session.removeAttribute("empresa");
        proprietario = proprietarioDao.find(1);
        banco = bancoDao.find("select b from Banco b where b.proprietario.idproprietario=" + proprietario.getIdproprietario() + " and b.emitiboleto=1");
        nomearquivo = Formatacao.ConvercaoDataPadrao(new Date());
        nomeFtp = nomearquivo.substring(6, 10) + nomearquivo.substring(3, 5) + nomearquivo.substring(0, 2) + ".REM";
        ServletContext servletContext = (ServletContext) fc.getExternalContext().getContext();
        nomearquivo = servletContext.getRealPath("/remessa/" + nomeFtp);
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

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public boolean isSelecionadoTodos() {
        return selecionadoTodos;
    }

    public void setSelecionadoTodos(boolean selecionadoTodos) {
        this.selecionadoTodos = selecionadoTodos;
    }

    public List<Contasreceber> getListaSelecionadas() {
        return listaSelecionadas;
    }

    public void setListaSelecionadas(List<Contasreceber> listaSelecionadas) {
        this.listaSelecionadas = listaSelecionadas;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
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

    public List<Empresa> getListaEmpresa() {
        return listaEmpresa;
    }

    public void setListaEmpresa(List<Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
    }

    public StreamedContent getStream() {
        return stream;
    }

    public void setStream(StreamedContent stream) {
        this.stream = stream;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public List<Associado> getListaAssociado() {
        return listaAssociado;
    }

    public void setListaAssociado(List<Associado> listaAssociado) {
        this.listaAssociado = listaAssociado;
    }

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public EmpresaDao getEmpresaDao() {
        return empresaDao;
    }

    public void setEmpresaDao(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao;
    }

    public BancoDao getBancoDao() {
        return bancoDao;
    }

    public void setBancoDao(BancoDao bancoDao) {
        this.bancoDao = bancoDao;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public boolean isHabilitar2via() {
        return habilitar2via;
    }

    public void setHabilitar2via(boolean habilitar2via) {
        this.habilitar2via = habilitar2via;
    }

    public boolean isHabilitarGerarBoleto() {
        return habilitarGerarBoleto;
    }

    public void setHabilitarGerarBoleto(boolean habilitarGerarBoleto) {
        this.habilitarGerarBoleto = habilitarGerarBoleto;
    }

    public boolean isHabilitarEmpresa() {
        return habilitarEmpresa;
    }

    public void setHabilitarEmpresa(boolean habilitarEmpresa) {
        this.habilitarEmpresa = habilitarEmpresa;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public float getValorTotalEmpresa() {
        return valorTotalEmpresa;
    }

    public void setValorTotalEmpresa(float valorTotalEmpresa) {
        this.valorTotalEmpresa = valorTotalEmpresa;
    }

    public Ftpdados getFtpDados() {
        return ftpDados;
    }

    public void setFtpDados(Ftpdados ftpDados) {
        this.ftpDados = ftpDados;
    }

    public String getNomearquivo() {
        return nomearquivo;
    }

    public void setNomearquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

    public String getNomeFtp() {
        return nomeFtp;
    }

    public void setNomeFtp(String nomeFtp) {
        this.nomeFtp = nomeFtp;
    }

    public Ftp getFtp() {
        return ftp;
    }

    public void setFtp(Ftp ftp) {
        this.ftp = ftp;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public void selecionarTodasLista() {
        if (selecionadoTodos) {
            for (int i = 0; i < listaContasReceber.size(); i++) {
                listaContasReceber.get(i).setSelecionado(true);
            }
        } else {
            for (int i = 0; i < listaContasReceber.size(); i++) {
                listaContasReceber.get(i).setSelecionado(false);
            }
        }
    }

    public void gerarContas2via() {
        listaContasReceber = null;
        listaContasReceber = contasReceberDao.list("select c from Contasreceber c where c.enviado=true and c.tipopagamento='Boleto'");
        if (listaContasReceber == null || listaContasReceber.isEmpty()) {
            listaContasReceber = new ArrayList<Contasreceber>();
        }
        habilitar2via = true;
        habilitarGerarBoleto = false;
    }

    public void habilitarContas1via() {
        habilitar2via = false;
        habilitarGerarBoleto = true;
    }

    public String gerarBoleto() {
        if (habilitarEmpresa) {
            gerarBoletoEmpresa();
        } else {
            List<Boleto> listaBoletos = null;
            listaBoletos = new ArrayList<Boleto>();
            for (int i = 0; i < listaContasReceber.size(); i++) {
                listaBoletos.add(gerarClasseBoleto(listaContasReceber.get(i)));
            }
            if (listaBoletos.size() > 0) {
                DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
                dadosBoletoBean.gerarPDFS(listaBoletos);
            }
            return "";
        }
        return "";
    }

    public Boleto gerarClasseBoleto(Contasreceber conta) {
        associado = pegarEndereco(conta);
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
        conta.setSituacaoboleto("enviado");
        contasReceberDao.update(conta);
        return dadosBoletoBean.getBoleto();
    }

    public String gerarBoletoEmpresa() {
        Associado copia = null;
        float valorTotalPorEmpresa;
        List<Boleto> listaBoletos = null;
        listaBoletos = new ArrayList<Boleto>();
        for (int i = 0; i < listaContasReceber.size(); i++) {
            valorTotalPorEmpresa = 0.0f;
            if (associado == null) {
                associado = pegarEndereco(listaContasReceber.get(i));
                if (copia == null) {
                } else {
                    if (associado.getIdassociado() == copia.getIdassociado()) {
                        associado = null;
                    }
                }
            } else {
                copia = pegarEndereco(listaContasReceber.get(i));
                if (associado.getIdassociado() == copia.getIdassociado()) {
                    associado = null;
                } else {
                    associado = copia;
                }
            }
            if (associado == null) {
            } else {
                List<Associadoempresa> lista = associadoEmpresaDao.list("select a from Associadoempresa a where a.associado.idassociado=" + associado.getIdassociado());
                empresa = lista.get(0).getEmpresa();
                for (int j = 0; j < listaContasReceber.size(); j++) {
                    if (listaContasReceber.get(j).getCliente().getIdcliente() == lista.get(0).getAssociado().getCliente().getIdcliente()) {
                        valorTotalPorEmpresa = valorTotalPorEmpresa + listaContasReceber.get(j).getValorconta();
                    }
                }
                listaBoletos.add(gerarClasseBoletoEmpresa(listaContasReceber.get(i), valorTotalPorEmpresa));
            }
        }
        if (listaBoletos.size() > 0) {
            DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
            dadosBoletoBean.gerarPDFS(listaBoletos);
        }
        return "";
    }

    public Boleto gerarClasseBoletoEmpresa(Contasreceber conta, float valorTotal) {
        // associado = pegarEndereco(conta);
        DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
        dadosBoletoBean.setAgencias(banco.getAgencia());
        dadosBoletoBean.setCarteiras(banco.getCarteira());
        dadosBoletoBean.setCnpjCedente(proprietario.getCnpj());
        dadosBoletoBean.setDataDocumento(new Date());
        dadosBoletoBean.setDigitoAgencias(banco.getDigitoagencia());
        dadosBoletoBean.setDigitoContas(banco.getDigitoconta());
        dadosBoletoBean.setDataVencimento(conta.getDatavencimento());
        dadosBoletoBean.setNomeCedente(proprietario.getRazaosocial());
        dadosBoletoBean.setNomeSacado(empresa.getRazaosocial());
        dadosBoletoBean.setNumeroContas(banco.getConta());
        dadosBoletoBean.setNumeroDocumentos(Formatacao.gerarNumeroDocumentoBoleto(conta.getNumerodocumento(), String.valueOf(conta.getNumeroparcela())));
        dadosBoletoBean.setValor(Formatacao.converterFloatBigDecimal(valorTotal));
        dadosBoletoBean.setNossoNumeros(dadosBoletoBean.getNumeroDocumentos());
        dadosBoletoBean.setEnderecoSacado(new Endereco());

        dadosBoletoBean.getEnderecoSacado().setBairro(empresa.getBairro());
        dadosBoletoBean.getEnderecoSacado().setCep(empresa.getCep());
        dadosBoletoBean.getEnderecoSacado().setComplemento(empresa.getComplemento());
        dadosBoletoBean.getEnderecoSacado().setLocalidade(empresa.getCidade());
        dadosBoletoBean.getEnderecoSacado().setLogradouro(empresa.getTipologradouro() + " " + empresa.getLogradouro());
        dadosBoletoBean.getEnderecoSacado().setNumero(empresa.getNumero());
        dadosBoletoBean.getEnderecoSacado().setUF(UnidadeFederativa.valueOfSigla(empresa.getEstado()));

        String juros = Formatacao.converterValorFloatReal(banco.getValorjuros());
        String multa = Formatacao.converterValorFloatReal(banco.getValormulta());
        dadosBoletoBean.criarBoleto(juros, multa);
        conta.setNossonumero(dadosBoletoBean.getNossoNumeros());
        conta.setSituacaoboleto("enviado");
        contasReceberDao.update(conta);
        return dadosBoletoBean.getBoleto();
    }

    public String enviarBoleto() {
        List<Contasreceber> lista = new ArrayList<Contasreceber>();
        for (int i = 0; i < listaContasReceber.size(); i++) {
            if (listaContasReceber.get(i).isSelecionado()) {
                lista.add(listaContasReceber.get(i));
            }
        }
        if (lista.size() == 0) {
            lista = listaContasReceber;
        }
        if (lista.size() > 0) {
            ftpDados = ftpDadosDao.find(1);
            GerarArquivoRemessaItau arquivoRemessaItau = new GerarArquivoRemessaItau(lista, usuarioLogadoMB, proprietario, lista, banco, nomearquivo, nomeFtp, ftpDados);
            confirmarContas(lista);
            InputStream stream = procurarArquivo();
            file = new DefaultStreamedContent(stream, "", nomeFtp);
            FacesMessage msg = new FacesMessage("Enviado! ", "Disponivel para download, aperte novamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("Erro! ", "Nenhuma Conta Selecionada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return "";
    }

    public String calcularMultaJuros(float valor, float percentual) {
        Float calculo = valor * (percentual / 100);
        String scalculo = Formatacao.foramtarFloatString(calculo);
        return scalculo;
    }

    public void gerarListaEmpresa() {
        listaEmpresa = empresaDao.list("select e from Empresa e");
        if (listaEmpresa == null) {
            listaEmpresa = new ArrayList<Empresa>();
        }
        for (int i = 0; i < listaEmpresa.size(); i++) {
            if (listaEmpresa.get(i).getIdempresa() == 1) {

            }
        }
    }

    private void confirmarContas(List<Contasreceber> lista) {
        for (int i = 0; i < lista.size(); i++) {
            Contasreceber conta = lista.get(i);
            conta.setEnviado(true);
            conta.setSituacaoboleto("Enviado");
            contasReceberDao.update(conta);
            listaContasReceber.remove(conta);
        }
    }

    public Associado pegarEndereco(Contasreceber contasreceber) {
        Associado associadoo;
        listaAssociado = associadoDao.list("select a from Associado a where a.cliente.idcliente=" + contasreceber.getCliente().getIdcliente());
        for (int i = 0; i < listaAssociado.size(); i++) {
            associadoo = listaAssociado.get(i);
            return associadoo;
        }
        return null;
    }

    public Associado pegarAssociadoEmpresa(Associado associado) {
        Associado associadoo;
        listaAssociado = associadoDao.list("select a from Associado a where a.cliente.idcliente=" + contasreceber.getCliente().getIdcliente());
        for (int i = 0; i < listaAssociado.size(); i++) {
            associadoo = listaAssociado.get(i);
            return associadoo;
        }
        return null;
    }

    public InputStream procurarArquivo() {
        InputStream is = null;
        ftpDados = ftpDadosDao.find(1);
        ftp = new Ftp(ftpDados.getHost(), ftpDados.getUser(), ftpDados.getPassword());
        try {
            ftp.conectar();
            is = ftp.receberArquivo("", nomeFtp, "/panoramico/remessa/");
            ftp.desconectar();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return is;
    }

}
