package br.com.panoramico.managebean;

import br.com.panoramico.dao.NotificacaoDao;
import br.com.panoramico.model.Notificacao;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class MenuMB implements Serializable{


    private static final long serialVersionUID = 1L;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Notificacao> listaNotificacao;
    @EJB
    private NotificacaoDao notificacaoDao;
    private boolean desabilitarNumeroMensagem;
    private int numeroTotalNotificacao;
    private String escolherCor = "";

    @PostConstruct
    public void init() {
        getUsuarioLogadoMB();
        gerarNumeroNotificacoes();
        habilitarMensagem();
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public List<Notificacao> getListaNotificacao() {
        return listaNotificacao;
    }

    public void setListaNotificacao(List<Notificacao> listaNotificacao) {
        this.listaNotificacao = listaNotificacao;
    }

    public NotificacaoDao getNotificacaoDao() {
        return notificacaoDao;
    }

    public void setNotificacaoDao(NotificacaoDao notificacaoDao) {
        this.notificacaoDao = notificacaoDao;
    }

    public boolean isDesabilitarNumeroMensagem() {
        return desabilitarNumeroMensagem;
    }

    public void setDesabilitarNumeroMensagem(boolean desabilitarNumeroMensagem) {
        this.desabilitarNumeroMensagem = desabilitarNumeroMensagem;
    }

    public int getNumeroTotalNotificacao() {
        return numeroTotalNotificacao;
    }

    public void setNumeroTotalNotificacao(int numeroTotalNotificacao) {
        this.numeroTotalNotificacao = numeroTotalNotificacao;
    }

    public String getEscolherCor() {
        return escolherCor;
    }

    public void setEscolherCor(String escolherCor) {
        this.escolherCor = escolherCor;
    }

    
    
    public void notificacoes(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 700);
        options.put("closable", false);
        FacesContext fc = FacesContext.getCurrentInstance();
        RequestContext.getCurrentInstance().openDialog("consNotificacao", options, null);
    }  
    
    public void adicionarNotificacao(){ 
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 520);
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("cadNotificacao", options, null);
    }
    
    public void retornoDialogAddNotificacao(SelectEvent event){
        Notificacao notificao = (Notificacao) event.getObject();
        if (notificao.getIdnotificacao() != null) {
            Mensagem.lancarMensagemInfo("Enviado", "com sucesso");
        }
    }
    
    public Integer gerarNumeroNotificacoes(){
        numeroTotalNotificacao = 0;
        listaNotificacao = notificacaoDao.list("Select n from Notificacao n where n.usuariorecebe.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario()
                                    + " and n.visto=0");
        numeroTotalNotificacao = listaNotificacao.size();
        return numeroTotalNotificacao;
    }
    
    public void habilitarMensagem(){
        if (numeroTotalNotificacao > 0) {
            desabilitarNumeroMensagem = true;
            escolherCor = "color:#91D8F7";
        }else{
            desabilitarNumeroMensagem = false;
            escolherCor = "";
        }
    }
    
    public void retornoDialogConsNotificacao(){
        gerarNumeroNotificacoes();
        habilitarMensagem();
    }
    
    public void gerarRelatorioClientes(){ 
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 340);
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("relatorioAniversariantes", options, null);
    }
}
