/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contaspagar;

import br.com.panoramico.dao.ContasPagarDao;
import br.com.panoramico.dao.PagamentoDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Contaspagar;
import br.com.panoramico.model.Pagamento;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
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
public class CadPagamentoMB implements Serializable{
    
    private Contaspagar contaspagar;
    private Pagamento pagamento;
    @EJB
    private PagamentoDao pagamentoDao;
    @EJB
    private ContasPagarDao contasPagarDao;
    private Float juros = 0.0f;
    private Float desagio = 0.0f;
    private Float valorTotalPago = 0.0f;
    private Float valorPagar = 0.0f;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contaspagar = (Contaspagar) session.getAttribute("contaspagar");
        if (pagamento == null) {
            pagamento = new Pagamento();
        }
        if (contaspagar != null) {
            valorPagar = contaspagar.getValor();
            valorTotalPago = contaspagar.getValor();
        }
    }

    public Contaspagar getContaspagar() {
        return contaspagar;
    }

    public void setContaspagar(Contaspagar contaspagar) {
        this.contaspagar = contaspagar;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public PagamentoDao getPagamentoDao() {
        return pagamentoDao;
    }

    public void setPagamentoDao(PagamentoDao pagamentoDao) {
        this.pagamentoDao = pagamentoDao;
    }

    public ContasPagarDao getContasPagarDao() {
        return contasPagarDao;
    }

    public void setContasPagarDao(ContasPagarDao contasPagarDao) {
        this.contasPagarDao = contasPagarDao;
    }

    public Float getJuros() {
        return juros;
    }

    public void setJuros(Float juros) {
        this.juros = juros;
    }

    public Float getDesagio() {
        return desagio;
    }

    public void setDesagio(Float desagio) {
        this.desagio = desagio;
    }

    public Float getValorTotalPago() {
        return valorTotalPago;
    }

    public void setValorTotalPago(Float valorTotalPago) {
        this.valorTotalPago = valorTotalPago;
    }

    public Float getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(Float valorPagar) {
        this.valorPagar = valorPagar;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }
    
    public void calcularTotal(){
        valorTotalPago = (valorPagar + juros) - desagio;
    }
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Pagamento());
    }
    
     public void salvar(){
        Float totalPago = 0.0f;
        if (valorTotalPago > contaspagar.getValor() && juros == 0.0f) {
            Mensagem.lancarMensagemInfo("Atenção", "valor total a receber acima do valor da conta sem constar o valor de juros");
        }else{
            if (valorTotalPago >= contaspagar.getValor()) {
                List<Pagamento> listaPagamento = pagamentoDao.list("Select p from Pagamento p where p.contasreceber.idcontasreceber="+ contaspagar.getIdcontaspagar());
                for (int i = 0; i < listaPagamento.size(); i++) {
                    totalPago = totalPago + listaPagamento.get(i).getValorpago();
                }
                contaspagar.setValor(totalPago);
            }else{
                contaspagar.setValor(contaspagar.getValor() - valorTotalPago);
            }
            contasPagarDao.update(contaspagar);
            pagamento.setJuros(juros);
            pagamento.setDesagio(desagio);
            pagamento.setValorpago(valorTotalPago);
            pagamento.setUsuario(usuarioLogadoMB.getUsuario());
            pagamento.setContaspagar(contaspagar);
            pagamento = pagamentoDao.update(pagamento);
            RequestContext.getCurrentInstance().closeDialog(pagamento);
        }
    }
}
