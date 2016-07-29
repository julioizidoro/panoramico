/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.PlanoContaDao;
import br.com.panoramico.model.Planoconta;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadPlanosContasMB implements Serializable{
    
    private Planoconta planoconta;
    @EJB
    private PlanoContaDao planoContaDao;
    
    
    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        planoconta = (Planoconta) session.getAttribute("planoconta");
        if (planoconta == null) {
            planoconta = new Planoconta();
        }
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
    
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Planoconta());
    }
    
    public void salvar(){
        String mensagem = validarDados(planoconta);
        if (mensagem.length() < 5) {
            planoconta = planoContaDao.update(planoconta);
            RequestContext.getCurrentInstance().closeDialog(planoconta);
        }else{
            Mensagem.lancarMensagemInfo("", mensagem);
        }
    }
    
    public String validarDados(Planoconta planoconta){
        String msg = "";
        if (planoconta.getDescricao().equalsIgnoreCase("")) {
            msg = msg + " Descrição não informada \r\n";
        }
        return msg;
    }
}
