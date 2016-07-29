/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contasreceber;

import br.com.panoramico.dao.RecebimentoDao;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Recebimento;
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

@Named
@ViewScoped
public class ConsRecebimentosMB implements Serializable{
    
    private Recebimento recebimento;
    private Contasreceber contasreceber;
    private List<Recebimento> listaRecebimentos;
    @EJB
    private RecebimentoDao recebimentoDao;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasreceber = (Contasreceber) session.getAttribute("contasreceber");
        session.removeAttribute("contasreceber");
        gerarListaRecebimentos();
    }

    public Recebimento getRecebimento() {
        return recebimento;
    }

    public void setRecebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public List<Recebimento> getListaRecebimentos() {
        return listaRecebimentos;
    }

    public void setListaRecebimentos(List<Recebimento> listaRecebimentos) {
        this.listaRecebimentos = listaRecebimentos;
    }

    public RecebimentoDao getRecebimentoDao() {
        return recebimentoDao;
    }

    public void setRecebimentoDao(RecebimentoDao recebimentoDao) {
        this.recebimentoDao = recebimentoDao;
    }
    
    
    public void gerarListaRecebimentos(){
        listaRecebimentos = recebimentoDao.list("Select r from Recebimento r JOIN Contasreceber c on r.contasreceber.idcontasreceber=c.idcontasreceber"
                + " where r.contasreceber.idcontasreceber=" + contasreceber.getIdcontasreceber() + " and c.numeroparcela='" + contasreceber.getNumeroparcela() + "'");
        if (listaRecebimentos == null) {
            listaRecebimentos = new ArrayList<Recebimento>();
        }  
    }
    
    public void fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
    } 
}  
