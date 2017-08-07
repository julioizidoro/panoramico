/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AcessoDao;
import br.com.panoramico.dao.PerfilDao;
import br.com.panoramico.dao.UsuarioDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Perfil;
import br.com.panoramico.model.Usuario;
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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Julio
 */
@Named
@ViewScoped
public class PerfilMB implements Serializable {

    @EJB
    private PerfilDao perfilDao;
    private Perfil perfil;
    private List<Perfil> listaPerfil;
    @EJB
    private UsuarioDao usuarioDao;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private AcessoDao acessoDao;

    @PostConstruct
    public void init() {
        gerarListaPerfil();
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
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

    public AcessoDao getAcessoDao() {
        return acessoDao;
    }

    public void setAcessoDao(AcessoDao acessoDao) {
        this.acessoDao = acessoDao;
    }

    public void gerarListaPerfil() {
        listaPerfil = perfilDao.list("select p from Perfil p");
        if (listaPerfil == null) {
            listaPerfil = new ArrayList<Perfil>();
        }
    }

    public String novoCadastroPerfil() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 400);
        RequestContext.getCurrentInstance().openDialog("cadPerfil", options, null);
        return "";
    }

    public void retornoDialogNovo(SelectEvent event) {
        Perfil perfil = (Perfil) event.getObject();
        if (perfil.getIdperfil() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de perfil realizado com sucesso");
        }
        gerarListaPerfil();
    }

    public void retornoDialogAlteracao(SelectEvent event) {
        Perfil perfil = (Perfil) event.getObject();
        if (perfil.getIdperfil() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Ateração de perfil realizado com sucesso");
        }
        gerarListaPerfil();
    }

    public void editar(Perfil perfil) {
        if (perfil != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("perfil", perfil);
            options.put("contentWidth", 400);
            RequestContext.getCurrentInstance().openDialog("cadPerfil", options, null);
        }
    }

    public void excluir(Perfil perfil) {
        List<Usuario> listaPerfilUsuario = usuarioDao.list("select u from Usuario u where u.perfil.idperfil=" + perfil.getIdperfil());
        if (listaPerfilUsuario == null || listaPerfilUsuario.isEmpty()) {
            perfilDao.remove(perfil.getIdperfil());
            Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
            gerarListaPerfil();
        } else {
            Mensagem.lancarMensagemInfo("Atenção", " este perfil não pode ser excluido");
        }
    }
}
