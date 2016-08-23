/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.boleto;

import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.EmpresaDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Empresa;
import br.com.panoramico.uil.Formatacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.jrimum.bopepo.Boleto;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;

@Named
@ViewScoped
public class BoletoMB implements Serializable{
    
    private Contasreceber contasreceber;
    private List<Contasreceber> listaContasReceber;
    @EJB
    private ContasReceberDao contasReceberDao;
    private boolean selecionadoTodos;
    private List<Contasreceber> listaSelecionadas;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private EmpresaDao empresaDao;
    private Empresa empresa;
    
    
    @PostConstruct
    public void init(){
        gerarListaContasReceber();
        empresa = empresaDao.find(1);
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public List<Contasreceber> getListaContasReceber() {
        return listaContasReceber;
    }

    public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
        this.listaContasReceber = listaContasReceber;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public boolean isSelecionadoTodos() {
        return selecionadoTodos;
    }

    public void setSelecionadoTodos(boolean selecionadoTodos) {
        this.selecionadoTodos = selecionadoTodos;
    }

    public List<Contasreceber> getListaSelecionadas() {
        return listaSelecionadas;
    }

    public void setListaSelecionadas(List<Contasreceber> listaSelecionadas) {
        this.listaSelecionadas = listaSelecionadas;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public EmpresaDao getEmpresaDao() {
        return empresaDao;
    }

    public void setEmpresaDao(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    
    public void selecionarTodasLista(){
        if (selecionadoTodos) {
            for (int i = 0; i < listaContasReceber.size(); i++) {
                listaContasReceber.get(i).setSelecionado(true);
            }
        }else{
            for (int i = 0; i < listaContasReceber.size(); i++) {
                listaContasReceber.get(i).setSelecionado(false);
            }
        }
    }
    
    public void gerarListaContasReceber(){
        listaContasReceber = contasReceberDao.list("Select c from Contasreceber c where c.enviado=false and c.tipopagamento='Boleto'");
        if (listaContasReceber == null || listaContasReceber.isEmpty()) {
            listaContasReceber = new ArrayList<Contasreceber>();
        }
    }
    
    public String gerarBoleto() {
        List<Boleto> listaBoletos = new ArrayList<Boleto>();
        if (listaSelecionadas == null) {
            listaSelecionadas = new ArrayList<Contasreceber>();
        }
        for (int i = 0; i < listaContasReceber.size(); i++) {
            if (listaContasReceber.get(i).isSelecionado()) {
                listaSelecionadas.add(listaContasReceber.get(i));
            }
        }
        for (int i = 0; i < listaSelecionadas.size(); i++) {
            listaBoletos.add(gerarClasseBoleto(listaSelecionadas.get(i)));
        }
        if (listaBoletos.size() > 0) {
            DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
            dadosBoletoBean.gerarPDFS(listaBoletos);
        }
        return "";
    } 
    
    
    public Boleto gerarClasseBoleto(Contasreceber conta) {
        DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
        dadosBoletoBean.setAgencias(empresa.getBanco().getAgencia());
        dadosBoletoBean.setCarteiras(empresa.getBanco().getCarteira());
        dadosBoletoBean.setCnpjCedente(empresa.getCnpj());
        dadosBoletoBean.setDataDocumento(new Date());
        dadosBoletoBean.setDigitoAgencias(empresa.getBanco().getDigitoagencia());
        dadosBoletoBean.setDigitoContas(empresa.getBanco().getDigitoconta());
        dadosBoletoBean.setDataVencimento(conta.getDatalancamento());
        dadosBoletoBean.setNomeCedente(empresa.getRazaosocial());
        dadosBoletoBean.setNomeSacado(conta.getCliente().getNome());
        dadosBoletoBean.setNumeroContas(empresa.getBanco().getConta());
        dadosBoletoBean.setNumeroDocumentos(Formatacao.gerarNumeroDocumentoBoleto(conta.getNumerodocumento(), String.valueOf(conta.getNumeroparcela())));
        dadosBoletoBean.setValor(Formatacao.converterFloatBigDecimal(conta.getValorconta()));
        dadosBoletoBean.setNossoNumeros(dadosBoletoBean.getNumeroDocumentos());
        dadosBoletoBean.setEnderecoSacado(new Endereco());
        //dadosBoletoBean.getEnderecoSacado().setBairro(conta.getVendas().getCliente().getBairro());
        //dadosBoletoBean.getEnderecoSacado().setCep(conta.getVendas().getCliente().getCep());
        //dadosBoletoBean.getEnderecoSacado().setComplemento(conta.getVendas().getCliente().getComplemento());
        //dadosBoletoBean.getEnderecoSacado().setLocalidade(conta.getVendas().getCliente().getCidade());
       // dadosBoletoBean.getEnderecoSacado().setLogradouro(conta.getVendas().getCliente().getTipologradouro() + " " + conta.getVendas().getCliente().getLogradouro());
       // dadosBoletoBean.getEnderecoSacado().setNumero(conta.getVendas().getCliente().getNumero());
       // dadosBoletoBean.getEnderecoSacado().setUF(UnidadeFederativa.valueOfSigla(conta.getVendas().getCliente().getEstado()));
        dadosBoletoBean.criarBoleto();
        conta.setNossonumero(dadosBoletoBean.getNossoNumeros());
        conta.setSituacaoboleto("enviado");
        contasReceberDao.update(conta);
        return dadosBoletoBean.getBoleto();
    } 
    
     
     public String enviarBoleto(){
       List<Contasreceber> lista = new ArrayList<Contasreceber>();
       for(int i=0;i<listaSelecionadas.size();i++){
           if(listaSelecionadas.get(i).isSelecionado()){
               lista.add(listaSelecionadas.get(i));
           }
       }
       if(lista.size()==0){
           lista=listaSelecionadas;
       }
       if(lista.size()>0){
             GerarArquivoRemessaItau arquivoRemessaItau = new GerarArquivoRemessaItau(lista, usuarioLogadoMB);
             FacesMessage msg = new FacesMessage("Sucesso! ", "Arquivo Remessa Gerado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
       }else{
            FacesMessage msg = new FacesMessage("Erro! ", "Nenhuma Conta Selecionada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
       }
       return "";
    }
    
    public String calcularMultaJuros(float valor, float percentual){
        Float calculo = valor * (percentual/100);
        String scalculo = Formatacao.foramtarFloatString(calculo);
        return scalculo;
    }
}
