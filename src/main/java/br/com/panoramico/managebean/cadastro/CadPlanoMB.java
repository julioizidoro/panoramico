/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.PlanoDao;
import br.com.panoramico.model.Plano;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Julio
 */
@Named
@ViewScoped
public class CadPlanoMB implements Serializable {

    @EJB
    private PlanoDao planoDao;
    private Plano plano;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        plano = (Plano) session.getAttribute("plano");
        session.removeAttribute("plano");
        if (plano == null) {
            plano = new Plano();
        }
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

    public void salvar() {
        String mensagem = validarDados();
        if (mensagem.length() == 0) {
            plano = planoDao.update(plano);
            RequestContext.getCurrentInstance().closeDialog(plano);
        } else {
            Mensagem.lancarMensagemInfo("", mensagem);
        }
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(plano);
    }

    public String validarDados() {
        String msg = "";
        if (plano.getDescricao() == null || plano.getDescricao().length() == 0) {
            msg = msg + " Informe a descrição do plano \r\n";
        }
        if (plano.getValor() == null || plano.getValor() < 0.0f) {
            msg = msg + " Informe um valor do plano igual ou maior que zero \r\n";
        }
        return msg;
    }
}
