/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean;

import br.com.panoramico.dao.UsuarioDao;
import br.com.panoramico.model.Usuario;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Kamilla Rodrigues
 */

@Named
@SessionScoped
public class UsuarioLogadoMB implements Serializable{
    
    private Usuario usuario;
    @EJB
    private UsuarioDao usuarioDao;
    private String nomeCliente;
    private String novaSenha;
    private String senhaAtual;
    private String confirmaNovaSenha;
    private boolean cadastrar;
    private boolean editar;
    private boolean excluir;

    public UsuarioLogadoMB() {
        this.usuario = new Usuario();
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

    public boolean isExcluir() {
        return excluir;
    }

    public void setExcluir(boolean excluir) {
        this.excluir = excluir;
    }

   
    
    public String getConfirmaNovaSenha() {
        return confirmaNovaSenha;
    }

    public void setConfirmaNovaSenha(String confirmaNovaSenha) {
        this.confirmaNovaSenha = confirmaNovaSenha;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    
    
    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }
    
    
    
    public String validarUsuario() {
        if ((usuario.getLogin() != null) && (usuario.getSenha() == null)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Login Invalido."));
        } else {
            String senha = "";
            try {
                senha = Criptografia.encript(usuario.getSenha());
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
                FacesMessage mensagem = new FacesMessage("Erro: " + ex);
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }
            List<Usuario> listaUsuario = usuarioDao.list("Select u from Usuario u where u.login='" + usuario.getLogin() + "' and u.senha='" + senha + "'");
            if (listaUsuario == null || listaUsuario.isEmpty()) {
                Mensagem.lancarMensagemInfo("", "Acesso negado!!");
            } else {
                usuario.setSenha(senha);
                usuario = usuarioDao.find("select u from Usuario u where u.login='" + usuario.getLogin() + "' and u.senha='" + usuario.getSenha() + "'");
                if (usuario == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Acesso Negado."));
                } else {
                    verificarPerfilUsuario(usuario);
                    return "incial";
                }
            }
        }
        usuario = new Usuario();
        return "";
    }
     
    
     public void validarTrocarSenha() {
        if ((!usuario.getLogin().equalsIgnoreCase("")) && (usuario.getSenha().equalsIgnoreCase("")) || (usuario.getLogin().equalsIgnoreCase("")) && (!usuario.getSenha().equalsIgnoreCase(""))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Login Invalido."));
        } else {
            String senha = "";
            try {
                senha = Criptografia.encript(usuario.getSenha());
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
                FacesMessage mensagem = new FacesMessage("Erro: " + ex);
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }
            usuario.setSenha(senha);
                usuario = usuarioDao.find("Select u from Usuario u where u.login='"+ usuario.getLogin() +"' and u.senha='"+ usuario.getSenha()+"'");
                if (usuario == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
                }else{ 
                	Map<String, Object> options = new HashMap<String, Object>();
                	options.put("contentWidth", 400);
                	options.put("closable", false);
                	RequestContext.getCurrentInstance().openDialog("cadNovaSenha", options, null);
                }

        }
    }
     public String confirmaTrocaSenha() {
    	 String repetirSenhaAtual = "";
    	 try {
			repetirSenhaAtual = Criptografia.encript(senhaAtual);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if (repetirSenhaAtual.equalsIgnoreCase(usuario.getSenha())) {
    	
	        if ((novaSenha.length() > 0) && (confirmaNovaSenha.length() > 0)) {
                    if (novaSenha.equalsIgnoreCase(confirmaNovaSenha)) { 
	                String senha = "";
	                try {
	                    senha = Criptografia.encript(novaSenha);
	                } catch (NoSuchAlgorithmException ex) {
	                    Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
	                    FacesMessage mensagem = new FacesMessage("Erro: " + ex);
	                    FacesContext.getCurrentInstance().addMessage(null, mensagem);
	                }
                            usuario.setSenha(senha);
	                    usuario = usuarioDao.update(usuario);
	                    novaSenha = "";
	                    confirmaNovaSenha = "";
                            usuario = new Usuario();
                            RequestContext.getCurrentInstance().closeDialog(usuario);
                            return "";
	            } else {
	                novaSenha = "";
	                confirmaNovaSenha = "";
	                senhaAtual = "";
                        usuario = new Usuario();
	                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
	            }
	
	        } else {
	        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
	        }
    	}else{
    		
    		Mensagem.lancarMensagemInfo("Alteração Negada", "");
    		senhaAtual = "";
    		novaSenha = "";
    		confirmaNovaSenha = "";
                usuario = new Usuario();
    	}
        return "";
    }
    
     public String cancelarTrocaSenha(){
        usuario = new Usuario();
        novaSenha="";
        confirmaNovaSenha="";
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
     
     
     public void retornoDialogAlteracaoSenha(SelectEvent event) {
         Usuario usuario = (Usuario) event.getObject();
         if (usuario != null) {
         	Mensagem.lancarMensagemInfo("Senha", "alterada com sucesso");
         }
     }
     
     
     public String deslogar(){
         Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();  
         sessionMap.clear();  
         return "index";
     }
     
     public void verificarPerfilUsuario(Usuario usuario){
         if(usuario.getPerfil().getAcesso().getIdacesso() == 1) {
             cadastrar = true;
             editar = true;
             excluir = true;
         }else if (usuario.getPerfil().getAcesso().getIdacesso() == 2) {
             cadastrar = true;
             editar = true;
         }else if (usuario.getPerfil().getAcesso().getIdacesso() == 3) {
             cadastrar = true;
             excluir = true;
         }else if (usuario.getPerfil().getAcesso().getIdacesso() == 4) {
             editar = true;
             excluir = true;
         }else if(usuario.getPerfil().getAcesso().getIdacesso() == 5){
             cadastrar = true;
         }else if(usuario.getPerfil().getAcesso().getIdacesso() == 6){
             editar = true;
         }else if(usuario.getPerfil().getAcesso().getIdacesso() == 7){
             excluir = true;
         }
     }
}
