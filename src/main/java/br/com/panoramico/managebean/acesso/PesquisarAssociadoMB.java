/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.acesso;

import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;


@Named
@ViewScoped
public class PesquisarAssociadoMB implements Serializable{
    
    private Cliente cliente;
    private String cpfCliente;
    private String matricula;
    @EJB
    private ClienteDao clienteDao;
    
    
    @PostConstruct
    public void init(){
        
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    
    public void pesquisar(){
        List<Cliente> listaCliente = clienteDao.list("Select c From Cliente c Where c.cpf='" + cpfCliente + "'");
        if (listaCliente == null || listaCliente.isEmpty()) {
            Mensagem.lancarMensagemInfo("Cliente não encontrado", "");
            cliente = null;
            matricula = "";
        }else{
            for (int i = 0; i < listaCliente.size(); i++) {
                cliente = listaCliente.get(i);
            }
            if (cliente.getAssociado() == null) {
                Mensagem.lancarMensagemInfo("Cliente não é associado", "");
                matricula = "";
            }else{
                matricula = cliente.getAssociado().getMatricula();
            }
        }
    }
    
    public void fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
    }
    
}
