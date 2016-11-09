/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.exame;

import br.com.panoramico.dao.ExameAssociadoDao;
import br.com.panoramico.dao.ExameDao;
import br.com.panoramico.dao.ExameDependenteDao;
import br.com.panoramico.dao.MedicoDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Exame;
import br.com.panoramico.model.Exameassociado;
import br.com.panoramico.model.Examedependente;
import br.com.panoramico.model.Medico;
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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class ExameMB implements Serializable{
    
    private Exame exame;
    private List<Exame> listaExames;
    @EJB
    private ExameDao exameDao;
    private Exameassociado exameassociado;
    private Examedependente examedependente;
    @EJB
    private ExameAssociadoDao exameAssociadoDao;
    @EJB
    private ExameDependenteDao exameDependenteDao;
    private Date dataInicio;
    private Date dataFinal;
    private String situacao;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Medico medico;
    @EJB
    private MedicoDao medicoDao;
    private String nomeCliente;
    private String matricula;
    
    @PostConstruct
    public void init(){
        gerarListaExame();
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public List<Exame> getListaExames() {
        return listaExames;
    }

    public void setListaExames(List<Exame> listaExames) {
        this.listaExames = listaExames;
    }

    public ExameDao getExameDao() {
        return exameDao;
    }

    public void setExameDao(ExameDao exameDao) {
        this.exameDao = exameDao;
    }

    public Exameassociado getExameassociado() {
        return exameassociado;
    }

    public void setExameassociado(Exameassociado exameassociado) {
        this.exameassociado = exameassociado;
    }

    public Examedependente getExamedependente() {
        return examedependente;
    }

    public void setExamedependente(Examedependente examedependente) {
        this.examedependente = examedependente;
    }

    public ExameAssociadoDao getExameAssociadoDao() {
        return exameAssociadoDao;
    }

    public void setExameAssociadoDao(ExameAssociadoDao exameAssociadoDao) {
        this.exameAssociadoDao = exameAssociadoDao;
    }

    public ExameDependenteDao getExameDependenteDao() {
        return exameDependenteDao;
    }

    public void setExameDependenteDao(ExameDependenteDao exameDependenteDao) {
        this.exameDependenteDao = exameDependenteDao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public MedicoDao getMedicoDao() {
        return medicoDao;
    }

    public void setMedicoDao(MedicoDao medicoDao) {
        this.medicoDao = medicoDao;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    
    
    
    public void gerarListaExame(){
        listaExames = exameDao.list("Select e from Exame e");
        if (listaExames == null) {
            listaExames = new ArrayList<Exame>();
        }
    }
    
    
     public String novoCadastroExame() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 545);
        RequestContext.getCurrentInstance().openDialog("cadExame", options, null);
        return "";
    }
    
    public void retornoDialogNovo(SelectEvent event){
        Exame exame = (Exame) event.getObject();
        if (exame.getIdexame()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de exame realizado com sucesso");
        }
        gerarListaExame();
    }
    
    public void retornoDialogAlteracao(SelectEvent event){
        Exame exame = (Exame) event.getObject();
        if (exame.getIdexame()!= null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração de exame realizado com sucesso");
        }
        gerarListaExame();
    }
    
    
    public void editar(Exame exame){
        pesquisarMedico(usuarioLogadoMB.getUsuario().getIdusuario());
        if (medico == null) {
            Mensagem.lancarMensagemInfo("", "Acesso Negado!!");
        }else{
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("exame", exame);
            session.setAttribute("medico", medico);
            options.put("contentWidth", 545);
            RequestContext.getCurrentInstance().openDialog("cadExame", options, null);
        }
    }
    
    public void excluir(Exame exame){
        List<Exameassociado> listaExameAssociado = exameAssociadoDao.list("Select ea from Exameassociado ea where ea.exame.idexame=" + exame.getIdexame());
        List<Examedependente> listaExameDependente  = exameDependenteDao.list("Select ed from Examedependente ed where ed.exame.idexame=" + exame.getIdexame());
        if (listaExameAssociado == null || listaExameAssociado.isEmpty()) {
            for (int i = 0; i < listaExameDependente.size(); i++) {
                exameDependenteDao.remove(listaExameDependente.get(i).getIdexamedependente());
            }
        }else{
            for (int i = 0; i < listaExameAssociado.size(); i++) {
                exameAssociadoDao.remove(listaExameAssociado.get(i).getIdexameassociado());
            }
        }
        exameDao.remove(exame.getIdexame());
        Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
        gerarListaExame();
    }
    
    
     public String pegarValores(Exame exame){
        List<Exameassociado> listaExameAssociado = exameAssociadoDao.list("Select ea from Exameassociado ea where ea.exame.idexame=" + exame.getIdexame());
        List<Examedependente> listaExameDependente  = exameDependenteDao.list("Select ed from Examedependente ed where ed.exame.idexame=" + exame.getIdexame());
        if (listaExameAssociado == null || listaExameAssociado.isEmpty()) {
            for (int i = 0; i < listaExameDependente.size(); i++) {
                examedependente = listaExameDependente.get(i);
                return examedependente.getDependente().getNome();
            }
        }else{
            for (int i = 0; i < listaExameAssociado.size(); i++) {
                exameassociado = listaExameAssociado.get(i);
                return exameassociado.getAssociado().getCliente().getNome();
            }
        }
        return "";
    }
     
    
    public void filtrar(){
        String sql = "Select e from Exame e";
        if (!situacao.equalsIgnoreCase("sn") || dataInicio != null || dataFinal != null) {
            sql = sql + " where";
        }
        
//        if (nomeCliente.length() > 0) {
//            sql = sql + " e.cliente.nomecliente like '%" + nomeCliente + "%'";
//            if (!situacao.equalsIgnoreCase("sn") || dataInicio != null || dataFinal != null) {
//                sql = sql + " and";
//            }
//        }
        
        if (!situacao.equalsIgnoreCase("sn")) {
            sql = sql + " e.situacao='" + situacao + "'";
            if (dataInicio != null && dataFinal != null) {
                sql = sql + " and";
            }
        }
        
        if (dataInicio != null && dataFinal != null) {
            sql = sql + " e.data>='" + Formatacao.ConvercaoDataSql(dataInicio) + "' and e.data<='" 
                    + Formatacao.ConvercaoDataSql(dataFinal) + "'";
        }
        listaExames = exameDao.list(sql);
        Mensagem.lancarMensagemInfo("", "Filtrado com sucesso");
    }
     
    public void limparFiltro(){
        situacao = "";
        dataInicio = null;
        dataFinal = null;
        gerarListaExame();
    }
    
    public void pesquisarMedico(int idusuario){
        List<Medico> listaMedico = medicoDao.list("Select m From Medico m Where m.idusuario=" + idusuario
          + " and m.situacao='Ativo'");
        for (int i = 0; i < listaMedico.size(); i++) {
            medico = listaMedico.get(i);
        }
    }
}
