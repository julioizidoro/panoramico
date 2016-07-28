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
import br.com.panoramico.model.Planoconta;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class ContasPagarMB implements Serializable{
    
    private Contaspagar contaspagar;
    @EJB
    private ContasPagarDao contasPagarDao;
    private List<Contaspagar> listaContasPagar;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Planoconta planoconta;
    private List<Planoconta> listaPlanoContas;
    @EJB
    private PlanoContaDao planoContaDao;
    private Date dataInicial;
    private Date dataFinal;
    
    
    @PostConstruct
    public void init(){
        gerarListaContasPagar();
        gerarListaPlanoContas();
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
    
    public Planoconta getPlanoconta() {
        return planoconta;
    }

    public void setPlanoconta(Planoconta planoconta) {
        this.planoconta = planoconta;
    }

    public List<Planoconta> getListaPlanoContas() {
        return listaPlanoContas;
    }

    public void setListaPlanoContas(List<Planoconta> listaPlanoContas) {
        this.listaPlanoContas = listaPlanoContas;
    }

    public PlanoContaDao getPlanoContaDao() {
        return planoContaDao;
    }

    public void setPlanoContaDao(PlanoContaDao planoContaDao) {
        this.planoContaDao = planoContaDao;
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

    public List<Contaspagar> getListaContasPagar() {
        return listaContasPagar;
    }

    public void setListaContasPagar(List<Contaspagar> listaContasPagar) {
        this.listaContasPagar = listaContasPagar;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }
    
    public void gerarListaContasPagar(){
        listaContasPagar = contasPagarDao.list("Select c from Contaspagar c");
        if (listaContasPagar == null) {
            listaContasPagar = new ArrayList<Contaspagar>();
        }
    }
    
    public void gerarListaPlanoContas(){
        listaPlanoContas = planoContaDao.list("Select p from Planoconta p");
        if (listaPlanoContas == null) {
            listaPlanoContas = new ArrayList<Planoconta>();
        }
    }
    
    public void retornoDialogNovo(SelectEvent event){
        Contaspagar conta = (Contaspagar) event.getObject();
        if (conta.getIdcontaspagar()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de uma conta a receber realizado com sucesso");
        }
        gerarListaContasPagar();
    }
}
