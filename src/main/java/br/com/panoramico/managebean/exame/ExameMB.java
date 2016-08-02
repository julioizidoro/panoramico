/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.exame;

import br.com.panoramico.dao.ExameDao;
import br.com.panoramico.model.Exame;
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
public class ExameMB implements Serializable{
    
    private Exame exame;
    private List<Exame> listaExames;
    @EJB
    private ExameDao exameDao;
    
    
    @PostConstruct
    public void init(){
        gerarListaExame();
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public List<Exame> getListaExames() {
        return listaExames;
    }

    public void setListaExames(List<Exame> listaExames) {
        this.listaExames = listaExames;
    }

    public ExameDao getExameDao() {
        return exameDao;
    }

    public void setExameDao(ExameDao exameDao) {
        this.exameDao = exameDao;
    }
    
    
    public void gerarListaExame(){
        listaExames = exameDao.list("Select e from Exame e");
        if (listaExames == null) {
            listaExames = new ArrayList<Exame>();
        }
    }
    
    
     public String novoCadastroExame() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 545);
        RequestContext.getCurrentInstance().openDialog("cadExame", options, null);
        return "";
    }
    
    public void retornoDialogNovo(SelectEvent event){
        Exame exame = (Exame) event.getObject();
        if (exame.getIdexame()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de exame realizado com sucesso");
        }
        gerarListaExame();
    }
    
    public void retornoDialogAlteracao(SelectEvent event){
        Exame exame = (Exame) event.getObject();
        if (exame.getIdexame()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração de exame realizado com sucesso");
        }
        gerarListaExame();
    }
    
    
    public void editar(Exame exame){
        if (exame != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("exame", exame);
            options.put("contentWidth", 545);
            RequestContext.getCurrentInstance().openDialog("cadExame", options, null);
        }
    }
    
    public void excluir(Exame exame){
        exameDao.remove(exame.getIdexame());
        Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
        gerarListaExame();
    }
}
