/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.uil.Mensagem;
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
public class CadClienteMB implements  Serializable{
    
    
    @EJB
    private ClienteDao clienteDao;
    private Cliente cliente;
    private Boolean ePassaporte;
    private String cpfCliente;
    private boolean noveDigito = false;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        cliente = (Cliente) session.getAttribute("cliente");
        ePassaporte = (Boolean) session.getAttribute("ePassaporte");
        cpfCliente = (String) session.getAttribute("cpfCliente");
        session.removeAttribute("cpfCliente");
        session.removeAttribute("ePassaporte");
        session.removeAttribute("cliente");
        if (cliente == null) {
            cliente = new Cliente();
        }else{
            if (cliente.getTelefone().length() > 12) {
                noveDigito = true;
            }
        }
        if (ePassaporte == null) {
            ePassaporte = false;
        }
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getePassaporte() {
        return ePassaporte;
    }

    public void setePassaporte(Boolean ePassaporte) {
        this.ePassaporte = ePassaporte;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public boolean isNoveDigito() {
        return noveDigito;
    }

    public void setNoveDigito(boolean noveDigito) {
        this.noveDigito = noveDigito;
    }

    
    
    
    public void salvar(){
        if (ePassaporte) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("cliente", cliente);
        }
        cliente = clienteDao.update(cliente);
        RequestContext.getCurrentInstance().closeDialog(cliente);
    }
    
    public String cancelar(){
        if (ePassaporte) {
            return "cadPassaporte";
        }
        RequestContext.getCurrentInstance().closeDialog(cliente);
        return "";
    }
    
    
    public String habilitarNoveDigito(){
        if (noveDigito) {
            return "(99)999999999";
        }else{
            return "(99)99999999";
        }
    }
}
