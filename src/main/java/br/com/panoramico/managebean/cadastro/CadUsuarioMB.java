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
import br.com.panoramico.uil.Criptografia;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        usuario =  (Usuario) session.getAttribute("usuario");
        session.removeAttribute("usuario");
        gerarListaPerfil(); 
        if (usuario == null) {
            usuario = new Usuario();
            perfil = new Perfil();
        }else{
            perfil = usuario.getPerfil();
        }
        
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
    
    public void gerarListaPerfil() {
        listaPerfil = perfilDao.list("Select p from Perfil p");
        if (listaPerfil == null) {
            listaPerfil = new ArrayList<Perfil>();
        }
    }

    public String salvar() {
        List<Usuario> listaUsuario = usuarioDao.list("Select u from Usuario u where u.login='" + usuario.getLogin() + "'");
        if (listaUsuario == null || listaUsuario.isEmpty()) {
            usuario.setPerfil(perfil);
            try {
                usuario.setSenha(Criptografia.encript(usuario.getSenha()));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(CadUsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
            }
            usuario = usuarioDao.update(usuario);
            RequestContext.getCurrentInstance().closeDialog(usuario);
        } else if (usuario.getIdusuario() != null) {
            usuario.setPerfil(perfil);
            try {
                usuario.setSenha(Criptografia.encript(usuario.getSenha()));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(CadUsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
            }
            usuario = usuarioDao.update(usuario);
            RequestContext.getCurrentInstance().closeDialog(usuario);
        } else {
            Mensagem.lancarMensagemInfo("Atenção", "este login ja tem uma conta existente!!");
        }
        return "";
    }
     
        
    public void cancelar(){
        perfil = perfilDao.find(1);
        RequestContext.getCurrentInstance().closeDialog(usuario);
    }
}
