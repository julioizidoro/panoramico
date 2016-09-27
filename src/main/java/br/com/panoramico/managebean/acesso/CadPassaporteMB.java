/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.acesso;

import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.dao.PassaporteDao;
import br.com.panoramico.dao.PassaporteValorDao;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Passaporte;
import br.com.panoramico.model.Passaportevalor;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class CadPassaporteMB implements Serializable{
    
    
    @EJB
    private ClienteDao clienteDao;
    private Cliente cliente;
    private Integer adultos;
    private Integer criancas;
    private Float valorTotal;
    private Passaporte passaporte;
    private String cpfCliente;
    @EJB
    private PassaporteDao passaporteDao;
    private Passaportevalor passaportevalor;
    @EJB
    private PassaporteValorDao passaporteValorDao;
    
    
    @PostConstruct
    public void init(){
        if (passaporte == null) {
            passaporte = new Passaporte();
            passaporte.setDatacompra(new Date());
        }
        getValoresPassaporte();
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

    public Integer getAdultos() {
        return adultos;
    }

    public void setAdultos(Integer adultos) {
        this.adultos = adultos;
    }

    public Integer getCriancas() {
        return criancas;
    }

    public void setCriancas(Integer criancas) {
        this.criancas = criancas;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Passaporte getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(Passaporte passaporte) {
        this.passaporte = passaporte;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    
 
    public String pesquisarCliente(){
        List<Cliente> listaCliente = clienteDao.list("Select c From Cliente c Where c.cpf='" + cpfCliente + "'");
        if (listaCliente == null || listaCliente.isEmpty()) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("cpfCliente", cpfCliente);
            return "cadCliente";
        }else{
            for (int i = 0; i < listaCliente.size(); i++) {
                cliente = listaCliente.get(i);
            }
            Mensagem.lancarMensagemInfo("Cliente: ", cliente.getNome() + " encontrado");
            return "";
        }
    }
    
    
    public void calcularValorTotal(){
        if (passaportevalor == null || passaportevalor.getIdpassaportevalor() == null) {
            valorTotal = 0.0f;
        }else{
            valorTotal = ((passaportevalor.getValoradulto() * adultos) + (passaportevalor.getValorcrianca() * criancas));
        }
    }
    
    public void getValoresPassaporte(){
        passaportevalor = passaporteValorDao.find("Select pv From Passaportevalor pv Where pv.situacao=1");
    }
    
}
