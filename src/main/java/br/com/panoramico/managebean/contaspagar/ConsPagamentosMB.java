/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contaspagar;

import br.com.panoramico.dao.PagamentoDao;
import br.com.panoramico.model.Contaspagar;
import br.com.panoramico.model.Pagamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import sun.misc.Request;

@Named
@ViewScoped
public class ConsPagamentosMB implements Serializable{
    
    private Pagamento pagamento;
    @EJB
    private PagamentoDao pagamentoDao;
    private Contaspagar contaspagar;
    private List<Pagamento> listaPagamentos;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contaspagar = (Contaspagar) session.getAttribute("contaspagar");
        session.removeAttribute("contaspagar");
        gerarListaPagamentos();
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

    public Contaspagar getContaspagar() {
        return contaspagar;
    }

    public void setContaspagar(Contaspagar contaspagar) {
        this.contaspagar = contaspagar;
    }

    public List<Pagamento> getListaPagamentos() {
        return listaPagamentos;
    }

    public void setListaPagamentos(List<Pagamento> listaPagamentos) {
        this.listaPagamentos = listaPagamentos;
    }
    
    public void gerarListaPagamentos(){
        listaPagamentos = pagamentoDao.list("Select p from Pagamento p where p.contaspagar.idcontaspagar=" + contaspagar.getIdcontaspagar());
        if (listaPagamentos == null) {
            listaPagamentos = new ArrayList<Pagamento>();
        }
    }
    
    public void fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
    }
}
