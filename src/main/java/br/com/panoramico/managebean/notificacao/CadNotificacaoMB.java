/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.notificacao;

import br.com.panoramico.dao.NotificacaoDao;
import br.com.panoramico.dao.UsuarioDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Notificacao;
import br.com.panoramico.model.Usuario;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadNotificacaoMB implements Serializable{
    
    private Usuario usuario;
    private List<Usuario> listaUsuarios;
    private Notificacao notificacao;
    @EJB
    private NotificacaoDao notificacaoDao;
    private Usuario usuarioEnviar;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private UsuarioDao usuarioDao;
    
    
    @PostConstruct
    public void init(){
        if (notificacao == null) {
            notificacao = new Notificacao();
            notificacao.setData(new Date());
            usuarioEnviar = usuarioLogadoMB.getUsuario();
            retornarHoraAtual();
        }
        gerarListaUsuario();
    }

    
    public Usuario getUsuario() {
        return usuario; 
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Notificacao getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Notificacao notificacao) {
        this.notificacao = notificacao;
    }

    public NotificacaoDao getNotificacaoDao() {
        return notificacaoDao;
    }

    public void setNotificacaoDao(NotificacaoDao notificacaoDao) {
        this.notificacaoDao = notificacaoDao;
    }

    public Usuario getUsuarioEnviar() {
        return usuarioEnviar;
    }

    public void setUsuarioEnviar(Usuario usuarioEnviar) {
        this.usuarioEnviar = usuarioEnviar;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }
    
    
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Notificacao());
    }
    
    public void salvar(){
        notificacao.setUsuarioenvia(usuarioEnviar);
        notificacao.setUsuariorecebe(usuario);
        notificacao.setVisto(false);
        String msg = validarDados();
        if (msg.length() < 5) {
            notificacao = notificacaoDao.update(notificacao);
            RequestContext.getCurrentInstance().closeDialog(notificacao);
        }else{
            Mensagem.lancarMensagemInfo("", msg);
        }
    }
     
    public String validarDados(){
        String mensagem = "";
        if (notificacao.getAssunto().equalsIgnoreCase("")) {
            mensagem = mensagem + " você não escreveu nenhum assunto \r\n";
        }
        if (notificacao.getUsuariorecebe() == null) {
            mensagem = mensagem + " você não escolheu o destinário \r\n";
        }
        return mensagem;
    }
    
   public void gerarListaUsuario(){
       listaUsuarios = usuarioDao.list("Select u from Usuario u where u.idusuario<>" + usuarioLogadoMB.getUsuario().getIdusuario());
       if (listaUsuarios == null || listaUsuarios.isEmpty()) {
           listaUsuarios = new ArrayList<Usuario>();
       }
   }
   
   public void retornarHoraAtual(){
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    Date hora = Calendar.getInstance().getTime();
    notificacao.setHora(sdf.format(hora));
   }
}
