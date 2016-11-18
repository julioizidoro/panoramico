/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contasreceber;

import br.com.panoramico.dao.CobrancaDao;
import br.com.panoramico.dao.CobrancasParcelasDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.HistoricoCobrancaDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Cobranca;
import br.com.panoramico.model.Cobrancasparcelas;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Historicocobranca;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import sun.misc.Request;

@Named
@ViewScoped
public class CobrancasMB implements Serializable{
    
    private Cobranca cobranca;
    private Cobrancasparcelas cobrancasparcelas;
    private Historicocobranca historicocobranca;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private CobrancaDao cobrancaDao;
    @EJB  
    private CobrancasParcelasDao cobrancasParcelasDao;
    private Contasreceber contasreceber;
    private List<Historicocobranca> listaHistoricoCobranca;
    private List<Cobrancasparcelas> listaCobrancasParcelas;
    private List<Cobranca> listaCobranca;
    @EJB
    private HistoricoCobrancaDao historicoCobrancaDao;
    private boolean adicionado = false;
    private boolean habilitarCobrança = true;
    private boolean habilitarCadCobranca = false;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private boolean noveDigito1 = false;
    private boolean noveDigito2 = false;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasreceber = (Contasreceber) session.getAttribute("contasreceber");
        gerarListaCobrancasParcelas();
        gerarListaHistoricoCobranca();
        if (historicocobranca == null) {
            historicocobranca = new Historicocobranca();
        }
        if (cobranca == null) {
            cobranca = new Cobranca();
        }else{
            if (cobranca.getFone1().length() > 12) {
                noveDigito1 = true;
            }
            if (cobranca.getFone2().length() > 12) {
                noveDigito2 = true;
            }
        }
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    public Cobrancasparcelas getCobrancasparcelas() {
        return cobrancasparcelas;
    }

    public void setCobrancasparcelas(Cobrancasparcelas cobrancasparcelas) {
        this.cobrancasparcelas = cobrancasparcelas;
    }

    public Historicocobranca getHistoricocobranca() {
        return historicocobranca;
    }

