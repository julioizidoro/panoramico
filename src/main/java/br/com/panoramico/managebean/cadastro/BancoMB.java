/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.BancoDao;
import br.com.panoramico.model.Banco;
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
public class BancoMB implements Serializable{
    
    private Banco banco;
    private List<Banco> listaBanco;
    @EJB
    private BancoDao bancoDao;
    
    
    @PostConstruct
    public void init(){
        gerarListaBanco();
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Banco> getListaBanco() {
        return listaBanco;
    }

    public void setListaBanco(List<Banco> listaBanco) {
        this.listaBanco = listaBanco;
    }

    public BancoDao getBancoDao() {
        return bancoDao;
    }

    public void setBancoDao(BancoDao bancoDao) {
        this.bancoDao = bancoDao;
    }
    
    
    public void gerarListaBanco(){
        listaBanco = bancoDao.list("Select b from Banco b");
        if (listaBanco == null || listaBanco.isEmpty()) {
            listaBanco = new ArrayList<Banco>();
        }
    }
    
    public void retornoDialogNovo(SelectEvent event){
        Banco banco = (Banco) event.getObject();
        if (banco.getIdbanco() != null) {
            listaBanco.add(banco);
            Mensagem.lancarMensagemInfo("Banco:" + banco.getNome(), " salvo com sucesso");
        }
    }
    
    public String editar(Banco banco) {
        if (banco != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("banco", banco);
            options.put("contentWidth", 500);
            RequestContext.getCurrentInstance().openDialog("cadBanco", options, null);
        }
        return "";
    }
    
    public String novoBanco() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 500); 
        RequestContext.getCurrentInstance().openDialog("cadBanco", options, null);
        return "";
    }
    
    public void excluir(Banco banco){
        bancoDao.remove(banco.getIdbanco());
        listaBanco.remove(banco);
        Mensagem.lancarMensagemInfo("Excluido", " com sucesso");
    }
}
