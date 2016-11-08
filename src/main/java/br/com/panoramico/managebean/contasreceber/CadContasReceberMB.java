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
import br.com.panoramico.model.Planoconta;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadContasReceberMB implements Serializable{
    
    private Contasreceber contasreceber;
    private Planoconta planoconta;
    private Cliente cliente;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Contasreceber> listaContasReceber;
    private List<Planoconta> listaPlanoContas;
    private List<Cliente> listaCliente;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private PlanoContaDao planoContaDao;
    @EJB
    private ClienteDao clienteDao;
    private String tipoPagamento;
    private String numeroParcela;
    
    
    @PostConstruct
    public void init() { 
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasreceber = (Contasreceber) session.getAttribute("contasreceber");
        cliente = (Cliente) session.getAttribute("cliente");
        session.removeAttribute("cliente"); 
        session.removeAttribute("contasreceber");
        if (contasreceber == null) {
            contasreceber = new Contasreceber();
        } else {
            cliente = contasreceber.getCliente();
            planoconta = contasreceber.getPlanoconta();
            tipoPagamento = contasreceber.getTipopagamento();
            numeroParcela = contasreceber.getNumeroparcela();
        }
        gerarListaCliente();
        gerarListaPlanoConta();
    } 

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public Planoconta getPlanoconta() {
        return planoconta;
    }

    public void setPlanoconta(Planoconta planoconta) {
        this.planoconta = planoconta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public List<Contasreceber> getListaContasReceber() {
        return listaContasReceber;
    }

    public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
        this.listaContasReceber = listaContasReceber;
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

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
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

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public String getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(String numeroParcela) {
        this.numeroParcela = numeroParcela;
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
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Contasreceber());
    }
    
    public void salvar() {
        Float formataNParcela = Formatacao.formatarStringfloat(numeroParcela);
        contasreceber.setDatalancamento(new Date());
        if (formataNParcela > 1) {
            calculoParcelaMensal(formataNParcela, contasreceber);
        } else {
            contasreceber.setNumeroparcela(numeroParcela);
            contasreceber.setTipopagamento(tipoPagamento);
            contasreceber.setCliente(cliente);
            contasreceber.setPlanoconta(planoconta);
            contasreceber.setUsuario(usuarioLogadoMB.getUsuario());
            contasreceber.setSituacao("PAGAR");
            String mensagem = validarDados(contasreceber);
            if (mensagem.length() < 5) {
                if (contasreceber.getTipopagamento().equalsIgnoreCase("Boleto") && contasreceber.getIdcontasreceber() == null) {
                    contasreceber.setSituacaoboleto("Novo");
                    contasreceber.setEnviado(false);
                }else if(!contasreceber.getTipopagamento().equalsIgnoreCase("Boleto")  && contasreceber.getIdcontasreceber() == null){
                    contasreceber.setSituacaoboleto("Não");
                    contasreceber.setEnviado(false);
                }
                contasreceber = contasReceberDao.update(contasreceber);
                RequestContext.getCurrentInstance().closeDialog(contasreceber);
            } else {
                Mensagem.lancarMensagemInfo("", mensagem);
            }

        }
    }
    
    public String validarDados(Contasreceber contasreceber){
        String msg = "";
        if (contasreceber.getNumeroparcela().equalsIgnoreCase("")) {
            msg = msg + " Número de parcela não selecionada \r\n";
        }
        if (contasreceber.getDatalancamento() == null) {
            msg = msg + " Data de lançamento não informada \r\n";
        }
        if (contasreceber.getValorconta() == null) {
            msg = msg + " Valor da conta não informada \r\n";
        }
        if (contasreceber.getTipopagamento() == null || contasreceber.getTipopagamento().equalsIgnoreCase("")) {
            msg = msg + " Tipo de pagamento no informado \r\n";
        }
        return msg;
    }
    
    public void calculoParcelaMensal(Float nParcela, Contasreceber contasreceber) {
        float valorParcela = contasreceber.getValorconta() / nParcela;
        contasreceber.setValorconta(valorParcela);
        for (int i = 1; i <= nParcela; i++) {
            Contasreceber copia = new Contasreceber();
            copia = contasreceber;
            contasreceber.setNumeroparcela("" + i);
            contasreceber.setTipopagamento(tipoPagamento);
            contasreceber.setCliente(cliente);
            contasreceber.setPlanoconta(planoconta);
            contasreceber.setUsuario(usuarioLogadoMB.getUsuario());
            contasreceber.setSituacao("PAGAR");
            String mensagem = validarDados(contasreceber);
            if (mensagem.length() < 5) {
                if (contasreceber.getTipopagamento().equalsIgnoreCase("Boleto") && contasreceber.getIdcontasreceber() == null) {
                    contasreceber.setSituacaoboleto("Novo");
                    contasreceber.setEnviado(false);
                }else if(!contasreceber.getTipopagamento().equalsIgnoreCase("Boleto")  && contasreceber.getIdcontasreceber() == null){
                    contasreceber.setSituacaoboleto("Não");
                    contasreceber.setEnviado(false);
                } 
                contasreceber = contasReceberDao.update(contasreceber);
                Calendar c = new GregorianCalendar();
                c.setTime(copia.getDatavencimento());
                c.add(Calendar.MONTH, 1);
                Date data = c.getTime();
                copia.setDatavencimento(data);
                if (i < nParcela) {
                    contasreceber = new Contasreceber();
                    contasreceber = copia;
                }
            } else {
                Mensagem.lancarMensagemInfo("", mensagem);
            }

        }
        RequestContext.getCurrentInstance().closeDialog(contasreceber);
    }
}
