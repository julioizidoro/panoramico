package br.com.panoramico.managebean.boleto;
 
import br.com.panoramico.dao.BancoDao;
import br.com.panoramico.dao.ContasReceberDao; 
import br.com.panoramico.dao.FtpDadosDao;
import br.com.panoramico.dao.ProprietarioDao;
import br.com.panoramico.dao.RemessaArquivoDao;
import br.com.panoramico.dao.RemessaContasDao;
import br.com.panoramico.managebean.UsuarioLogadoMB; 
import br.com.panoramico.model.Banco;
import br.com.panoramico.model.Contasreceber; 
import br.com.panoramico.model.Ftpdados;
import br.com.panoramico.model.Proprietario;
import br.com.panoramico.model.Recebimento;
import br.com.panoramico.model.Remessaarquivo;
import br.com.panoramico.model.Remessacontas;
import br.com.panoramico.uil.Formatacao; 
import br.com.panoramico.uil.Ftp;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List; 
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
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
    @EJB
    private BancoDao bancoDao;
    private String nomeFtp;
    private StreamedContent file;
    @EJB
    private FtpDadosDao ftpDadosDao;
    private Ftpdados ftpdados;
    private Ftp ftp;
    private String nomeBotao = "Enviar";
    @EJB
    private RemessaArquivoDao remesssaArquivoDao;
    @EJB
    private RemessaContasDao remessaContasDao;
    
    
    public BoletoMB() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        listarSelecionados = (List<Contasreceber>) session.getAttribute("listaContas");
        nomearquivo = Formatacao.ConvercaoDataPadrao(new Date());
        nomeFtp = nomearquivo.substring(6, 10) + nomearquivo.substring(3, 5) + nomearquivo.substring(0, 2) + ".REM";
        nomearquivo = nomeFtp;
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

    public String getNomeFtp() {
        return nomeFtp;
    }

    public void setNomeFtp(String nomeFtp) {
        this.nomeFtp = nomeFtp;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public String getNomeBotao() {
        return nomeBotao;
    }

    public void setNomeBotao(String nomeBotao) {
        this.nomeBotao = nomeBotao;
    }
    
    

    public String enviarBoleto() {
        Remessaarquivo remessaarquivo;
        Remessacontas remessacontas;
        List<Recebimento> listaRecebimento;
        if (nomeBotao.equalsIgnoreCase("Enviar")) {
            proprietario = proprietarioDao.find(1);
            Banco banco = bancoDao.find("Select b From  Banco b Where b.proprietario.idproprietario=" + proprietario.getIdproprietario() + " and b.emitiboleto=1");
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
                ftpdados = ftpDadosDao.find(1);
                GerarArquivoRemessaItau arquivoRemessaItau = new GerarArquivoRemessaItau(lista, usuarioLogadoMB, proprietario, lista, banco, nomearquivo, nomeFtp, ftpdados);
                confirmarContas(lista);
                InputStream stream = procurarArquivo();
                file = new DefaultStreamedContent(stream, "", nomeFtp);
                FacesMessage msg = new FacesMessage("Enviado! ", "Disponivel para download, aperte novamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                nomeBotao = "Download";
            } else {
                FacesMessage msg = new FacesMessage("Erro! ", "Nenhuma Conta Selecionada");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            for (int i = 0; i < lista.size(); i++) {
                remessaarquivo = new Remessaarquivo();
                remessacontas = new Remessacontas();
                remessaarquivo.setDataenvio(new Date());
                remessaarquivo.setUsuario(usuarioLogadoMB.getUsuario());
                remessaarquivo.setNomearquivo(nomearquivo);
                remessaarquivo = remesssaArquivoDao.update(remessaarquivo);
                remessacontas.setContasreceber(lista.get(i));
                remessacontas.setCodigoocorrencia("01");
                remessacontas.setRemessaarquivo(remessaarquivo);
                remessaContasDao.update(remessacontas);
            }
        }else if(nomeBotao.equalsIgnoreCase("Download")){
             InputStream stream = procurarArquivo();
             file = new DefaultStreamedContent(stream, "", nomeFtp);
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
    
    
    public InputStream procurarArquivo() {
        InputStream is = null;
        ftpdados = ftpDadosDao.find(1);
        ftp = new Ftp(ftpdados.getHost(), ftpdados.getUser(), ftpdados.getPassword());
        try {
            ftp.conectar();
            is = ftp.receberArquivo("", nomearquivo, "/panoramico/remessa/");
            ftp.desconectar();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return is;
    }
}
