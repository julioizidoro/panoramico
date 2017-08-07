/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.ContasPagarDao;
import br.com.panoramico.dao.PlanoContaDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Contaspagar;
import br.com.panoramico.model.Planoconta;
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
public class PlanosContasMB implements Serializable {

    private Planoconta planoConta;
    private List<Planoconta> listaPlanosContas;
    @EJB
    private PlanoContaDao planoContaDao;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private ContasPagarDao contasPagarDao;

    @PostConstruct
    public void init() {
        gerarListaPlanoContas();
    }

    public Planoconta getPlanoConta() {
        return planoConta;
    }

    public void setPlanoConta(Planoconta planoConta) {
        this.planoConta = planoConta;
    }

    public List<Planoconta> getListaPlanosContas() {
        return listaPlanosContas;
    }

    public void setListaPlanosContas(List<Planoconta> listaPlanosContas) {
        this.listaPlanosContas = listaPlanosContas;
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

    public void gerarListaPlanoContas() {
        listaPlanosContas = planoContaDao.list("select p from Planoconta p");
        if (listaPlanosContas == null) {
            listaPlanosContas = new ArrayList<Planoconta>();
        }
    }

    public String novoCadastroPlanoContas() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 400);
        RequestContext.getCurrentInstance().openDialog("cadPlanosContas", options, null);
        return "";
    }

    public void retornoDialogNovo(SelectEvent event) {
        Planoconta planoconta = (Planoconta) event.getObject();
        if (planoconta.getIdplanoconta() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de plano de conta realizado com sucesso");
        }
        gerarListaPlanoContas();
    }

    public void retornoDialogAlteracao(SelectEvent event) {
        Planoconta planoconta = (Planoconta) event.getObject();
        if (planoconta.getIdplanoconta() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração de plano de conta realizada com sucesso");
        }
        gerarListaPlanoContas();
    }

    public void editar(Planoconta planoconta) {
        if (planoconta != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("planoconta", planoconta);
            options.put("contentWidth", 400);
            RequestContext.getCurrentInstance().openDialog("cadPlanosContas", options, null);
        }
    }

    public void excluir(Planoconta planoconta) {
        List<Contaspagar> listaContasPagar = contasPagarDao.list("select c from Contaspagar c where c.planoconta.idplanoconta=" + planoconta.getIdplanoconta());
        if (listaContasPagar == null || listaContasPagar.isEmpty()) {
            planoContaDao.remove(planoconta.getIdplanoconta());
            Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
            gerarListaPlanoContas();
        } else {
            Mensagem.lancarMensagemInfo("Atenção", " este plano de conta não pode ser excluido");
        }
    }
}
