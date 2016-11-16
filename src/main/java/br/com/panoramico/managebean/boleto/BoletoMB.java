package br.com.panoramico.managebean.boleto;
 
import br.com.panoramico.dao.ContasReceberDao; 
import br.com.panoramico.dao.ProprietarioDao;
import br.com.panoramico.managebean.UsuarioLogadoMB; 
import br.com.panoramico.model.Contasreceber; 
import br.com.panoramico.model.Proprietario;
import br.com.panoramico.uil.Formatacao; 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List; 
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

@Named
@ViewScoped
public class BoletoMB implements Serializable {

    private List<Contasreceber> listarSelecionados;
    @EJB
    private ContasReceberDao contasReceberDao;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private String nomearquivo;
    @EJB
    private ProprietarioDao proprietarioDao;
    private Proprietario proprietario;
    private StreamedContent stream;

    public BoletoMB() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        listarSelecionados = (List<Contasreceber>) session.getAttribute("listaContas");
        nomearquivo = Formatacao.ConvercaoDataPadrao(new Date());
        nomearquivo = nomearquivo.substring(6, 10) + nomearquivo.substring(3, 5) + nomearquivo.substring(0, 2);
        nomearquivo = nomearquivo;
        proprietario = proprietarioDao.find(1);
    }

    public List<Contasreceber> getListarSelecionados() {
        return listarSelecionados;
    }

    public void setListarSelecionados(List<Contasreceber> listarSelecionados) {
        this.listarSelecionados = listarSelecionados;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public String getNomearquivo() {
        return nomearquivo;
    }

    public void setNomearquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

    public void fechardialogBoletos() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public ProprietarioDao getProprietarioDao() {
        return proprietarioDao;
    }

    public void setProprietarioDao(ProprietarioDao proprietarioDao) {
        this.proprietarioDao = proprietarioDao;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public StreamedContent getStream() {
        return stream;
    }

    public void setStream(StreamedContent stream) {
        this.stream = stream;
    }

    public String enviarBoleto() {
        List<Contasreceber> lista = new ArrayList<Contasreceber>();
        for (int i = 0; i < listarSelecionados.size(); i++) {
            if (listarSelecionados.get(i).isSelecionado()) {
                lista.add(listarSelecionados.get(i));
            }
        }
        if (lista.size() == 0) {
            lista = listarSelecionados;
        }
        if (lista.size() > 0) {
            GerarArquivoRemessaItau arquivoRemessaItau = new GerarArquivoRemessaItau(lista, usuarioLogadoMB, proprietario, stream, lista);
            confirmarContas(lista);
            FacesMessage msg = new FacesMessage("Sucesso! ", "Arquivo Remessa Gerado no caminho: "
                    + arquivoRemessaItau.getNomeArquivo());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("Erro! ", "Nenhuma Conta Selecionada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return "";
    }

    private void confirmarContas(List<Contasreceber> lista) {
        for (int i = 0; i < lista.size(); i++) {
            Contasreceber conta = lista.get(i);
            conta.setEnviado(true);
            conta.setSituacaoboleto("Enviado");
            contasReceberDao.update(conta);
            listarSelecionados.remove(conta);
        }
    }
}
