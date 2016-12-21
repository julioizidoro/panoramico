/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.DependenteDao;
import br.com.panoramico.dao.PlanoDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Dependente;
import br.com.panoramico.model.Plano;
import br.com.panoramico.uil.Mensagem;
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
import org.jfree.data.time.Month;
import org.jfree.data.time.Year;
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
    @EJB
    private ContasReceberDao contasReceberDao;
    private String situacaoAntiga;
    private float valorPlano = 0.0f;

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
            associado.setEstado("PR");
        } else {
            cliente = associado.getCliente();
            plano = associado.getPlano();
            situacaoAntiga = associado.getSituacao();
            valorPlano = plano.getValor();
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

    public DependenteDao getDependenteDao() {
        return dependenteDao;
    }

    public void setDependenteDao(DependenteDao dependenteDao) {
        this.dependenteDao = dependenteDao;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public String getSituacaoAntiga() {
        return situacaoAntiga;
    }

    public void setSituacaoAntiga(String situacaoAntiga) {
        this.situacaoAntiga = situacaoAntiga;
    }

    public float getValorPlano() {
        return valorPlano;
    }

    public void setValorPlano(float valorPlano) {
        this.valorPlano = valorPlano;
    }
    
    

    public void salvar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        associado.setPlano(plano);
        associado.setCliente(cliente);
        associado.setAno(new Year().getYear());
        associado.setMes(new Month().getMonth() + 1);
        String msg = validarDados();
        if (msg.length() > 0) {
            Mensagem.lancarMensagemInfo("", msg);
        }else{
            if(associado.getIdassociado()==null){
                associado.setDataassociacao(new Date());
            }
            if (associado.getSituacao().equalsIgnoreCase("Inativo")) {
                if (associado.getDependenteList() != null && associado.getDependenteList().size() > 0) {
                    Dependente dependente;
                    for (int i = 0; i < associado.getDependenteList().size(); i++) {
                        dependente = associado.getDependenteList().get(i);
                        dependente.setSituacao("Inativo");
                        dependenteDao.update(dependente);
                    }
                }
                List<Contasreceber> listaContasReceber = contasReceberDao.list("Select c from Contasreceber c where c.situacao<>'CANCELADO' and c.situacao<>'PAGO'");
                if (listaContasReceber != null && listaContasReceber.size() > 0) {
                    for (int i = 0; i < listaContasReceber.size(); i++) {
                        listaContasReceber.get(i).setSituacao("CANCELADO");
                        contasReceberDao.update(listaContasReceber.get(i));
                    }
                }
            }else if (situacaoAntiga != null && situacaoAntiga.equalsIgnoreCase("Inativo")
                    && !situacaoAntiga.equalsIgnoreCase(associado.getSituacao())) {
                if (associado.getDependenteList() != null && associado.getDependenteList().size() > 0) {
                    salvarDependentes();
                }
            }
            boolean novo = false;
            if (associado.getIdassociado()==null){
                novo=true;
            }
            associado = associadoDao.update(associado);
            if (novo){
                session.setAttribute("idAssociado", associado.getIdassociado());
            }
            RequestContext.getCurrentInstance().closeDialog(associado);
        }
    }

    public void salvarDependentes() {
        if (associado.getDependenteList() != null && associado.getDependenteList().size() > 0) {
            Dependente dependente;
            for (int i = 0; i < associado.getDependenteList().size(); i++) {
                dependente = associado.getDependenteList().get(i);
                dependente.setSituacao("Ativo");
                dependenteDao.update(dependente);
            }
        }
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
    
     public void pegarValorPlano(){
        if (plano != null) {
            valorPlano = plano.getValor();
        }
    }
     
     
    public String validarDados(){
        Associado socio;
        String mensagem = "";
        
        if (cliente == null) {
            mensagem = mensagem + " Informe o cliente para esse associado \r\n";
        }
        if (associado.getMatricula() == null || associado.getMatricula().length() == 0) {
            mensagem = mensagem + " Informe a matricula \r\n";
        }else{
            if(associado.getIdassociado()==null){
                socio = associadoDao.find("Select a From Associado a Where a.matricula='" + associado.getMatricula() + "'");
                if (socio == null || socio.getIdassociado() == null) {
                }else{
                    mensagem = mensagem + " Matricula ja existente \r\n";
                }
            }
        }
        if (plano == null) {
            mensagem = mensagem + " Informe o plano desde associado \r\n";
        }
        return mensagem;
    }

}
