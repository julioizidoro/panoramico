/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.notificacao;

import br.com.panoramico.dao.NotificacaoDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Notificacao;
import br.com.panoramico.model.Usuario;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class NotificacaoMB implements Serializable{
    
    
    private Notificacao notificacao;
    private List<Notificacao> listaNotificacao;
    @EJB
    private NotificacaoDao notificacaoDao;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Usuario usuario;
    private List<Usuario> listaUsuario;
    
    
    @PostConstruct
    public void init(){
        gerarListaNotificacoes();
    }

    public Notificacao getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Notificacao notificacao) {
        this.notificacao = notificacao;
    }

    public List<Notificacao> getListaNotificacao() {
        return listaNotificacao;
    }

    public void setListaNotificacao(List<Notificacao> listaNotificacao) {
        this.listaNotificacao = listaNotificacao;
    }

    public NotificacaoDao getNotificacaoDao() {
        return notificacaoDao;
    }

    public void setNotificacaoDao(NotificacaoDao notificacaoDao) {
        this.notificacaoDao = notificacaoDao;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
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
    
    
    
    public void gerarListaNotificacoes(){
        listaNotificacao = notificacaoDao.list("Select n from Notificacao n where n.usuariorecebe.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario()
                            + " and n.visto=0");
        if (listaNotificacao == null || listaNotificacao.isEmpty()) {
            listaNotificacao = new ArrayList<Notificacao>();
        }
    }
    
    public void vistoMensagem(Notificacao notificacao){
        if (notificacao != null) {
            notificacao.setVisto(true);
            notificacaoDao.update(notificacao);
            listaNotificacao.remove(notificacao);
            Mensagem.lancarMensagemInfo("Mensagem", "apagada com sucesso");
        }
    }
    
    
    
    public void fechar(){
        RequestContext.getCurrentInstance().closeDialog(new Notificacao());
    }
}
