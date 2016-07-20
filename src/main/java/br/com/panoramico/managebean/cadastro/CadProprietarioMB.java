/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.ProprietarioDao;
import br.com.panoramico.model.Proprietario;
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
public class CadProprietarioMB implements  Serializable{
    
    @EJB
    private ProprietarioDao proprietarioDao;
    private Proprietario proprietario;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        proprietario = (Proprietario) session.getAttribute("proprietario");
        session.removeAttribute("proprietario");
        if (proprietario == null) {
            proprietario = new Proprietario();
        }
    }
 
    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public ProprietarioDao getProprietarioDao() {
        return proprietarioDao;
    }

    public void setProprietarioDao(ProprietarioDao proprietarioDao) {
        this.proprietarioDao = proprietarioDao;
    }
    
    
    
    /**
     *
     */
    public void Salvar(){ 
      proprietario =  proprietarioDao.update(proprietario);
      Mensagem.lancarMensagemInfo("Salvou", "Proprietario cadastrado com sucesso!!");
      proprietario = new Proprietario();
      RequestContext.getCurrentInstance().closeDialog(proprietario);
    }
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(proprietario);
    }
}
