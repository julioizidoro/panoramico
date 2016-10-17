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
public class CadContasPagarMB implements Serializable{
    
    private Contaspagar contaspagar;
    @EJB
    private ContasPagarDao contasPagarDao;
    private List<Planoconta> listaPlanoContas;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Planoconta planoconta;
    private String tipoPagamento;
    private String numeroParcela;
    @EJB
    private PlanoContaDao planoContaDao;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contaspagar = (Contaspagar) session.getAttribute("contaspagar");
        if (contaspagar == null) {
            contaspagar = new Contaspagar();
        }else{
            planoconta = contaspagar.getPlanoconta();
            numeroParcela = contaspagar.getNumeroparcela();
            tipoPagamento = contaspagar.getFormapagamento();
        }
        gerarListaPlanoContas();
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

    public List<Planoconta> getListaPlanoContas() {
        return listaPlanoContas;
    }

    public void setListaPlanoContas(List<Planoconta> listaPlanoContas) {
        this.listaPlanoContas = listaPlanoContas;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Planoconta getPlanoconta() {
        return planoconta;
    }

    public void setPlanoconta(Planoconta planoconta) {
        this.planoconta = planoconta;
    }

    public PlanoContaDao getPlanoContaDao() {
        return planoContaDao;
    }

    public void setPlanoContaDao(PlanoContaDao planoContaDao) {
        this.planoContaDao = planoContaDao;
    }
    
    
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Contaspagar());
    }
    
    public void gerarListaPlanoContas(){
        listaPlanoContas = planoContaDao.list("Select p from Planoconta p");
        if (listaPlanoContas == null) {
            listaPlanoContas = new ArrayList<Planoconta>();
        }
    }
    
    public void salvar() {
        Float formataNParcela = Formatacao.formatarStringfloat(numeroParcela);
        if (formataNParcela > 1) {
             calcularParcelamentoMensal(formataNParcela, contaspagar);
        } else {
            contaspagar.setFormapagamento(tipoPagamento);
            contaspagar.setNumeroparcela(numeroParcela);
            contaspagar.setPlanoconta(planoconta);
            contaspagar.setUsuario(usuarioLogadoMB.getUsuario());
            contaspagar.setSituacao("PAGAR");
            String mensagem = validarDados(contaspagar);
            if (mensagem.length() < 5) {
                contaspagar = contasPagarDao.update(contaspagar);
                RequestContext.getCurrentInstance().closeDialog(contaspagar);
            } else {
                Mensagem.lancarMensagemInfo("", mensagem);
            }
        }
    }
    
    public String validarDados(Contaspagar contaspagar){
        String msg = "";
        if (contaspagar.getNumeroparcela().equalsIgnoreCase("")) {
            msg = msg + " Número de parcela não selecionada \r\n";
        }
        if (contaspagar.getDatalancamento() == null) {
            msg = msg + " Data de lançamento não informada \r\n";
        }
        if (contaspagar.getDatavencimento() == null) {
            msg = msg + " Data de vencimento não informada \r\n";
        }
        if (contaspagar.getValor()== null) {
            msg = msg + " Valor da conta não informada \r\n";
        }
        return msg;
    }
    
    public void calcularParcelamentoMensal(Float nParcela, Contaspagar contaspagar) {
        for (int i = 1; i <= nParcela; i++) {
            Contaspagar copia = new Contaspagar();
            copia = contaspagar;
            contaspagar.setFormapagamento(tipoPagamento);
            contaspagar.setNumeroparcela("" + i);
            contaspagar.setPlanoconta(planoconta);
            contaspagar.setUsuario(usuarioLogadoMB.getUsuario());
            contaspagar.setSituacao("PAGAR");
            String mensagem = validarDados(contaspagar);
            if (mensagem.length() < 5) {
                contaspagar = contasPagarDao.update(contaspagar);
                Calendar c = new GregorianCalendar();
                c.setTime(copia.getDatalancamento());
                c.add(Calendar.MONTH, 1);
                Date data = c.getTime();
                copia.setDatavencimento(data);
                if (i < nParcela) {
                    contaspagar = new Contaspagar();
                    contaspagar = copia;
                }
            } else {
                Mensagem.lancarMensagemInfo("", mensagem);
            }
        }
        RequestContext.getCurrentInstance().closeDialog(contaspagar);
    }
}
 