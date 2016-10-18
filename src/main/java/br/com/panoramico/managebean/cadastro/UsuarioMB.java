/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.UsuarioDao;
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
public class UsuarioMB implements Serializable {

    @EJB
    private UsuarioDao usuarioDao;
    private Usuario usuario;
    private List<Usuario> listaUsuario;
    private String nome;
    private String login;

    @PostConstruct
    public void init() {
        gerarListaUsuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void gerarListaUsuario() {
        listaUsuario = usuarioDao.list("Select u from Usuario u");
        if (listaUsuario == null) {
            listaUsuario = new ArrayList<Usuario>();
        }
    }

    public String novoCadastroUsuario() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 545);
        RequestContext.getCurrentInstance().openDialog("cadUsuario", options, null);
        return "";
    }

    public void retornoDialogNovo(SelectEvent event) {
        Usuario usuario = (Usuario) event.getObject();
        if (usuario.getIdusuario() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de usuario realizado com sucesso");
        }
        gerarListaUsuario();
    }

    public void retornoDialogAlteracao(SelectEvent event) {
        Usuario usuario = (Usuario) event.getObject();
        if (usuario.getIdusuario() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração de usuario realizado com sucesso");
        }
        gerarListaUsuario();
    }

    public void editar(Usuario usuario) {
        if (usuario != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("usuario", usuario);
            options.put("contentWidth", 545);
            RequestContext.getCurrentInstance().openDialog("cadUsuario", options, null);
        }
    }

    public void excluir(Usuario usuario) {
        usuarioDao.remove(usuario.getIdusuario());
        Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
        gerarListaUsuario();
    }

    public void pesquisar() {
        String sql = "Select u from Usuario u where u.nome like '%" + nome + "%'";
        if (login != null && login.length() > 0) {
            sql = sql + " and u.login='" + login + "'";
        }
        sql = sql + " order by u.nome";
        listaUsuario = usuarioDao.list(sql);
        if (listaUsuario == null) {
            listaUsuario = new ArrayList<Usuario>();
        }
    }

    public void limpar() {
        nome = "";
        login = null;
        gerarListaUsuario();
    }
}
