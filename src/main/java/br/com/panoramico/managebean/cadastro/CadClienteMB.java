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
    private boolean noveDigito2 = false;
    private boolean noveDigito3 = false;
    private String telefone = "";
    
    
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

    public boolean isNoveDigito2() {
        return noveDigito2;
    }

    public void setNoveDigito2(boolean noveDigito2) {
        this.noveDigito2 = noveDigito2;
    }

    public boolean isNoveDigito3() {
        return noveDigito3;
    }

    public void setNoveDigito3(boolean noveDigito3) {
        this.noveDigito3 = noveDigito3;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    
    public void salvar(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        String msg = "";
        if (ePassaporte) {
            session.setAttribute("cliente", cliente);
        }
        msg = validarDados();
        if (msg.length() > 0) {
            Mensagem.lancarMensagemInfo("", msg);
        }else{
            boolean novo = false;
            if (cliente.getIdcliente()==null){
                novo=true;
            }
            cliente = clienteDao.update(cliente);
            if (novo) {
                session.setAttribute("idCliente", cliente.getIdcliente());
            }
            RequestContext.getCurrentInstance().closeDialog(cliente);
        }
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
    
    public String habilitarNoveDigito2(){
        if (noveDigito2) {
            return "(99)999999999";
        }else{
            return "(99)99999999";
        }
    }
    
    public String habilitarNoveDigito3(){
        if (noveDigito3) {
            return "(99)999999999";
        }else{
            return "(99)99999999";
        }
    }
    
    
    public void metodoKeyUp(){
        if (telefone != null && (telefone.length() > 0 && telefone.length() <= 1)) {
            telefone = "(" + telefone;
        }
        if (telefone != null && telefone.length() == 3) {
            telefone = telefone + ")";
        }
    }
    
    public String validarDados(){
        String mensagem = "";
        if (cliente.getCpf() == null || cliente.getCpf().length() == 0) {
            mensagem = mensagem + " Informe o CPF \r\n";
        }else{
            Cliente c = clienteDao.find("Select c From Cliente c Where c.cpf='" + cliente.getCpf() + "'");
            if (c == null || c.getIdcliente() == null) {
            }else{
                mensagem = mensagem + " Este CPF ja existe \r\n";
            }
        }
        return mensagem;
    }
}
