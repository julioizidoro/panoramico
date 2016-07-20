/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean;

import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.dao.UsuarioDao;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Usuario;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
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

    public UsuarioLogadoMB() {
        this.usuario = new Usuario();
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
            usuario.setSenha(senha);
            usuario = usuarioDao.find("select u from Usuario u where u.login='" + usuario.getLogin() + "' and u.senha='" + usuario.getSenha() + "'  order by u.nome");
            if (usuario == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Acesso Negado."));
            } else {
                return "incial";
            }
        }
        usuario = new Usuario();
        return "";
    }
    
    
     public void validarTrocarSenha() {
        if ((usuario.getLogin() != null) && (usuario.getSenha() == null) || (usuario.getLogin() == null) && (usuario.getSenha() != null)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Login Invalido."));
        } else {
            usuarioDao = new UsuarioDao();
            String senha = "";
            try {
                senha = Criptografia.encript(usuario.getSenha());
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
                FacesMessage mensagem = new FacesMessage("Erro: " + ex);
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }
            usuario.setSenha(senha);
            try {
                usuario = usuarioDao.consultar(usuario.getLogin(), usuario.getSenha());
                if (usuario == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
                }else{ 
                	Map<String, Object> options = new HashMap<String, Object>();
                	options.put("contentWidth", 300);
                	options.put("closable", false);
                	RequestContext.getCurrentInstance().openDialog("cadNovaSenha", options, null);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
                FacesMessage mensagem = new FacesMessage("Erro: " + ex);
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }

        }
    }
     public String confirmaNovaSenha() {
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
	        		usuarioDao = new UsuarioDao();
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
                            RequestContext.getCurrentInstance().closeDialog(usuario);
                            return "";
	            } else {
	                novaSenha = "";
	                confirmaNovaSenha = "";
	                senhaAtual = "";
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
}
