/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.exame;

import br.com.panoramico.dao.ExameConvidadoDao;
import br.com.panoramico.dao.ExameDao;
import br.com.panoramico.dao.MedicoDao;
import br.com.panoramico.dao.ParametrosDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Exame;
import br.com.panoramico.model.Exameconvidado;
import br.com.panoramico.model.Medico;
import br.com.panoramico.model.Parametros;
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
public class CadSolicitacaoExameConvidadoMB implements Serializable {

    private Exame exame;
    private Medico medico;
    private List<Medico> listaMedico;
    @EJB
    private MedicoDao medicoDao;
    @EJB
    private ExameDao exameDao;
    private Float totalPagar = 0.0f;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Float valorExame = 0.0f;
    private Float descontoExame = 0.0f;
    private List<Object> listaObjetos;
    private String associadoDependente = "";
    private String exibirNome = "Dependente";
    private boolean habilitarDependente = true;
    private boolean habilitarAssociado = false;
    private boolean habilitarNome = true;
    private Exameconvidado exameconvidado;
    @EJB
    private ExameConvidadoDao exameConvidadoDao;
    private Parametros parametros;
    @EJB
    private ParametrosDao parametrosDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        exame = (Exame) session.getAttribute("exame");
        session.removeAttribute("exame");
        getMedicoDefault();
        medico = exame.getMedico();
        valorExame = exame.getValor();
        descontoExame = exame.getDesconto();
        exameconvidado = exame.getExameconvidado();
        calcularTotal();
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public ExameDao getExameDao() {
        return exameDao;
    }

    public void setExameDao(ExameDao exameDao) {
        this.exameDao = exameDao;
    }

    public Exameconvidado getExameconvidado() {
        return exameconvidado;
    }

    public void setExameconvidado(Exameconvidado exameconvidado) {
        this.exameconvidado = exameconvidado;
    }

    public ExameConvidadoDao getExameConvidadoDao() {
        return exameConvidadoDao;
    }

    public void setExameConvidadoDao(ExameConvidadoDao exameConvidadoDao) {
        this.exameConvidadoDao = exameConvidadoDao;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public List<Medico> getListaMedico() {
        return listaMedico;
    }

    public void setListaMedico(List<Medico> listaMedico) {
        this.listaMedico = listaMedico;
    }

    public MedicoDao getMedicoDao() {
        return medicoDao;
    }

    public void setMedicoDao(MedicoDao medicoDao) {
        this.medicoDao = medicoDao;
    }

    public Float getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Float totalPagar) {
        this.totalPagar = totalPagar;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Float getValorExame() {
        return valorExame;
    }

    public void setValorExame(Float valorExame) {
        this.valorExame = valorExame;
    }

    public Float getDescontoExame() {
        return descontoExame;
    }

    public void setDescontoExame(Float descontoExame) {
        this.descontoExame = descontoExame;
    }

    public List<Object> getListaObjetos() {
        return listaObjetos;
    }

    public void setListaObjetos(List<Object> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public String getAssociadoDependente() {
        return associadoDependente;
    }

    public void setAssociadoDependente(String associadoDependente) {
        this.associadoDependente = associadoDependente;
    }

    public String getExibirNome() {
        return exibirNome;
    }

    public void setExibirNome(String exibirNome) {
        this.exibirNome = exibirNome;
    }

    public boolean isHabilitarDependente() {
        return habilitarDependente;
    }

    public void setHabilitarDependente(boolean habilitarDependente) {
        this.habilitarDependente = habilitarDependente;
    }

    public boolean isHabilitarAssociado() {
        return habilitarAssociado;
    }

    public void setHabilitarAssociado(boolean habilitarAssociado) {
        this.habilitarAssociado = habilitarAssociado;
    }

    public boolean isHabilitarNome() {
        return habilitarNome;
    }

    public void setHabilitarNome(boolean habilitarNome) {
        this.habilitarNome = habilitarNome;
    }

    public Parametros getParametros() {
        return parametros;
    }

    public void setParametros(Parametros parametros) {
        this.parametros = parametros;
    }

    public ParametrosDao getParametrosDao() {
        return parametrosDao;
    }

    public void setParametrosDao(ParametrosDao parametrosDao) {
        this.parametrosDao = parametrosDao;
    }

    public void salvar() {
        exame.setMedico(medico);
        exame.setValor(valorExame);
        exame.setDesconto(descontoExame);
        exame.setNomeCliente(exameconvidado.getEventoconvidados().getNome());
        exame.setMatricula("0");
        exame = exameDao.update(exame);
        exameconvidado.setExame(exame);
        exameConvidadoDao.update(exameconvidado);
        RequestContext.getCurrentInstance().closeDialog(exame);
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(new Exame());
    }

    public void calcularTotal() {
        totalPagar = valorExame - descontoExame;
    }

    public void getMedicoDefault() {
        parametros = parametrosDao.find(1);
        medico = medicoDao.find(parametros.getMedico());
    }
}
