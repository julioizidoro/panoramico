/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.exame;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.DependenteDao;
import br.com.panoramico.dao.ExameAssociadoDao;
import br.com.panoramico.dao.ExameDao;
import br.com.panoramico.dao.ExameDependenteDao;
import br.com.panoramico.dao.MedicoDao;
import br.com.panoramico.dao.ParametrosDao;
import br.com.panoramico.dao.PlanoContaDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Dependente;
import br.com.panoramico.model.Exame;
import br.com.panoramico.model.Exameassociado;
import br.com.panoramico.model.Examedependente;
import br.com.panoramico.model.Medico;
import br.com.panoramico.model.Parametros;
import br.com.panoramico.model.Planoconta;
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
public class CadSolicitacaoExameMB implements Serializable {

    private Exame exame;
    private Medico medico;
    private Contasreceber contasreceber;
    private Associado associado;
    private Exameassociado exameassociado;
    private Examedependente examedependente;
    private Dependente dependente;
    private List<Dependente> listaDependente;
    private List<Associado> listaAssociado;
    private List<Medico> listaMedico;
    @EJB
    private DependenteDao dependenteDao;
    @EJB
    private MedicoDao medicoDao;
    @EJB
    private ExameAssociadoDao exameAssociadoDao;
    @EJB
    private ExameDependenteDao exameDependenteDao;
    @EJB
    private ExameDao exameDao;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private AssociadoDao associadoDao;
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
    private Planoconta planoconta;
    @EJB
    private PlanoContaDao planoContaDao;
    private Parametros parametros;
    @EJB
    private ParametrosDao parametrosDao;

