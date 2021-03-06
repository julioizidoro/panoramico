/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.PlanoDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Plano;
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
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class PlanoMB implements Serializable {

    @EJB
    private PlanoDao planoDao;
    private Plano plano;
    private List<Plano> listaPlano;
    @EJB
    private AssociadoDao associadoDao;

    @PostConstruct
    public void init() {
        gerarListaPlano();
    }

    public List<Plano> getListaPlano() {
        return listaPlano;
    }

    public void setListaPlano(List<Plano> listaPlano) {
        this.listaPlano = listaPlano;
    }

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public PlanoDao getPlanoDao() {
        return planoDao;
    }

    public void setPlanoDao(PlanoDao planoDao) {
        this.planoDao = planoDao;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public void gerarListaPlano() {
        listaPlano = planoDao.list("select p from Plano p");
        if (listaPlano == null) {
            listaPlano = new ArrayList<Plano>();
        }
    }

    public String novoCadastroPlano() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 450);
        RequestContext.getCurrentInstance().openDialog("cadPlano", options, null);
        return "";
    }

    public void retornoDialogNovo(SelectEvent event) {
        Plano plano = (Plano) event.getObject();
        if (plano.getIdplano() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de Plano realizado com sucesso");
        }
        gerarListaPlano();
    }

    public void retornoDialogAlteracao(SelectEvent event) {
        Plano plano = (Plano) event.getObject();
        if (plano.getIdplano() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração de Plano realizado com sucesso");
        }
        gerarListaPlano();
    }

    public void editar(Plano plano) {
        if (plano != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("plano", plano);
            options.put("contentWidth", 450);
            RequestContext.getCurrentInstance().openDialog("cadPlano", options, null);
        }
    }

    public void excluir(Plano plano) {
        List<Associado> listaAssociado = associadoDao.list("select a from Associado a where a.plano.idplano=" + plano.getIdplano());
        if (listaAssociado == null || listaAssociado.isEmpty()) {
            planoDao.remove(plano.getIdplano());
            Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
            gerarListaPlano();
        } else {
            Mensagem.lancarMensagemInfo("Atenção", " este plano não pode ser excluido");
        }
    }
}
