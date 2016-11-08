/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.acesso;

import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.PassaporteDao;
import br.com.panoramico.dao.RecebimentoDao;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Passaporte;
import br.com.panoramico.model.Recebimento;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

@Named
@ViewScoped
public class PassaporteMB implements Serializable {

    private Passaporte passaporte;
    @EJB
    private PassaporteDao passaporteDao;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    @EJB
    private ClienteDao clienteDao;
    private List<Passaporte> listaPassaporte;
    private Date dataInicioCompra;
    private Date dataFinalCompra;
    private Date dataInicialUso;
    private Date dataFinalUso;
    private String localCompra;
    private String passaporteUtilizado;
    @EJB
    private ContasReceberDao contasReceberDao;
    private Contasreceber contasreceber;
    private List<Recebimento> listaRecebimento;
    @EJB
    private RecebimentoDao recebimentoDao;

    @PostConstruct
    public void init() {
        gerarListaPassaporte();
        gerarListaCliente();
    }

    public Passaporte getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(Passaporte passaporte) {
        this.passaporte = passaporte;
    }

    public PassaporteDao getPassaporteDao() {
        return passaporteDao;
    }

    public void setPassaporteDao(PassaporteDao passaporteDao) {
        this.passaporteDao = passaporteDao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public List<Passaporte> getListaPassaporte() {
        return listaPassaporte;
    }

    public void setListaPassaporte(List<Passaporte> listaPassaporte) {
        this.listaPassaporte = listaPassaporte;
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public Date getDataInicioCompra() {
        return dataInicioCompra;
    }

    public void setDataInicioCompra(Date dataInicioCompra) {
        this.dataInicioCompra = dataInicioCompra;
    }

    public Date getDataFinalCompra() {
        return dataFinalCompra;
    }

    public void setDataFinalCompra(Date dataFinalCompra) {
        this.dataFinalCompra = dataFinalCompra;
    }

    public Date getDataInicialUso() {
        return dataInicialUso;
    }

    public void setDataInicialUso(Date dataInicialUso) {
        this.dataInicialUso = dataInicialUso;
    }

    public Date getDataFinalUso() {
        return dataFinalUso;
    }

    public void setDataFinalUso(Date dataFinalUso) {
        this.dataFinalUso = dataFinalUso;
    }

    public String getLocalCompra() {
        return localCompra;
    }

    public void setLocalCompra(String localCompra) {
        this.localCompra = localCompra;
    }

    public String getPassaporteUtilizado() {
        return passaporteUtilizado;
    }

    public void setPassaporteUtilizado(String passaporteUtilizado) {
        this.passaporteUtilizado = passaporteUtilizado;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public List<Recebimento> getListaRecebimento() {
        return listaRecebimento;
    }

    public void setListaRecebimento(List<Recebimento> listaRecebimento) {
        this.listaRecebimento = listaRecebimento;
    }

    
    
    
    

    public void gerarListaPassaporte() {
        String sql = "Select p From Passaporte p Where p.dataacesso is null";
        listaPassaporte = passaporteDao.list(sql);
        if (listaPassaporte == null || listaPassaporte.isEmpty()) {
            listaPassaporte = new ArrayList<Passaporte>();
        }
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("Select c From Cliente c");
        if (listaCliente == null || listaCliente.isEmpty()) {
            listaCliente = new ArrayList<Cliente>();
        }
    }

    public String novoPassaporte() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 550);
        RequestContext.getCurrentInstance().openDialog("cadPassaporte", options, null);
        return "";
    }

    public String utilizarPassaporte(Passaporte passaporte) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("passaporte", passaporte);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 400);
        RequestContext.getCurrentInstance().openDialog("utilizadoPassaporte", options, null);
        return "";
    }

    public void retornoDialogPassaporte(SelectEvent event) {
        Passaporte passaporte = (Passaporte) event.getObject();
        if (passaporte.getIdpassaporte() != null) {
            Mensagem.lancarMensagemInfo("Compra feita com sucesso", "");
            listaPassaporte.add(passaporte);
        }
    }

    public void retornoDialogUtilizado(SelectEvent event) {
        Passaporte passaporte = (Passaporte) event.getObject();
        if (passaporte.getIdpassaporte() != null) {
            Mensagem.lancarMensagemInfo("Acesso feito com sucesso", "");
            listaPassaporte.remove(passaporte);
        }
    }
    
    public void retornoDialogRecebimento(SelectEvent event) {
        Recebimento recebimento = (Recebimento) event.getObject();
        if (recebimento.getIdrecebimento() != null) {
            Mensagem.lancarMensagemInfo("Recebimento com sucesso", "");
        }
    }

    public void editar(Passaporte passaporte) {
        if (passaporte != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("passaporte", passaporte);
            options.put("contentWidth", 550);
            RequestContext.getCurrentInstance().openDialog("cadPassaporte", options, null);
        }
    }
    
    
    public void filtrar(){
        String sql = "Select p From Passaporte p";
        if ((cliente != null && cliente.getIdcliente() != null) || dataInicialUso != null || dataFinalUso != null || dataInicioCompra != null || dataFinalCompra != null
                || !passaporteUtilizado.equalsIgnoreCase("sn") || !localCompra.equalsIgnoreCase("sn")) {
            sql = sql + " Where";
        }
        if (cliente != null && cliente.getIdcliente() != null) {
            sql = sql + " p.cliente.idcliente=" + cliente.getIdcliente();
             if (dataInicialUso != null || dataFinalUso != null || dataInicioCompra != null || dataFinalCompra != null
                || !passaporteUtilizado.equalsIgnoreCase("sn") || !localCompra.equalsIgnoreCase("sn")) {
                sql = sql + " and";
            }
        }
        if (dataInicialUso != null && dataFinalUso != null) {
             sql = sql + " p.dataacesso>='" + Formatacao.ConvercaoDataSql(dataInicialUso) + "' and p.dataacesso<='" + 
                     Formatacao.ConvercaoDataSql(dataFinalUso) + "'";
             if (dataInicioCompra != null || dataFinalCompra != null
                || !passaporteUtilizado.equalsIgnoreCase("sn") || !localCompra.equalsIgnoreCase("sn")) {
                sql = sql + " and";
            }
        }
        if (dataInicioCompra != null && dataFinalCompra != null) {
             sql = sql + " p.datacompra>='" + Formatacao.ConvercaoDataSql(dataInicioCompra) + "' and p.datacompra<='" + 
                     Formatacao.ConvercaoDataSql(dataFinalCompra) + "'";
             if (!passaporteUtilizado.equalsIgnoreCase("sn") || !localCompra.equalsIgnoreCase("sn")) {
                sql = sql + " and";
            }
        }
        if (!localCompra.equalsIgnoreCase("sn")) {
            if (localCompra.equalsIgnoreCase("site")) {
                sql = sql + " p.localizador='PPA'";
            }else if(localCompra.equalsIgnoreCase("clube")){
                sql = sql + " p.localizador like 'PPA%'";
            }
        }
        if (!passaporteUtilizado.equalsIgnoreCase("sn")) {
            if (passaporteUtilizado.equalsIgnoreCase("sim")) {
                sql = sql + " p.dataacesso>='1900-01-01'";
            }else if(passaporteUtilizado.equalsIgnoreCase("nao")){
                sql = sql + " p.dataacesso is null";
            }
        }
        listaPassaporte = new ArrayList<Passaporte>();
        listaPassaporte = passaporteDao.list(sql); 
    }
    
    public void limparFiltro(){
        cliente = null;
        dataFinalCompra = null;
        dataFinalUso = null;
        dataInicialUso = null;
        dataInicioCompra = null;
        localCompra = "";
        passaporteUtilizado = "";
        gerarListaPassaporte();
    }
    
    
    public String novoRecebimento(Passaporte passaporte) {
        if (passaporte != null) {
           contasreceber = contasReceberDao.find("Select c From Contasreceber c Where c.numerodocumento='Passaporte-" + passaporte.getIdpassaporte() + "'");
           listaRecebimento = recebimentoDao.list("Select r From Recebimento r where r.contasreceber.idcontasreceber=" + contasreceber.getIdcontasreceber());
            if (listaRecebimento == null || listaRecebimento.isEmpty()) {
                Map<String, Object> options = new HashMap<String, Object>();
                options.put("contentWidth", 500);
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
                session.setAttribute("contasreceber", contasreceber);
                RequestContext.getCurrentInstance().openDialog("cadRecebimento", options, null);
            }else{
               Mensagem.lancarMensagemInfo("Este passaporte ja teve o valor recebido", "");
            }
        }
        return "";
    }
}