    @PostConstruct
    public void init() {
        gerarListaMedico();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        exame = (Exame) session.getAttribute("exame");
        session.removeAttribute("exame");
        getMedicoDefault();
        if (exame == null) {
            exame = new Exame();
            exameassociado = new Exameassociado();
            examedependente = new Examedependente();
        } else {
            pegarValores();
            medico = exame.getMedico();
            valorExame = exame.getValor();
            descontoExame = exame.getDesconto();
            calcularTotal();
            if (exameassociado != null) {
                associado = exameassociado.getAssociado();
                exibirNome = "Associado.";
                habilitarAssociado = true;
                habilitarDependente = false;
                associadoDependente = "Associado.";
                gerarListaAssociados();
            } else {
                dependente = examedependente.getDependente();
                habilitarDependente = true;
                exibirNome = "Dependente";
                habilitarAssociado = false;
                associadoDependente = "Dependente";
                gerarListaDependentes();
            }
        }
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
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

    public ExameDao getExameDao() {
        return exameDao;
    }

    public void setExameDao(ExameDao exameDao) {
        this.exameDao = exameDao;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public Exameassociado getExameassociado() {
        return exameassociado;
    }

    public void setExameassociado(Exameassociado exameassociado) {
        this.exameassociado = exameassociado;
    }

    public Examedependente getExamedependente() {
        return examedependente;
    }

    public void setExamedependente(Examedependente examedependente) {
        this.examedependente = examedependente;
    }

    public ExameAssociadoDao getExameAssociadoDao() {
        return exameAssociadoDao;
    }

    public void setExameAssociadoDao(ExameAssociadoDao exameAssociadoDao) {
        this.exameAssociadoDao = exameAssociadoDao;
    }

    public ExameDependenteDao getExameDependenteDao() {
        return exameDependenteDao;
    }

    public void setExameDependenteDao(ExameDependenteDao exameDependenteDao) {
        this.exameDependenteDao = exameDependenteDao;
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

    public Dependente getDependente() {
        return dependente;
    }

    public void setDependente(Dependente dependente) {
        this.dependente = dependente;
    }

    public List<Dependente> getListaDependente() {
        return listaDependente;
    }

    public void setListaDependente(List<Dependente> listaDependente) {
        this.listaDependente = listaDependente;
    }

    public DependenteDao getDependenteDao() {
        return dependenteDao;
    }

    public void setDependenteDao(DependenteDao dependenteDao) {
        this.dependenteDao = dependenteDao;
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

    public void gerarListaAssociados() {
        listaAssociado = associadoDao.list("Select a from Associado a");
        if (listaAssociado == null || listaAssociado.isEmpty()) {
            listaAssociado = new ArrayList<Associado>();
        }
    }

    public void gerarListaDependentes() {
        listaDependente = dependenteDao.list("Select d from Dependente d");
        if (listaAssociado == null || listaAssociado.isEmpty()) {
            listaAssociado = new ArrayList<Associado>();
        }
    }

    public void salvar() {
        exame.setMedico(medico);
        exame.setValor(valorExame);
        exame.setDesconto(descontoExame);
        String mensagem = validarDados();
        if (mensagem.length() < 5) {
            lancarContaReceber();
            if (associadoDependente.equalsIgnoreCase("Associado") && exame.getIdexame() != null) {
                exame.setNomeCliente(associado.getCliente().getNome());
                exame.setMatricula(associado.getMatricula());
                exame = exameDao.update(exame);
                exameassociado.setExame(exame);
                exameassociado.setAssociado(associado);
                exameAssociadoDao.update(exameassociado);
            } else if (associadoDependente.equalsIgnoreCase("Dependente") && exame.getIdexame() != null) {
                exame.setNomeCliente(dependente.getNome());
                exame.setMatricula(dependente.getMatricula());
                exame = exameDao.update(exame);
                examedependente.setExame(exame);
                examedependente.setDependente(dependente);
                exameDependenteDao.update(examedependente);
            }
            RequestContext.getCurrentInstance().closeDialog(exame);
        }
    }

    public String validarDados() {
        String msg = "";
        if (exame.getMedico() == null) {
            msg = msg + " você não informou o medico";
        }
        return msg;
    }

    public void lancarContaReceber() {
        contasreceber = new Contasreceber();
        contasreceber.setDatalancamento(new Date());
        contasreceber.setNumeroparcela("1");
        contasreceber.setSituacao("PAGAR");
        contasreceber.setValorconta(totalPagar);
        contasreceber.setTipopagamento(exame.getFormapagamento());
        if (exame.getFormapagamento().equalsIgnoreCase("Boleto")) {
            contasreceber.setSituacaoboleto("Novo");
        } else {
            contasreceber.setSituacaoboleto("Não");
        }
        contasreceber.setUsuario(usuarioLogadoMB.getUsuario());
        contasreceber.setNumerodocumento("" + exame.getIdexame());
        planoconta = planoContaDao.find(4);
        contasreceber.setPlanoconta(planoconta);
        if (associadoDependente.equalsIgnoreCase("Associado")) {
            contasreceber.setCliente(associado.getCliente());
        } else {
            contasreceber.setCliente(dependente.getAssociado().getCliente());
        }
        contasReceberDao.update(contasreceber);
    }

    public void calcularValidade() {
        Calendar c = new GregorianCalendar();
        c.setTime(exame.getData());
        c.add(Calendar.DAY_OF_MONTH, 89);
        Date data = c.getTime();
        exame.setDatavalidade(data);
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(new Exame());
    }

    public void calcularTotal() {
        totalPagar = valorExame - descontoExame;
    }

    public String gerarListaAssociadoDependente() {
        if (associadoDependente.equalsIgnoreCase("Associado")) {
            exibirNome = "Associado.";
            gerarListaAssociados();
            habilitarAssociado = true;
            habilitarNome = true;
            habilitarDependente = false;
        } else if (associadoDependente.equals("Dependente")) {
            gerarListaDependentes();
            exibirNome = "Dependente";
            habilitarDependente = true;
            habilitarNome = true;
            habilitarAssociado = false;
        }
        return "";
    }

    public void gerarListaMedico() {
        listaMedico = medicoDao.list("Select m from Medico m where m.situacao='Ativo'");
        if (listaMedico == null || listaMedico.isEmpty()) {
            listaMedico = new ArrayList<Medico>();
        }
    }

    public void pegarValores() {
        List<Exameassociado> listaExameAssociado = exameAssociadoDao.list("Select ea from Exameassociado ea where ea.exame.idexame=" + exame.getIdexame());
        List<Examedependente> listaExameDependente = exameDependenteDao.list("Select ed from Examedependente ed where ed.exame.idexame=" + exame.getIdexame());
        if (listaExameAssociado == null || listaExameAssociado.isEmpty()) {
            for (int i = 0; i < listaExameDependente.size(); i++) {
                examedependente = listaExameDependente.get(i);
            }
        } else {
            for (int i = 0; i < listaExameAssociado.size(); i++) {
                exameassociado = listaExameAssociado.get(i);
            }
        }
    }

    public void getMedicoDefault() {
        parametros = parametrosDao.find(1);
        medico = medicoDao.find(parametros.getMedico());
    }
}
