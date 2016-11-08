/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contasreceber;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.AssociadoEmpresaDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.EmpresaDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Associadoempresa;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Empresa;
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

@Named
@ViewScoped
public class totalSerPagoMB implements Serializable{
    
    private Contasreceber contasreceber;
    private List<Contasreceber> listaContasAssociados;
    private List<Contasreceber> listaContasAssociadosEmpresa;
    @EJB
    private ContasReceberDao contasReceberDao;
    private Associado associado;
    private List<Associado> listaAssociado;
    @EJB
    private AssociadoDao associadoDao;
    private float valorTotalEmpresa;
    private float valorTotalAssociado;
    private List<Contasreceber> listaTotalContasAssociados;
    private List<Contasreceber> listaTotalContasAssociadoEmpresa;
    private boolean  empresa;
    private boolean selecionadoTodosEmpresa;
    private boolean selecionadoTodosAssociado;
    @EJB
    private EmpresaDao empresaDao;
    private List<Empresa> listaEmpresa;
    private Empresa empresaa;
    @EJB
    private AssociadoEmpresaDao associadoEmpresaDao;
    
    
    @PostConstruct
    public void init(){
        gerarListaAssociado();
        gerarListaEmpresa();
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public List<Contasreceber> getListaContasAssociados() {
        return listaContasAssociados;
    }

    public void setListaContasAssociados(List<Contasreceber> listaContasAssociados) {
        this.listaContasAssociados = listaContasAssociados;
    }

    public List<Contasreceber> getListaContasAssociadosEmpresa() {
        return listaContasAssociadosEmpresa;
    }

    public void setListaContasAssociadosEmpresa(List<Contasreceber> listaContasAssociadosEmpresa) {
        this.listaContasAssociadosEmpresa = listaContasAssociadosEmpresa;
    }

    

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public List<Associado> getListaAssociado() {
        return listaAssociado;
    }

    public void setListaAssociado(List<Associado> listaAssociado) {
        this.listaAssociado = listaAssociado;
    }

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }
    
    

    public float getValorTotalEmpresa() {
        return valorTotalEmpresa;
    }

    public void setValorTotalEmpresa(float valorTotalEmpresa) {
        this.valorTotalEmpresa = valorTotalEmpresa;
    }

    public float getValorTotalAssociado() {
        return valorTotalAssociado;
    }

    public void setValorTotalAssociado(float valorTotalAssociado) {
        this.valorTotalAssociado = valorTotalAssociado;
    }

    public List<Contasreceber> getListaTotalContasAssociados() {
        return listaTotalContasAssociados;
    }

    public void setListaTotalContasAssociados(List<Contasreceber> listaTotalContasAssociados) {
        this.listaTotalContasAssociados = listaTotalContasAssociados;
    }

    public List<Contasreceber> getListaTotalContasAssociadoEmpresa() {
        return listaTotalContasAssociadoEmpresa;
    }

    public void setListaTotalContasAssociadoEmpresa(List<Contasreceber> listaTotalContasAssociadoEmpresa) {
        this.listaTotalContasAssociadoEmpresa = listaTotalContasAssociadoEmpresa;
    }

    public boolean isEmpresa() {
        return empresa;
    }

    public void setEmpresa(boolean empresa) {
        this.empresa = empresa;
    }

    public boolean isSelecionadoTodosEmpresa() {
        return selecionadoTodosEmpresa;
    }

    public void setSelecionadoTodosEmpresa(boolean selecionadoTodosEmpresa) {
        this.selecionadoTodosEmpresa = selecionadoTodosEmpresa;
    }

    public boolean isSelecionadoTodosAssociado() {
        return selecionadoTodosAssociado;
    }

    public void setSelecionadoTodosAssociado(boolean selecionadoTodosAssociado) {
        this.selecionadoTodosAssociado = selecionadoTodosAssociado;
    }

    public EmpresaDao getEmpresaDao() {
        return empresaDao;
    }

    public void setEmpresaDao(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao;
    }

    public List<Empresa> getListaEmpresa() {
        return listaEmpresa;
    }

    public void setListaEmpresa(List<Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
    }

    public Empresa getEmpresaa() {
        return empresaa;
    }

    public void setEmpresaa(Empresa empresaa) {
        this.empresaa = empresaa;
    }

    public AssociadoEmpresaDao getAssociadoEmpresaDao() {
        return associadoEmpresaDao;
    }

    public void setAssociadoEmpresaDao(AssociadoEmpresaDao associadoEmpresaDao) {
        this.associadoEmpresaDao = associadoEmpresaDao;
    }

    
    
    
    
    public void gerarListaAssociado(){
        listaTotalContasAssociadoEmpresa = new ArrayList<>();
        listaTotalContasAssociados = new ArrayList<>();
        listaContasAssociados = new ArrayList<>();
        listaContasAssociadosEmpresa = new ArrayList<>();
        listaAssociado = associadoDao.list("Select a From Associado a Where a.situacao='Ativo'");
        if (listaAssociado == null || listaAssociado.isEmpty()) {
            listaAssociado = new ArrayList<>();
        }
        for (int i = 0; i < listaAssociado.size(); i++) {
            if (listaAssociado.get(i).getAssociadoempresaList().size() > 0) {
                for (int j = 0; j < listaAssociado.get(i).getAssociadoempresaList().size(); j++) {
                    gerarListaContasAssociadoEmpresa(listaAssociado.get(i).getAssociadoempresaList().get(j));
                }
            }else{
                    gerarListaContasAssociado(listaAssociado.get(i));
            }
        }
    }
    
    
    public void gerarListaContasAssociado(Associado associado){
        listaContasAssociados = contasReceberDao.list("Select c From Contasreceber c Where c.situacao='PAGAR' and c.tipopagamento='Boleto' and c.cliente.idcliente="
                    + associado.getCliente().getIdcliente());
        if (listaContasAssociados == null || listaContasAssociados.isEmpty()) {
            listaContasAssociados = new ArrayList<>();
        }
        for (int j = 0; j < listaContasAssociados.size(); j++) {
            valorTotalAssociado = valorTotalAssociado + listaContasAssociados.get(j).getValorconta();
            listaTotalContasAssociados.add(listaContasAssociados.get(j));
        }
    }
    
