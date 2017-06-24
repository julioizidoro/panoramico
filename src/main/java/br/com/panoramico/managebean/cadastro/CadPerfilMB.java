/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AcessoDao;
import br.com.panoramico.dao.PerfilDao;
import br.com.panoramico.model.Acesso;
import br.com.panoramico.model.Perfil;
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
public class CadPerfilMB implements Serializable{
    
    @EJB
    private PerfilDao perfilDao;
    private Perfil perfil;
    @EJB
    private AcessoDao acessoDao;
    private Acesso acesso;
    private boolean cadastrar = false;
    private boolean editar = false;
    private boolean exlcuir = false;
    private boolean financeiro = false;
    private boolean cancelamento = false;
    
    
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        perfil = (Perfil) session.getAttribute("perfil");
        session.removeAttribute("perfil");
        if (perfil == null) {
            perfil = new Perfil();
        }else{
            if(perfil.getAcesso().getIdacesso() == 1){
                cadastrar = true;
                editar = true;
                exlcuir = true;
                financeiro = true;
            }else if(perfil.getAcesso().getIdacesso() == 2){
                editar = true;
                exlcuir = true;
                financeiro = true;
            }else if (perfil.getAcesso().getIdacesso() == 3){
                cadastrar = true;
                exlcuir = true;
                financeiro = true;
            }else if (perfil.getAcesso().getIdacesso() == 4) {
                exlcuir = true;
                financeiro = true;
            }else if (perfil.getAcesso().getIdacesso() == 5){
                cadastrar = true;
                editar = true;
                financeiro = true;
            }else if (perfil.getAcesso().getIdacesso() == 6){
                editar = true;
                financeiro = true;
            }else if (perfil.getAcesso().getIdacesso() == 7){
                cadastrar = true;
                financeiro = true;
            }else if (perfil.getAcesso().getIdacesso() == 8){
                financeiro = true;
            }else if (perfil.getAcesso().getIdacesso() == 9){
                cadastrar = true;
                editar = true;
                exlcuir = true;
            }else if (perfil.getAcesso().getIdacesso() == 10){
                editar = true;
                exlcuir = true;
            }else if (perfil.getAcesso().getIdacesso() == 11){
                cadastrar = true;
                exlcuir = true;
            }else if (perfil.getAcesso().getIdacesso() == 12){
                exlcuir = true;
            }else if (perfil.getAcesso().getIdacesso() == 13){
                cadastrar = true;
                editar = true;
            }else if (perfil.getAcesso().getIdacesso() == 14){
                editar = true;
            }else if (perfil.getAcesso().getIdacesso() == 15){
                cadastrar = true;
            }else if(perfil.getAcesso().getIdacesso() == 17){
             cadastrar = true;
             editar = true;
             exlcuir = true;
             financeiro = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 18){
             cadastrar = true;
             editar = true;
             exlcuir = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 19){
             cadastrar = true;
             editar = true;
             financeiro = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 20){
             cadastrar = true;
             editar = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 21){
             cadastrar = true;
             exlcuir = true;
             financeiro = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 22){
             cadastrar = true;
             exlcuir = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 23){
             cadastrar = true;
             financeiro = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 24){
             cadastrar = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 25){
             editar = true;
             exlcuir = true;
             financeiro = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 26){
             editar = true;
             exlcuir = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 27){
             editar = true;
             financeiro = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 28){
             editar = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 29){
             exlcuir = true;
             financeiro = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 30){
             exlcuir = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 31){
             financeiro = true;
             cancelamento = true;
         }else if(perfil.getAcesso().getIdacesso() == 16){
             cancelamento = true;
         }
                    
        }
    }

    public AcessoDao getAcessoDao() {
        return acessoDao;
    }

    public void setAcessoDao(AcessoDao acessoDao) {
        this.acessoDao = acessoDao;
    }

    public Acesso getAcesso() {
        return acesso;
    }

    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }

    public boolean isCadastrar() {
        return cadastrar;
    }

    public void setCadastrar(boolean cadastrar) {
        this.cadastrar = cadastrar;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public boolean isExlcuir() {
        return exlcuir;
    }

    public void setExlcuir(boolean exlcuir) {
        this.exlcuir = exlcuir;
    }


    public PerfilDao getPerfilDao() {
        return perfilDao;
    }

    public void setPerfilDao(PerfilDao perfilDao) {
        this.perfilDao = perfilDao;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public boolean isFinanceiro() {
        return financeiro;
    }

    public void setFinanceiro(boolean financeiro) {
        this.financeiro = financeiro;
    }

    public boolean isCancelamento() {
        return cancelamento;
    }

    public void setCancelamento(boolean cancelamento) {
        this.cancelamento = cancelamento;
    }
    
    
    
    
    public void salvar(){
            if (cadastrar && editar && exlcuir && financeiro && !cancelamento) {
                acesso = acessoDao.find(1);
            }else if(exlcuir && editar && financeiro && !cadastrar && !cancelamento){
                acesso = acessoDao.find(2);
            }else if(cadastrar && exlcuir && financeiro && !editar && !cancelamento){
                acesso = acessoDao.find(3);
            }else if (financeiro && exlcuir && !cadastrar && !editar && !cancelamento) {
                acesso = acessoDao.find(4);
            }else if(cadastrar && editar && financeiro && !exlcuir && !cancelamento){
                acesso = acessoDao.find(5);
            }else if(editar && financeiro && !cadastrar && !exlcuir && !cancelamento){
                acesso = acessoDao.find(6);
            }else if(cadastrar && financeiro && !editar && !exlcuir && !cancelamento){
                acesso = acessoDao.find(7);
            }else if(financeiro && !cadastrar && !exlcuir && !editar && !cancelamento){
                acesso = acessoDao.find(8);
            }else if(cadastrar && editar && exlcuir && !financeiro && !cancelamento){
                acesso = acessoDao.find(9);
            }else if(exlcuir && editar && !cadastrar && !financeiro && !cancelamento){
                acesso = acessoDao.find(10);
            }else if(exlcuir && cadastrar && !editar && !financeiro && !cancelamento){
                acesso = acessoDao.find(11);
            }else if(exlcuir && !cadastrar && !editar && !financeiro && !cancelamento){
                acesso = acessoDao.find(12);
            }else if(cadastrar && editar && !exlcuir && !financeiro && !cancelamento){
                acesso = acessoDao.find(13);
            }else if(editar && !cadastrar && !exlcuir && !financeiro && !cancelamento){
                acesso = acessoDao.find(14);
            }else if(cadastrar && !editar && !exlcuir && !financeiro && !cancelamento){
                acesso = acessoDao.find(15);
            }else if(cadastrar && editar && exlcuir && financeiro && cancelamento){
                acesso = acessoDao.find(17);
            }else if(cadastrar && editar && exlcuir && !financeiro && cancelamento){
                acesso = acessoDao.find(18);
            }else if(cadastrar && editar && !exlcuir && financeiro && cancelamento){
                acesso = acessoDao.find(19);
            }else if(cadastrar && editar && !exlcuir && !financeiro && cancelamento){
                acesso = acessoDao.find(20);
            }else if(cadastrar && !editar && exlcuir && financeiro && cancelamento){
                acesso = acessoDao.find(21);
            }else if(cadastrar && !editar && exlcuir && !financeiro && cancelamento){
                acesso = acessoDao.find(22);
            }else if(cadastrar && !editar && !exlcuir && financeiro && cancelamento){
                acesso = acessoDao.find(23);
            }else if(cadastrar && !editar && !exlcuir && !financeiro && cancelamento){
                acesso = acessoDao.find(24);
            }else if(!cadastrar && editar && exlcuir && financeiro && cancelamento){
                acesso = acessoDao.find(25);
            }else if(!cadastrar && editar && exlcuir && !financeiro && cancelamento){
                acesso = acessoDao.find(26);
            }else if(cadastrar && editar && !exlcuir && financeiro && cancelamento){
                acesso = acessoDao.find(27);
            }else if(!cadastrar && editar && !exlcuir && !financeiro && cancelamento){
                acesso = acessoDao.find(28);
            }else if(!cadastrar && !editar && exlcuir && financeiro && cancelamento){
                acesso = acessoDao.find(29);
            }else if(!cadastrar && !editar && exlcuir && !financeiro && cancelamento){
                acesso = acessoDao.find(30);
            }else if(!cadastrar && !editar && !exlcuir && financeiro && cancelamento){
                acesso = acessoDao.find(31);
            }else{
                acesso = acessoDao.find(16);
            }
            perfil.setAcesso(acesso);
           
        perfil = perfilDao.update(perfil);
        RequestContext.getCurrentInstance().closeDialog(perfil);
    }

    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(perfil);
    }
}