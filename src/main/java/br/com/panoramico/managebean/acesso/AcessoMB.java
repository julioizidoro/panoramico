package br.com.panoramico.managebean.acesso;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.ControleAcessoDao;
import br.com.panoramico.dao.DependenteDao;
import br.com.panoramico.dao.ExameAssociadoDao;
import br.com.panoramico.dao.ExameDao;
import br.com.panoramico.dao.ExameDependenteDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Controleacesso;
import br.com.panoramico.model.Dependente;
import br.com.panoramico.model.Exame;
import br.com.panoramico.model.Exameassociado;
import br.com.panoramico.model.Examedependente;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

@Named
@ViewScoped
public class AcessoMB implements Serializable{
    
    @EJB
    private ExameDao exameDao;
    private Exame exame;
    @EJB
    private ContasReceberDao contasReceberDao;
    private Contasreceber contasreceber;
    private int codigoAssociado;
    private int codigoDependente;
    private String nome;
    private String descricaoNegado;
    private boolean desabilitarLiberado = true;
    private boolean desabilitarNegado = true;
    @EJB
    private AssociadoDao associadoDao;
    private Associado associado;
    @EJB
    private DependenteDao dependenteDao;
    private Dependente dependente;
    @EJB
    private ExameAssociadoDao exameAssociadoDao;
    private Exameassociado exameassociado;
    @EJB
    private ExameDependenteDao exameDependenteDao;
    private Examedependente examedependente;
    private Date dataExame;
    private String corDataExame = "color:black;";
    private int codigoPassaporte;
    private boolean habilitarResultado = false;
    private String tipoClasse = "";
    private String nomeStatus = "";
    @EJB
    private ControleAcessoDao controleAcessoDao;
    private Controleacesso controleacesso;
    
    
    @PostConstruct
    public void init(){
        
    }

    public ExameDao getExameDao() {
        return exameDao;
    }

