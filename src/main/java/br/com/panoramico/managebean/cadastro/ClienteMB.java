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
public class ClienteMB implements Serializable{
    
    @EJB
    private ClienteDao clienteDao;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    
    
    @PostConstruct
    public void init(){
       gerarListaCliente();
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

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    
    
    public void gerarListaCliente(){
        listaCliente = clienteDao.list("Select c from Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }
    }
    
    
    public String novoCadastroCliente() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("cadCliente", options, null);
        return "";
    }
    
    
    public void retornoDialogNovo(SelectEvent event){
        Cliente cliente = (Cliente) event.getObject();
        if (cliente.getIdcliente() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de cliente realizado com sucesso");
        }
        gerarListaCliente();
    }
    
    
    public void editar(Cliente cliente){
        if (cliente != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("cliente", cliente);
            options.put("contentWidth", 600);
            RequestContext.getCurrentInstance().openDialog("cadCliente", options, null);
        }
    }
    
   
    public void excluir(Cliente cliente){
        clienteDao.remove(cliente.getIdcliente());
        Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
        gerarListaCliente();
    }
}
