/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Ccancelamento;
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
public class ClienteMB implements Serializable {

    @EJB
    private ClienteDao clienteDao;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String sql;
    @EJB
    private AssociadoDao associadoDao;
    private boolean temSql = false;
    private int idCliente = 0;
    private String situacao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        sql = (String) session.getAttribute("sql");
        Integer idC = (Integer) session.getAttribute("idCliente");
        if (idC == null) {
            idC = 0;
        }
        idCliente = idC;
        if (idCliente > 0) {
            sql = null;
            gerarListaCliente();
        } else if (sql != null) {
            temSql = true;
            gerarListaCliente();
        }
        session.removeAttribute("sql");
        session.removeAttribute("idCliente");
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public boolean isTemSql() {
        return temSql;
    }

    public void setTemSql(boolean temSql) {
        this.temSql = temSql;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public void gerarListaCliente() {
        if (!temSql) {
            if (idCliente > 0) {
                sql = "select a from Cliente a where a.idcliente>" + (idCliente - 5) + " and a.situacao<>'Inativo' order by a.idcliente DESC";
                listaCliente = clienteDao.list(sql);
            }
        } else {
            listaCliente = clienteDao.list(sql);
        }
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }
    }

    public String novoCadastroCliente() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("sql", sql);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 550);
        RequestContext.getCurrentInstance().openDialog("cadCliente", options, null);
        return "";
    }

    public void retornoDialogNovo(SelectEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        sql = (String) session.getAttribute("sql");
        session.removeAttribute("sql");
        Cliente cliente = (Cliente) event.getObject();
        if (cliente.getIdcliente() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de cliente realizado com sucesso");
            if (listaCliente == null) {
                listaCliente = new ArrayList<>();
            }
            listaCliente.add(cliente);
        }
    }

    public void retornoDialogAlteracao(SelectEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        sql = (String) session.getAttribute("sql");
        session.removeAttribute("sql");
        Cliente cliente = (Cliente) event.getObject();
        if (cliente.getIdcliente() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração do cliente realizado com sucesso");
        }
        gerarListaCliente();
    }

    public void editar(Cliente cliente) {
        if (cliente != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("cliente", cliente);
            session.setAttribute("sql", sql);
            options.put("contentWidth", 550);
            RequestContext.getCurrentInstance().openDialog("cadCliente", options, null);
        }
    }

    public void excluir(Cliente cliente) {
        List<Associado> listaAssociado = associadoDao.list("select a from Associado a where a.cliente.idcliente=" + cliente.getIdcliente());
        if (listaAssociado == null || listaAssociado.isEmpty()) {
            clienteDao.remove(cliente.getIdcliente());
            Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
            listaCliente.remove(cliente);
        } else {
            Mensagem.lancarMensagemInfo("Atenção", " este cliente não pode ser excluido");
        }
    }

    public String novoCancelamento(Cliente cliente) {
        if (cliente != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 400);
            options.put("closable", false);
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("cliente", cliente);
            RequestContext.getCurrentInstance().openDialog("cadCancelamentoCliente", options, null);
        }
        return "";
    }

    public String limpar() {
        temSql = false;
        listaCliente = new ArrayList<>();
        gerarListaCliente();
        nome = "";
        cpf = "";
        email = "";
        telefone = "";
        return "";
    }

    public String pesquisar() {
        temSql = true;
        sql = "select c from Cliente c where c.nome like '%" + nome + "%' ";
        if (cpf.length() > 0) {
            sql = sql + " and c.cpf='" + cpf + "' ";
        }
        if (email.length() > 0) {
            sql = sql + " and c.email='" + email + "' ";
        }
        if (telefone.length() > 0) {
            sql = sql + " and c.telefone='" + telefone + "' ";
        }
        if (situacao != null && !situacao.equalsIgnoreCase("sn")) {
            sql = sql + " and c.situacao='" + situacao + "' ";
        }
        sql = sql + " order by c.nome";
        gerarListaCliente();
        return "";
    }

    public boolean desabilitarBotao(Cliente cliente) {
        if (cliente != null && cliente.getSituacao().equalsIgnoreCase("Inativo")) {
            return true;
        }
        return false;
    }

    public void retornoDialogCencelamento(SelectEvent event) {
        Ccancelamento ccancelamento = (Ccancelamento) event.getObject();
        if (ccancelamento.getIdccancelamento() != null) {
            Mensagem.lancarMensagemFatal("Cancalmento feito com sucesso", "");
        }
    }
}
