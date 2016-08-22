/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.boleto;

import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.model.Contasreceber;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class BoletoMB implements Serializable{
    
    private Contasreceber contasreceber;
    private List<Contasreceber> listaContasReceber;
    @EJB
    private ContasReceberDao contasReceberDao;
    private boolean selecionadoTodos;
    
    
    @PostConstruct
    public void init(){
        gerarListaContasReceber();
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
    
    
    
    public void selecionarTodasLista(){
        if (selecionadoTodos) {
            for (int i = 0; i < listaContasReceber.size(); i++) {
                listaContasReceber.get(i).setSelecionado(true);
            }
        }else{
            for (int i = 0; i < listaContasReceber.size(); i++) {
                listaContasReceber.get(i).setSelecionado(false);
            }
        }
    }
    
    public void gerarListaContasReceber(){
        listaContasReceber = contasReceberDao.list("Select c from Contasreceber c where c.enviado=false and c.tipopagamento='Boleto'");
        if (listaContasReceber == null || listaContasReceber.isEmpty()) {
            listaContasReceber = new ArrayList<Contasreceber>();
        }
    }
}
