/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.PerfilDao;
import br.com.panoramico.dao.UsuarioDao;
import br.com.panoramico.model.Perfil;
import br.com.panoramico.model.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Julio
 */

@Named
@ViewScoped
public class CadUsuarioMB implements Serializable{
     
    @EJB
    private UsuarioDao usuarioDao;
    private Usuario usuario;
    @EJB
    private PerfilDao perfilDao;
    private Perfil perfil;
    private List<Perfil> listaPerfil;
    
    
    @PostConstruct
    public void init(){
        
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public List<Perfil> getListaPerfil() {
        return listaPerfil;
    }

    public void setListaPerfil(List<Perfil> listaPerfil) {
        this.listaPerfil = listaPerfil;
    }
    
    public void gerarListaPerfil(){
        listaPerfil = perfilDao.list("Select p from Perfil p");
        if (listaPerfil == null) {
            listaPerfil = new ArrayList<Perfil>();
        }
    }
    
    
    public void salvar(){
        usuario.setPerfil(perfil);
        usuario = usuarioDao.update(usuario);
        RequestContext.getCurrentInstance().closeDialog(usuario);
    }
}