    public void setExameDao(ExameDao exameDao) {
        this.exameDao = exameDao;
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
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

    public int getCodigoAssociado() {
        return codigoAssociado;
    }

    public void setCodigoAssociado(int codigoAssociado) {
        this.codigoAssociado = codigoAssociado;
    }

    public int getCodigoDependente() {
        return codigoDependente;
    }

    public void setCodigoDependente(int codigoDependente) {
        this.codigoDependente = codigoDependente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricaoNegado() {
        return descricaoNegado;
    }

    public void setDescricaoNegado(String descricaoNegado) {
        this.descricaoNegado = descricaoNegado;
    }

    public boolean isDesabilitarLiberado() {
        return desabilitarLiberado;
    }

    public void setDesabilitarLiberado(boolean desabilitarLiberado) {
        this.desabilitarLiberado = desabilitarLiberado;
    }

    public boolean isDesabilitarNegado() {
        return desabilitarNegado;
    }

    public void setDesabilitarNegado(boolean desabilitarNegado) {
        this.desabilitarNegado = desabilitarNegado;
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

    public DependenteDao getDependenteDao() {
        return dependenteDao;
    }

    public void setDependenteDao(DependenteDao dependenteDao) {
        this.dependenteDao = dependenteDao;
    }

    public Dependente getDependente() {
        return dependente;
    }

    public void setDependente(Dependente dependente) {
        this.dependente = dependente;
    }

    public ExameAssociadoDao getExameAssociadoDao() {
        return exameAssociadoDao;
    }

    public void setExameAssociadoDao(ExameAssociadoDao exameAssociadoDao) {
        this.exameAssociadoDao = exameAssociadoDao;
    }

    public Exameassociado getExameassociado() {
        return exameassociado;
    }

    public void setExameassociado(Exameassociado exameassociado) {
        this.exameassociado = exameassociado;
    }

    public ExameDependenteDao getExameDependenteDao() {
        return exameDependenteDao;
    }

    public void setExameDependenteDao(ExameDependenteDao exameDependenteDao) {
        this.exameDependenteDao = exameDependenteDao;
    }

    public Examedependente getExamedependente() {
        return examedependente;
    }

    public void setExamedependente(Examedependente examedependente) {
        this.examedependente = examedependente;
    }

    public Date getDataExame() {
        return dataExame;
    }

    public void setDataExame(Date dataExame) {
        this.dataExame = dataExame;
    }

    public String getCorDataExame() {
        return corDataExame;
    }

    public void setCorDataExame(String corDataExame) {
        this.corDataExame = corDataExame;
    }

    public int getCodigoPassaporte() {
        return codigoPassaporte;
    }

    public void setCodigoPassaporte(int codigoPassaporte) {
        this.codigoPassaporte = codigoPassaporte;
    }

    public boolean isHabilitarResultado() {
        return habilitarResultado;
    }

    public void setHabilitarResultado(boolean habilitarResultado) {
        this.habilitarResultado = habilitarResultado;
    }

    public String getTipoClasse() {
        return tipoClasse;
    }

    public void setTipoClasse(String tipoClasse) {
        this.tipoClasse = tipoClasse;
    }

    public String getNomeStatus() {
        return nomeStatus;
    }

    public void setNomeStatus(String nomeStatus) {
        this.nomeStatus = nomeStatus;
    }

    public ControleAcessoDao getControleAcessoDao() {
        return controleAcessoDao;
    }

    public void setControleAcessoDao(ControleAcessoDao controleAcessoDao) {
        this.controleAcessoDao = controleAcessoDao;
    }
    
    
    
    public void pesquisar(){
        if (codigoAssociado > 0) {
            associado = associadoDao.find(codigoAssociado);
            nome = associado.getCliente().getNome();
            exameassociado = exameAssociadoDao.find("Select ea From Exameassociado ea Where associado.idassociado=" + associado.getIdassociado());
            dataExame = exameassociado.getExame().getDatavalidade();
            if ((dataExame.compareTo(new Date()) == 1) || 
                    (dataExame.compareTo(new Date()) == 0)) {
                tipoClasse = "cadastrar";
                nomeStatus = "LIBERADO";
                corDataExame = "color:black;";
                descricaoNegado = "";
            }else{
                tipoClasse = "cancelar";
                nomeStatus = "NEGADO";
                 Mensagem.lancarMensagemInfo("Validade do exame expirada", "");
                corDataExame = "color:#FB4C4C;";
            }
        }else if(codigoDependente > 0){
            dependente = dependenteDao.find(codigoDependente);
            nome = dependente.getNome();
            examedependente = exameDependenteDao.find("Select ed From Examedependente ed Where dependente.iddependente=" + dependente.getIddependente());
            dataExame = examedependente.getExame().getDatavalidade();
            if ((dataExame.compareTo(new Date()) == 1) ||
                    (dataExame.compareTo(new Date()) == 0)) {
                tipoClasse = "cadastrar";
                nomeStatus = "LIBERADO";
                corDataExame = "color:black;";
                descricaoNegado = "";
            }else{ 
                tipoClasse = "cancelar";
                nomeStatus = "NEGADO";
                Mensagem.lancarMensagemInfo("Validade do exame expirada", "");
                corDataExame = "color:#FB4C4C;";
            }
        }
        habilitarResultado = true;
    }
    
    
    public void controleAcesso(){
        controleacesso = new Controleacesso();
        controleacesso.setSituacao(nomeStatus);
        controleacesso.setData(new Date());
        controleacesso.setHora(retornarHoraAtual());
        if (codigoAssociado > 0) {
            controleacesso.setIddependente(0);
            controleacesso.setAssociado(associado);
            controleacesso.setTipo("A");
        }else if(codigoDependente > 0){
            controleacesso.setIddependente(dependente.getIddependente());
            controleacesso.setAssociado(dependente.getAssociado());
            controleacesso.setTipo("D");
        }
        controleacesso = controleAcessoDao.update(controleacesso);
        Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
    }
    
    
    public String retornarHoraAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date hora = Calendar.getInstance().getTime();
        return (sdf.format(hora));
    }
    
}
