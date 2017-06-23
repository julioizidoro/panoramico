/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.MotivoCancelamentoDao;
import br.com.panoramico.model.Motivocancelamento;
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
 * @author Anderson
 */

@Named
@ViewScoped
public class CadMotivoCancelamentoMB implements Serializable{
    
    private Motivocancelamento motivocancelamento;
    @EJB
    private MotivoCancelamentoDao motivoCancelamentoDao;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        motivocancelamento = (Motivocancelamento) session.getAttribute("motivocancelamento");
        session.removeAttribute("motivocancelamento");
        if (motivocancelamento == null) {
            motivocancelamento = new Motivocancelamento();
        }
    }

    public Motivocancelamento getMotivocancelamento() {
        return motivocancelamento;
    }

    public void setMotivocancelamento(Motivocancelamento motivocancelamento) {
        this.motivocancelamento = motivocancelamento;
    }
    
    
    
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Motivocancelamento());
    }
    
    public void salvar(){
        String mensagem = validarDados();
        if (mensagem.length() < 5) {
            motivocancelamento = motivoCancelamentoDao.update(motivocancelamento);
            RequestContext.getCurrentInstance().closeDialog(motivocancelamento);
        }else{
            Mensagem.lancarMensagemInfo("", mensagem);
        }
    }
    
    public String validarDados(){
        String msg = "";
        if (motivocancelamento.getDescricao().equalsIgnoreCase("")) {
            msg = msg + " Descrição não informada \r\n";
        }
        return msg;
    }
    
    
}
