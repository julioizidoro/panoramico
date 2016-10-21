/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contasreceber;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Contasreceber;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class totalSerPagoMB implements Serializable{
    
    private Contasreceber contasreceber;
    private List<Contasreceber> listaContasAssociados;
    private List<Contasreceber> listaContasAssociadosEmpresa;
    @EJB
    private ContasReceberDao contasReceberDao;
    private Associado associado;
    private List<Associado> listaAssociado;
    @EJB
    private AssociadoDao associadoDao;
    private float valorTotalEmpresa;
    private float valorTotalAssociado;
    
    
    @PostConstruct
    public void init(){
        gerarListaContasAssociadoEmpresa();
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public List<Contasreceber> getListaContasAssociados() {
        return listaContasAssociados;
    }

    public void setListaContasAssociados(List<Contasreceber> listaContasAssociados) {
        this.listaContasAssociados = listaContasAssociados;
    }

    public List<Contasreceber> getListaContasAssociadosEmpresa() {
        return listaContasAssociadosEmpresa;
    }

    public void setListaContasAssociadosEmpresa(List<Contasreceber> listaContasAssociadosEmpresa) {
        this.listaContasAssociadosEmpresa = listaContasAssociadosEmpresa;
    }

    

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
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
    
    

    public float getValorTotalEmpresa() {
        return valorTotalEmpresa;
    }

    public void setValorTotalEmpresa(float valorTotalEmpresa) {
        this.valorTotalEmpresa = valorTotalEmpresa;
    }

    public float getValorTotalAssociado() {
        return valorTotalAssociado;
    }

    public void setValorTotalAssociado(float valorTotalAssociado) {
        this.valorTotalAssociado = valorTotalAssociado;
    }
    
    
    
    public void gerarListaContasAssociado(){
        listaContasAssociados = contasReceberDao.list("Select c From Contasreceber c");
    }
    
    
    public void gerarListaAssociado(){
        listaAssociado = associadoDao.list("Select a From Associado a Join Associadoempresa ae on a.idassociado=ae.associado.idassociado");
    }
    
    public void gerarListaContasAssociadoEmpresa(){
        listaContasAssociadosEmpresa = contasReceberDao.list("Select c From Contasreceber c Join Associado a on c.cliente.idcliente=a.cliente.idcliente"
                + " Join Associadoempresa ae on a.idassociado="
                + " ae.associado.idassociado Where c.situacao='PAGAR'");
        if (listaContasAssociadosEmpresa == null || listaContasAssociadosEmpresa.isEmpty()) {
            listaContasAssociadosEmpresa = new ArrayList<>();
        }
        for (int i = 0; i < listaContasAssociadosEmpresa.size(); i++) {
            valorTotalEmpresa = valorTotalEmpresa + listaContasAssociadosEmpresa.get(i).getValorconta();
        }
    }
}