    public void gerarListaContasAssociadoEmpresa(Associadoempresa associadoempresa){
        listaContasAssociadosEmpresa = contasReceberDao.list("Select c From Contasreceber c Where c.situacao='PAGAR' and c.cliente.idcliente="
                + associadoempresa.getAssociado().getCliente().getIdcliente());
        if (listaContasAssociadosEmpresa == null || listaContasAssociadosEmpresa.isEmpty()) {
            listaContasAssociadosEmpresa = new ArrayList<>();
        }
            for (int j = 0; j < listaContasAssociadosEmpresa.size(); j++) {
                valorTotalEmpresa = valorTotalEmpresa + listaContasAssociadosEmpresa.get(j).getValorconta();
                listaTotalContasAssociadoEmpresa.add(listaContasAssociadosEmpresa.get(j));
            }
    }  
      
    
    public String boletoAssociado() {
        List<Contasreceber> listaSelecionada = new ArrayList<>();
        for (int i = 0; i < listaTotalContasAssociados.size(); i++) {
            if (listaTotalContasAssociados.get(i).isSelecionado()) {
                listaSelecionada.add(listaTotalContasAssociados.get(i));
            }
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        empresa = false;
        session.setAttribute("empresa", empresa);
        session.setAttribute("valorTotalEmpresa", valorTotalEmpresa);
        session.setAttribute("listaContasReceber", listaSelecionada);
        RequestContext.getCurrentInstance().openDialog("boletoTotalContas", options, null);
        return "";
    }
    
    public String boletoEmpresa() {
        List<Contasreceber> listaSelecionada = new ArrayList<>();
        for (int i = 0; i < listaTotalContasAssociadoEmpresa.size(); i++) {
            if (listaTotalContasAssociadoEmpresa.get(i).isSelecionado()) {
                listaSelecionada.add(listaTotalContasAssociadoEmpresa.get(i));
            }
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        empresa = true;
        session.setAttribute("valorTotalEmpresa", valorTotalEmpresa);
        session.setAttribute("empresa", empresa);
        session.setAttribute("listaContasReceber", listaSelecionada);
        RequestContext.getCurrentInstance().openDialog("boletoTotalContas", options, null);
        return "";
    }
    
    public void selecionarTodasListaEmpresa() {
        if (selecionadoTodosEmpresa) {
            for (int i = 0; i < listaTotalContasAssociadoEmpresa.size(); i++) {
                listaTotalContasAssociadoEmpresa.get(i).setSelecionado(true);
            }
        } else {
            for (int i = 0; i < listaTotalContasAssociadoEmpresa.size(); i++) {
                listaTotalContasAssociadoEmpresa.get(i).setSelecionado(false);
            }
        }
    }
    
    public void selecionarTodasListaAssociado() {
        if (selecionadoTodosAssociado) {
            for (int i = 0; i < listaTotalContasAssociados.size(); i++) {
                listaTotalContasAssociados.get(i).setSelecionado(true);
            }
        } else {
            for (int i = 0; i < listaTotalContasAssociados.size(); i++) {
                listaTotalContasAssociados.get(i).setSelecionado(false);
            }
        }
    }
    
    
    public void gerarListaEmpresa(){
        String sql = "Select e From Empresa e";
        listaEmpresa = empresaDao.list(sql);
        if (listaEmpresa == null || listaEmpresa.isEmpty()) {
            listaEmpresa = new ArrayList<>();
        }
    }
    
    public void filtrar(){
        listaTotalContasAssociadoEmpresa = new ArrayList<>();
        valorTotalEmpresa = 0.0f;
        String sql = "";
        List<Associadoempresa> listaAssociadoEmpresa;
        List<Contasreceber> listaContasEmpresa;
        if (empresaa == null) {
            gerarListaAssociado();  
        }else{
            sql = "Select ae From Associadoempresa ae Where ae.empresa.idempresa=" + empresaa.getIdempresa();
            listaAssociadoEmpresa = associadoEmpresaDao.list(sql);
            for (int i = 0; i < listaAssociadoEmpresa.size(); i++) {
                String sql2 = "Select c From Contasreceber c Where c.situacao='PAGAR' and c.cliente.idcliente=" + 
                listaAssociadoEmpresa.get(i).getAssociado().getCliente().getIdcliente();
                listaContasEmpresa = contasReceberDao.list(sql2);
                for (int j = 0; j < listaContasEmpresa.size(); j++) {
                    valorTotalEmpresa = valorTotalEmpresa + listaContasEmpresa.get(j).getValorconta();
                    listaTotalContasAssociadoEmpresa.add(listaContasEmpresa.get(j));
                }
            }
        }
    }
    
    
    public void limpar(){
        empresaa = null;
        gerarListaAssociado();
    }
    
   public String pegarNomeEmpresa(Contasreceber contasreceber){
       String nome = contasreceber.getCliente().getAssociado().getAssociadoempresaList().get(0).getEmpresa().getRazaosocial();
       return nome;
   }
}
