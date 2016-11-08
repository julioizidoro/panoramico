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
    
    
    
    
    public void salvar(){
            if (cadastrar && editar && exlcuir && financeiro) {
                acesso = acessoDao.find(1);
            }else if(exlcuir && editar && financeiro && !cadastrar){
                acesso = acessoDao.find(2);
            }else if(cadastrar && exlcuir && financeiro && !editar){
                acesso = acessoDao.find(3);
            }else if (financeiro && exlcuir && !cadastrar && !editar) {
                acesso = acessoDao.find(4);
            }else if(cadastrar && editar && financeiro && !exlcuir){
                acesso = acessoDao.find(5);
            }else if(editar && financeiro && !cadastrar && !exlcuir){
                acesso = acessoDao.find(6);
            }else if(cadastrar && financeiro && !editar && !exlcuir){
                acesso = acessoDao.find(7);
            }else if(financeiro && !cadastrar && !exlcuir && !editar){
                acesso = acessoDao.find(8);
            }else if(cadastrar && editar && exlcuir && !financeiro){
                acesso = acessoDao.find(9);
            }else if(exlcuir && editar && !cadastrar && !financeiro){
                acesso = acessoDao.find(10);
            }else if(exlcuir && cadastrar && !editar && !financeiro){
                acesso = acessoDao.find(11);
            }else if(exlcuir && !cadastrar && !editar && !financeiro){
                acesso = acessoDao.find(12);
            }else if(cadastrar && editar && !exlcuir && !financeiro){
                acesso = acessoDao.find(13);
            }else if(editar && !cadastrar && !exlcuir && !financeiro){
                acesso = acessoDao.find(14);
            }else if(cadastrar && !editar && !exlcuir && !financeiro){
                acesso = acessoDao.find(15);
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