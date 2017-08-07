/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.MedicoDao;
import br.com.panoramico.dao.UsuarioDao;
import br.com.panoramico.model.Medico;
import br.com.panoramico.model.Usuario;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadMedicoMB implements Serializable {

    private Medico medico;
    private Usuario usuario;
    @EJB
    private MedicoDao medicoDao;
    @EJB
    private UsuarioDao usuarioDao;
    private List<Usuario> listaUsuarios;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        medico = (Medico) session.getAttribute("medico");
        if (medico == null) {
            medico = new Medico();
        } else {
            usuario = usuarioDao.find(medico.getIdusuario());
        }
        gerarListaUsuarios();
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public MedicoDao getMedicoDao() {
        return medicoDao;
    }

    public void setMedicoDao(MedicoDao medicoDao) {
        this.medicoDao = medicoDao;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public void gerarListaUsuarios() {
        listaUsuarios = usuarioDao.list("select u from Usuario u where u.situacao=true");
        if (listaUsuarios == null) {
            listaUsuarios = new ArrayList<Usuario>();
        }
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(new Medico());
    }

    public void salvar() {
        medico.setIdusuario(usuario.getIdusuario());
        String mensagem = validarDados();
        if (mensagem.length() < 5) {
            medico.setSituacao("Ativo");
            medico = medicoDao.update(medico);
            RequestContext.getCurrentInstance().closeDialog(medico);
        } else {
            Mensagem.lancarMensagemInfo("", mensagem);
        }
    }

    public String validarDados() {
        String msg = "";
        if (medico.getNome().equalsIgnoreCase("")) {
            msg = msg + " Nome não informado \r\n";
        }
        if (medico.getCrm().equalsIgnoreCase("")) {
            msg = msg + " CRM não informado \r\n";
        }
        if (medico.getIdusuario() == 0) {
            msg = msg + " Usuario não informado \r\n";
        }
        return msg;
    }
}
