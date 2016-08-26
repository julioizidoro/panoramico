/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.BancoDao;
import br.com.panoramico.model.Banco;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;


@Named
@ViewScoped
public class CadBancoMB implements Serializable{
    
    private Banco banco;
    @EJB
    private BancoDao bancoDao;
    
   
    @PostConstruct
    public void init(){
        
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public BancoDao getBancoDao() {
        return bancoDao;
    }

    public void setBancoDao(BancoDao bancoDao) {
        this.bancoDao = bancoDao;
    }
    
    
    public void salvar(){
        String msg = validarDados();
        if (msg.length() < 5) {
            banco = bancoDao.update(banco);
            RequestContext.getCurrentInstance().closeDialog(banco);
        }
    }
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Banco());
    }
    
    public String validarDados(){
        String mensagem = "";
        
        if (banco.getAgencia().equalsIgnoreCase("")) {
            mensagem = mensagem + " Você não informou a agência \r\n";
        }
        if (banco.getConta().equalsIgnoreCase("")) {
            mensagem = mensagem + " Você não informou a conta \r\n";
        }
        if (banco.getNome().equalsIgnoreCase("")) {
            mensagem = mensagem + " Você não informou o nome do banco \r\n";
        }
        return mensagem;
    }
}