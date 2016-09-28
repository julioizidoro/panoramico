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
import java.io.Serializable;
import java.util.ArrayList;
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
    private Integer adultos = 0;
    private Integer criancas = 0;
    private Float valorTotal = 0.0f;
    private Passaporte passaporte;
    private String cpfCliente;
    @EJB
    private PassaporteDao passaporteDao;
    private Passaportevalor passaportevalor;
    private List<Passaportevalor> listaPassaporteValor;
    @EJB
    private PassaporteValorDao passaporteValorDao;
    private float totalValorAdulto;
    private float totalValorCrianca;
    private String formaPagamento;
    
    
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

    public PassaporteDao getPassaporteDao() {
        return passaporteDao;
    }

    public void setPassaporteDao(PassaporteDao passaporteDao) {
        this.passaporteDao = passaporteDao;
    }

    public Passaportevalor getPassaportevalor() {
        return passaportevalor;
    }

    public void setPassaportevalor(Passaportevalor passaportevalor) {
        this.passaportevalor = passaportevalor;
    }

    public PassaporteValorDao getPassaporteValorDao() {
        return passaporteValorDao;
    }

    public void setPassaporteValorDao(PassaporteValorDao passaporteValorDao) {
        this.passaporteValorDao = passaporteValorDao;
    }

    public float getTotalValorAdulto() {
        return totalValorAdulto;
    }

    public void setTotalValorAdulto(float totalValorAdulto) {
        this.totalValorAdulto = totalValorAdulto;
    }

    public float getTotalValorCrianca() {
        return totalValorCrianca;
    }

    public void setTotalValorCrianca(float totalValorCrianca) {
        this.totalValorCrianca = totalValorCrianca;
    }

    public List<Passaportevalor> getListaPassaporteValor() {
        return listaPassaporteValor;
    }

    public void setListaPassaporteValor(List<Passaportevalor> listaPassaporteValor) {
        this.listaPassaporteValor = listaPassaporteValor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
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
            return "";
        }  
    }
    
    
    public void calcularValorTotal(){
        if (passaportevalor == null || passaportevalor.getIdpassaportevalor() == null) {
            valorTotal = 0.0f;
        }else{
            totalValorAdulto = passaportevalor.getValoradulto() * adultos;
            totalValorCrianca = passaportevalor.getValorcrianca() * criancas;
            valorTotal = totalValorAdulto + totalValorCrianca;
        }
    }
    
    public void getValoresPassaporte(){
        listaPassaporteValor = passaporteValorDao.list("Select pv From Passaportevalor pv Where pv.situacao=1");
        if (listaPassaporteValor == null || listaPassaporteValor.isEmpty()) {
            listaPassaporteValor = new ArrayList<Passaportevalor>();
        }
    }
    
    
    public void pegar(){
        passaportevalor.setValoradulto(passaportevalor.getValoradulto());
    }
    
}