    public void setHistoricocobranca(Historicocobranca historicocobranca) {
        this.historicocobranca = historicocobranca;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public CobrancaDao getCobrancaDao() {
        return cobrancaDao;
    }

    public void setCobrancaDao(CobrancaDao cobrancaDao) {
        this.cobrancaDao = cobrancaDao;
    }

    public CobrancasParcelasDao getCobrancasParcelasDao() {
        return cobrancasParcelasDao;
    }

    public void setCobrancasParcelasDao(CobrancasParcelasDao cobrancasParcelasDao) {
        this.cobrancasParcelasDao = cobrancasParcelasDao;
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public List<Historicocobranca> getListaHistoricoCobranca() {
        return listaHistoricoCobranca;
    }

    public void setListaHistoricoCobranca(List<Historicocobranca> listaHistoricoCobranca) {
        this.listaHistoricoCobranca = listaHistoricoCobranca;
    }

    public List<Cobrancasparcelas> getListaCobrancasParcelas() {
        return listaCobrancasParcelas;
    }

    public void setListaCobrancasParcelas(List<Cobrancasparcelas> listaCobrancasParcelas) {
        this.listaCobrancasParcelas = listaCobrancasParcelas;
    }

    public List<Cobranca> getListaCobranca() {
        return listaCobranca;
    }

    public void setListaCobranca(List<Cobranca> listaCobranca) {
        this.listaCobranca = listaCobranca;
    }

    public HistoricoCobrancaDao getHistoricoCobrancaDao() {
        return historicoCobrancaDao;
    }

    public void setHistoricoCobrancaDao(HistoricoCobrancaDao historicoCobrancaDao) {
        this.historicoCobrancaDao = historicoCobrancaDao;
    }

    public boolean isAdicionado() {
        return adicionado;
    }

    public void setAdicionado(boolean adicionado) {
        this.adicionado = adicionado;
    }

    public boolean isHabilitarCobrança() {
        return habilitarCobrança;
    }

    public void setHabilitarCobrança(boolean habilitarCobrança) {
        this.habilitarCobrança = habilitarCobrança;
    }

    public boolean isHabilitarCadCobranca() {
        return habilitarCadCobranca;
    }

    public void setHabilitarCadCobranca(boolean habilitarCadCobranca) {
        this.habilitarCadCobranca = habilitarCadCobranca;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public boolean isNoveDigito1() {
        return noveDigito1;
    }

    public void setNoveDigito1(boolean noveDigito1) {
        this.noveDigito1 = noveDigito1;
    }

    public boolean isNoveDigito2() {
        return noveDigito2;
    }

    public void setNoveDigito2(boolean noveDigito2) {
        this.noveDigito2 = noveDigito2;
    }

    
    
    
    public void gerarListaHistoricoCobranca(){
        if (cobranca == null || cobranca.getIdcobranca() == null) {
            listaHistoricoCobranca = new ArrayList<>();
            adicionado = false;
        }else{
            listaHistoricoCobranca = historicoCobrancaDao.list("Select h From Historicocobranca h Where h.cobranca.idcobranca="
            + cobranca.getIdcobranca());
            if (listaHistoricoCobranca == null || listaHistoricoCobranca.isEmpty()) {
                listaHistoricoCobranca = new ArrayList<Historicocobranca>();
            }
            adicionado = true;
        }
    }
    
    public void gerarListaCobrancasParcelas(){
        listaCobrancasParcelas = cobrancasParcelasDao.list("Select c From Cobrancasparcelas c Where c.contasreceber.idcontasreceber="
        + contasreceber.getIdcontasreceber());
        if (listaCobrancasParcelas == null || listaCobrancasParcelas.isEmpty()) {
            listaCobrancasParcelas = new ArrayList<Cobrancasparcelas>();
        }
        for (int i = 0; i < listaCobrancasParcelas.size(); i++) {
            cobranca = listaCobrancasParcelas.get(i).getCobranca();
        }
    }
    
    
    public void salvarCobranca(){
        cobranca = cobrancaDao.update(cobranca);
        adicionado = true;
    }
    
    
    public void novaCobranca(){
        habilitarCobrança = false;
        habilitarCadCobranca = true;
        historicocobranca = new Historicocobranca();
    }
    
    public void editarCobranca(Historicocobranca historicocobranca){
        habilitarCobrança = false;
        habilitarCadCobranca = true;
        this.historicocobranca = historicocobranca;
    }
    
    public void excluirHistorico(Historicocobranca historicocobranca){
        historicoCobrancaDao.remove(historicocobranca.getIdhistoricocobranca());
        gerarListaHistoricoCobranca();
    }
    
    public void salvarHistoricoCobranca(){
        if (historicocobranca.getIdhistoricocobranca() == null) {
            historicocobranca.setCobranca(cobranca);
            historicocobranca.setUsuario(usuarioLogadoMB.getUsuario());
            historicocobranca = historicoCobrancaDao.update(historicocobranca);
            cobrancasparcelas = new Cobrancasparcelas();
            cobrancasparcelas.setCobranca(cobranca);
            cobrancasparcelas.setContasreceber(contasreceber);
            cobrancasparcelas = cobrancasParcelasDao.update(cobrancasparcelas);
            gerarListaHistoricoCobranca();
            historicocobranca = new Historicocobranca();
            habilitarCadCobranca = false;
            habilitarCobrança = true;
        }else{
            historicocobranca = historicoCobrancaDao.update(historicocobranca);
            gerarListaHistoricoCobranca();
            historicocobranca = new Historicocobranca();
            habilitarCadCobranca = false;
            habilitarCobrança = true;  
        }
    }
    
    public void cancelar(){
        historicocobranca = new Historicocobranca();
        habilitarCadCobranca = false;
        habilitarCobrança = true;
    }
    
    
    public void fecharCob(){
        RequestContext.getCurrentInstance().closeDialog(null);
    }
    
    
    
    public String habilitarNoveDigito1(){
        if (noveDigito1) {
            return "(99)999999999";
        }else{
            return "(99)99999999";
        }
    }
    
    
    public String habilitarNoveDigito2(){
        if (noveDigito2) {
            return "(99)999999999";
        }else{
            return "(99)99999999";
        }
    }
    
}
