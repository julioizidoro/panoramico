package br.com.panoramico.managebean.evento;

import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.EventoConvidadosDao;
import br.com.panoramico.dao.ExameConvidadoDao;
import br.com.panoramico.dao.ExameDao;
import br.com.panoramico.dao.MedicoDao;
import br.com.panoramico.dao.ParametrosDao;
import br.com.panoramico.dao.PlanoContaDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Evento;
import br.com.panoramico.model.Eventoconvidados;
import br.com.panoramico.model.Exame;
import br.com.panoramico.model.Exameconvidado;
import br.com.panoramico.model.Medico;
import br.com.panoramico.model.Parametros;
import br.com.panoramico.model.Planoconta;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kamilla Rodrigues
 */
@Named
@ViewScoped
public class cadConvidadosEventoMB implements Serializable {

    private Eventoconvidados eventoconvidados;
    private List<Eventoconvidados> listaConvidados;
    private Evento evento;
    @EJB
    private EventoConvidadosDao eventoConvidadosDao;
    private String mensagem;
    private Integer totalConvidados = 0;
    private Medico medico;
    @EJB
    private MedicoDao medicoDao;
    @EJB
    private ExameConvidadoDao exameConvidadoDao;
    private Float valorExame = 0.0f;
    private Float descontoExame = 0.0f;
    private String formapagamento;
    @EJB
    private ExameDao exameDao;
    private Parametros parametros;
    @EJB
    private ParametrosDao parametrosDao;
    @EJB
    private ContasReceberDao contasReceberDao;
    private Float totalPagar = 0.0f;
    private Planoconta planoconta;
    @EJB
    private PlanoContaDao planoContaDao;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private boolean gerarContasReceber;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        evento = (Evento) session.getAttribute("evento");
        session.removeAttribute("evento");
        gerarListaConvidados();
        if (eventoconvidados == null) {
            eventoconvidados = new Eventoconvidados();
        }
        getMedicoDefault();
    }

    public Eventoconvidados getEventoconvidados() {
        return eventoconvidados;
    }

    public void setEventoconvidados(Eventoconvidados eventoconvidados) {
        this.eventoconvidados = eventoconvidados;
    }

    public List<Eventoconvidados> getListaConvidados() {
        return listaConvidados;
    }

    public void setListaConvidados(List<Eventoconvidados> listaConvidados) {
        this.listaConvidados = listaConvidados;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public EventoConvidadosDao getEventoConvidadosDao() {
        return eventoConvidadosDao;
    }

    public void setEventoConvidadosDao(EventoConvidadosDao eventoConvidadosDao) {
        this.eventoConvidadosDao = eventoConvidadosDao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getTotalConvidados() {
        return totalConvidados;
    }

    public void setTotalConvidados(Integer totalConvidados) {
        this.totalConvidados = totalConvidados;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public MedicoDao getMedicoDao() {
        return medicoDao;
    }

    public void setMedicoDao(MedicoDao medicoDao) {
        this.medicoDao = medicoDao;
    }

    public ExameConvidadoDao getExameConvidadoDao() {
        return exameConvidadoDao;
    }

    public void setExameConvidadoDao(ExameConvidadoDao exameConvidadoDao) {
        this.exameConvidadoDao = exameConvidadoDao;
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

    public String getFormapagamento() {
        return formapagamento;
    }

    public void setFormapagamento(String formapagamento) {
        this.formapagamento = formapagamento;
    }

    public ExameDao getExameDao() {
        return exameDao;
    }

    public void setExameDao(ExameDao exameDao) {
        this.exameDao = exameDao;
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

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public Float getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Float totalPagar) {
        this.totalPagar = totalPagar;
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

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public boolean isGerarContasReceber() {
        return gerarContasReceber;
    }

    public void setGerarContasReceber(boolean gerarContasReceber) {
        this.gerarContasReceber = gerarContasReceber;
    }
    
    

    public void salvar() {
        mensagem = "";
        Eventoconvidados convidados = new Eventoconvidados();
        for (int i = 0; i < listaConvidados.size(); i++) {
            listaConvidados.get(i).setSituacao("N");
            listaConvidados.get(i).setEvento(evento);
            convidados = eventoConvidadosDao.update(listaConvidados.get(i));
        }
        if (convidados.getIdeventoconvidados() != null) {
            mensagem = "Convidados cadastrados com sucesso!!";
        }
        RequestContext.getCurrentInstance().closeDialog(mensagem);
    }

    public void cancelar() {
        mensagem = "";
        RequestContext.getCurrentInstance().closeDialog(mensagem);
    }

    public String validarDados(Eventoconvidados convidados) {
        String msg = "";
        if (convidados.getNome().equalsIgnoreCase("")) {
            msg = msg + " Nome do convidado não informado \r\n";
        }
        return msg;
    }

    public void adicionarConvidado() {
        String msgem = "";
        msgem = validarDados(eventoconvidados);
        if (listaConvidados == null) {
            listaConvidados = new ArrayList<Eventoconvidados>();
        }
        if (msgem.length() < 5) {
            listaConvidados.add(eventoconvidados);
            eventoconvidados = new Eventoconvidados();
            Mensagem.lancarMensagemInfo("Adicionou", "convidado cadastrado");
        }
    }

    public void excluirConvidado(Eventoconvidados excluirConvidado) {
        listaConvidados.remove(excluirConvidado);
        eventoConvidadosDao.remove(excluirConvidado.getIdeventoconvidados());
    }

    public void gerarListaConvidados() {
        listaConvidados = eventoConvidadosDao.list("Select c from Eventoconvidados c where c.evento.idevento=" + evento.getIdevento());
        if (listaConvidados == null) {
            listaConvidados = new ArrayList<Eventoconvidados>();
        }
    }

    public void gerarSolicitacaoExameTodos() {
        Exame exame;
        Exameconvidado exameconvidado;
        for (int i = 0; i < listaConvidados.size(); i++) {
            exame = new Exame();
            exame.setMedico(medico);
            exame.setValor(valorExame);
            exame.setFormapagamento(formapagamento);
            exame.setDesconto(descontoExame);
            exame = exameDao.update(exame);
            exameconvidado = new Exameconvidado();
            exameconvidado.setExame(exame);
            exameconvidado.setEventoconvidados(listaConvidados.get(i));
            exameconvidado = exameConvidadoDao.update(exameconvidado);
        }
        if(gerarContasReceber){
            totalPagar = (valorExame - descontoExame) * listaConvidados.size();
            lancarContaReceber();
        }
    }

    public void getMedicoDefault() {
        parametros = parametrosDao.find(1);
        medico = medicoDao.find(parametros.getMedico());
    }

    public void lancarContaReceber() {
        Contasreceber contasreceber = new Contasreceber();
        contasreceber.setDatalancamento(new Date());
        contasreceber.setNumeroparcela("1");
        contasreceber.setSituacao("PAGAR");
        contasreceber.setValorconta(totalPagar);
        contasreceber.setDatavencimento(evento.getData());
        contasreceber.setTipopagamento(formapagamento);
        if (formapagamento.equalsIgnoreCase("Boleto")) {
            contasreceber.setSituacaoboleto("Novo");
        } else {
            contasreceber.setSituacaoboleto("Não");
        }
        contasreceber.setUsuario(usuarioLogadoMB.getUsuario());
        contasreceber.setNumerodocumento("" + evento.getIdevento());
        planoconta = planoContaDao.find(4);
        contasreceber.setPlanoconta(planoconta);
        contasreceber.setCliente(evento.getCliente());
        contasReceberDao.update(contasreceber);
    }
}
