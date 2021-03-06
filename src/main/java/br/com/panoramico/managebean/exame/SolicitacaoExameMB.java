/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.exame;

import br.com.panoramico.dao.ExameAssociadoDao;
import br.com.panoramico.dao.ExameConvidadoDao;
import br.com.panoramico.dao.ExameDao;
import br.com.panoramico.dao.ExameDependenteDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Exame;
import br.com.panoramico.model.Exameassociado;
import br.com.panoramico.model.Examedependente;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class SolicitacaoExameMB implements Serializable {

    private Exame exame;
    private List<Exame> listaSolicitacao;
    @EJB
    private ExameDao exameDao;
    private Exameassociado exameassociado;
    private Examedependente examedependente;
    @EJB
    private ExameDependenteDao exameDependenteDao;
    @EJB
    private ExameAssociadoDao exameAssociadoDao;
    @EJB
    private ExameConvidadoDao exameConvidadoDao;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;

    @PostConstruct
    public void init() {
        gerarListaSolicitacoes();
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public List<Exame> getListaSolicitacao() {
        return listaSolicitacao;
    }

    public void setListaSolicitacao(List<Exame> listaSolicitacao) {
        this.listaSolicitacao = listaSolicitacao;
    }

    public ExameDao getExameDao() {
        return exameDao;
    }

    public void setExameDao(ExameDao exameDao) {
        this.exameDao = exameDao;
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

    public ExameDependenteDao getExameDependenteDao() {
        return exameDependenteDao;
    }

    public void setExameDependenteDao(ExameDependenteDao exameDependenteDao) {
        this.exameDependenteDao = exameDependenteDao;
    }

    public ExameAssociadoDao getExameAssociadoDao() {
        return exameAssociadoDao;
    }

    public void setExameAssociadoDao(ExameAssociadoDao exameAssociadoDao) {
        this.exameAssociadoDao = exameAssociadoDao;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public void gerarListaSolicitacoes() {
        listaSolicitacao = exameDao.list("select e from Exame e where e.situacao is null");
        if (listaSolicitacao == null || listaSolicitacao.isEmpty()) {
            listaSolicitacao = new ArrayList<Exame>();
        }
    }

    public String novaSolicitacaoExame() {
        if (usuarioLogadoMB.getUsuario().getPerfil().getNome().equalsIgnoreCase("Administrativo")
                || usuarioLogadoMB.getUsuario().getPerfil().getNome().equalsIgnoreCase("Gerencial")) {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 545);
            RequestContext.getCurrentInstance().openDialog("cadSolicitacaoExame", options, null);
        } else {
            Mensagem.lancarMensagemInfo("", "Acesso Negado!!");
        }
        return "";
    }

    public void retornoDialogNovo(SelectEvent event) {
        Exame exame = (Exame) event.getObject();
        if (exame.getIdexame() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Solicitação de exame realizado com sucesso");
        }
        gerarListaSolicitacoes();
    }

    public void retornoDialogAlteracao(SelectEvent event) {
        Exame exame = (Exame) event.getObject();
        if (exame.getIdexame() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração de exame realizado com sucesso");
        }
        gerarListaSolicitacoes();
    }

    public void excluir(Exame exame) {
        if (exame.getExamedependente() != null) {
            exameDependenteDao.remove(exame.getExamedependente().getIdexamedependente());
        } else if (exame.getExameassociado() != null) {
            exameAssociadoDao.remove(exame.getExameassociado().getIdexameassociado());
        } else {
            exameConvidadoDao.remove(exame.getExameconvidado().getIdexameconvidado());
        }
        exameDao.remove(exame.getIdexame());
        Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
        gerarListaSolicitacoes();
    }

    public void editar(Exame exame) {
        if (usuarioLogadoMB.getUsuario().getPerfil().getNome().equalsIgnoreCase("Administrativo")
                || usuarioLogadoMB.getUsuario().getPerfil().getNome().equalsIgnoreCase("Gerencial")) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("exame", exame);
            options.put("contentWidth", 545);
            if (exame.getExameconvidado() != null) {
                RequestContext.getCurrentInstance().openDialog("cadSolicitacaoExameConvidado", options, null);
            } else {
                RequestContext.getCurrentInstance().openDialog("cadSolicitacaoExame", options, null);
            }
        } else {
            Mensagem.lancarMensagemInfo("", "Acesso Negado!!");
        }
    }

    public Float calcularTotal(Exame exame) {
        return exame.getValor() - exame.getDesconto();
    }

    public String nomeCliente(Exame exame) {
        if (exame.getExameassociado() != null) {
            return exame.getExameassociado().getAssociado().getCliente().getNome();
        } else if (exame.getExamedependente() != null) {
            return exame.getExamedependente().getDependente().getNome();
        } else if (exame.getExameconvidado() != null) {
            return exame.getExameconvidado().getEventoconvidados().getNome();
        }
        return "";
    }
}
