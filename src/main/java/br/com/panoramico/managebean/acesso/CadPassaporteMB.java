/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.acesso;

import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.ParametrosDao;
import br.com.panoramico.dao.PassaporteDao;
import br.com.panoramico.dao.PassaporteValorDao;
import br.com.panoramico.dao.PlanoContaDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Parametros;
import br.com.panoramico.model.Passaporte;
import br.com.panoramico.model.Passaportevalor;
import br.com.panoramico.model.Planoconta;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadPassaporteMB implements Serializable {

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
    private float valorAdulto;
    private float valorCrianca;
    private boolean cadastrocliente = false;
    private boolean cadastropassaporte = true;
    private Parametros parametros;
    @EJB
    private ParametrosDao parametrosDao;
    private Planoconta planoconta;
    @EJB
    private PlanoContaDao planoContaDao;
    @EJB
    private ContasReceberDao contasReceberDao;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        cliente = (Cliente) session.getAttribute("cliente");
        passaporte = (Passaporte) session.getAttribute("passaporte");
        session.removeAttribute("cliente");
        if (passaporte == null) {
            passaporte = new Passaporte();
            passaporte.setDatacompra(new Date());
        }
        if (cliente != null) {
            cpfCliente = cliente.getCpf();
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

    public float getValorAdulto() {
        return valorAdulto;
    }

    public void setValorAdulto(float valorAdulto) {
        this.valorAdulto = valorAdulto;
    }

    public float getValorCrianca() {
        return valorCrianca;
    }

    public void setValorCrianca(float valorCrianca) {
        this.valorCrianca = valorCrianca;
    }

    public boolean isCadastrocliente() {
        return cadastrocliente;
    }

    public void setCadastrocliente(boolean cadastrocliente) {
        this.cadastrocliente = cadastrocliente;
    }

    public boolean isCadastropassaporte() {
        return cadastropassaporte;
    }

    public void setCadastropassaporte(boolean cadastropassaporte) {
        this.cadastropassaporte = cadastropassaporte;
    }

    public void pesquisarCliente() {
        List<Cliente> listaCliente = clienteDao.list("select c from Cliente c where c.cpf='" + cpfCliente + "'");
        if (listaCliente == null || listaCliente.isEmpty()) {
            cliente = new Cliente();
            cliente.setCpf(cpfCliente);
            Mensagem.lancarMensagemInfo("Cpf não encontrado", "cadastre um novo cliente");
            cadastropassaporte = false;
            cadastrocliente = true;
        } else {
            for (int i = 0; i < listaCliente.size(); i++) {
                cliente = listaCliente.get(i);
            }
            cadastropassaporte = true;
            cadastrocliente = false;
        }
    }

    public void calcularValorTotal() {
        if (passaportevalor == null || passaportevalor.getIdpassaportevalor() == null) {
            valorTotal = 0.0f;
        } else {
            totalValorAdulto = passaportevalor.getValoradulto() * adultos;
            totalValorCrianca = passaportevalor.getValorcrianca() * criancas;
            valorTotal = totalValorAdulto + totalValorCrianca;
        }
    }

    public void getValoresPassaporte() {
        listaPassaporteValor = passaporteValorDao.list("select pv from Passaportevalor pv where pv.situacao=1");
        if (listaPassaporteValor == null || listaPassaporteValor.isEmpty()) {
            listaPassaporteValor = new ArrayList<Passaportevalor>();
        }
    }

    public void pegar() {
        valorAdulto = passaportevalor.getValoradulto();
        valorCrianca = passaportevalor.getValorcrianca();
    }

    public void salvar() {
        String msg = validarDados();
        if (msg.length() < 5) {
            if (passaporte.getIdpassaporte() == null) {
                passaporte.setCliente(cliente);
                passaporte.setAdultos(adultos);
                passaporte.setCriancas(criancas);
                passaporte.setValorpago(valorTotal);
                passaporte.setFormapagamento(formaPagamento);
                passaporte = passaporteDao.update(passaporte);
                passaporte.setLocalizador("PPA" + passaporte.getIdpassaporte());
                passaporteDao.update(passaporte);
                lancarContasReceber();
            } else {
                passaporte.setCliente(cliente);
                passaporte.setAdultos(adultos);
                passaporte.setCriancas(criancas);
                passaporte.setValorpago(valorTotal);
                passaporte.setFormapagamento(formaPagamento);
                passaporte = passaporteDao.update(passaporte);
                passaporte.setLocalizador("PPA" + passaporte.getIdpassaporte());
                passaporteDao.update(passaporte);
            }
            RequestContext.getCurrentInstance().closeDialog(passaporte);
        }
    }

    public String validarDados() {
        String msg = "";
        if (cliente == null) {
            msg = msg + " informe seu cpf para cadastrar ou procurar o cliente \r\n";
        }
        return msg;
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(new Passaporte());
    }

    public void salvarCliente() {
        cliente = clienteDao.update(cliente);
        cadastropassaporte = true;
        cadastrocliente = false;
    }

    public void cancelarCliente() {
        cadastropassaporte = true;
        cadastrocliente = false;
    }

    public void lancarContasReceber() {
        Contasreceber contasreceber = new Contasreceber();
        contasreceber.setDatalancamento(new Date());
        contasreceber.setDatavencimento(new Date());
        contasreceber.setCliente(cliente);
        contasreceber.setNumeroparcela("1");
        contasreceber.setNumerodocumento("Passaporte-" + passaporte.getIdpassaporte());
        contasreceber.setValorconta(passaporte.getValorpago());
        contasreceber.setUsuario(usuarioLogadoMB.getUsuario());
        contasreceber.setEnviado(false);
        contasreceber.setSituacao("PAGAR");
        contasreceber.setTipopagamento(passaporte.getFormapagamento());
        parametros = parametrosDao.find(1);
        planoconta = planoContaDao.find(parametros.getPlanocontaavulso());
        contasreceber.setPlanoconta(planoconta);
        contasReceberDao.update(contasreceber);
    }

}
