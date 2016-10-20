/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.dao.DependenteDao;
import br.com.panoramico.dao.PlanoDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Dependente;
import br.com.panoramico.model.Plano;
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

/**
 *
 * @author Julio
 */
@Named
@ViewScoped
public class CadAssociadoMB implements Serializable {

    @EJB
    private DependenteDao dependenteDao;
    @EJB
    private AssociadoDao associadoDao;
    private Associado associado;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private PlanoDao planoDao;
    private Plano plano;
    private List<Plano> listaPlano;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        associado = (Associado) session.getAttribute("associado");
        session.removeAttribute("associado");
        gerarListaCliente();
        gerarListaPlano();
        if (associado == null) {
            associado = new Associado();
            if (cliente == null) {
                cliente = new Cliente();
            }
            if (plano == null) {
                plano = new Plano();
            }
        } else {
            cliente = associado.getCliente();
            plano = associado.getPlano();
        }

    }

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
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

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public PlanoDao getPlanoDao() {
        return planoDao;
    }

    public void setPlanoDao(PlanoDao planoDao) {
        this.planoDao = planoDao;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public List<Plano> getListaPlano() {
        return listaPlano;
    }

    public void setListaPlano(List<Plano> listaPlano) {
        this.listaPlano = listaPlano;
    }

    public void salvar() {
        associado.setPlano(plano);
        associado.setCliente(cliente);
        if (associado.getSituacao().equalsIgnoreCase("Inativo")) {
            if (associado.getDependenteList() != null && associado.getDependenteList().size() > 0) {
                Dependente dependente;
                for (int i = 0; i < associado.getDependenteList().size(); i++) {
                    dependente = associado.getDependenteList().get(i);
                    dependente.setSituacao("Inativo");
                    dependenteDao.update(dependente);
                }
            }
        }
        associado = associadoDao.update(associado);
        RequestContext.getCurrentInstance().closeDialog(associado);
    }

    private void gerarListaCliente() {
        listaCliente = clienteDao.list("Select c from Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }
    }

    public void gerarListaPlano() {
        listaPlano = planoDao.list("Select p from Plano p");
        if (listaPlano == null) {
            listaPlano = new ArrayList<Plano>();
        }
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(new Associado());
    }

}
