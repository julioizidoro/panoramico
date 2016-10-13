/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contaspagar;

import br.com.panoramico.dao.ContasPagarDao;
import br.com.panoramico.dao.PlanoContaDao;
import br.com.panoramico.model.Contaspagar;
import br.com.panoramico.model.Planoconta;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.GerarRelatorios;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;
 
@Named
@ViewScoped
public class ImprimirContasPagarMB implements Serializable {

    private Contaspagar contaspagar;
    @EJB
    private ContasPagarDao contasPagarDao;
    private Planoconta planocontas;
    @EJB
    private PlanoContaDao planoContasDao;
    private Date dataInicio;
    private Date dataFinal;
    private List<Planoconta> listaPlanoConta;

    @PostConstruct
    public void init() {
        gerarListaPlanoConta();
    }

    public Contaspagar getContaspagar() {
        return contaspagar;
    }

    public void setContaspagar(Contaspagar contaspagar) {
        this.contaspagar = contaspagar;
    }
 
    public ContasPagarDao getContasPagarDao() {
        return contasPagarDao;
    }

    public void setContasPagarDao(ContasPagarDao contasPagarDao) {
        this.contasPagarDao = contasPagarDao;
    }

    public Planoconta getPlanocontas() {
        return planocontas;
    }

    public void setPlanocontas(Planoconta planocontas) {
        this.planocontas = planocontas;
    }

    public PlanoContaDao getPlanoContasDao() {
        return planoContasDao;
    }

    public void setPlanoContasDao(PlanoContaDao planoContasDao) {
        this.planoContasDao = planoContasDao;
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

    public List<Planoconta> getListaPlanoConta() {
        return listaPlanoConta;
    }

    public void setListaPlanoConta(List<Planoconta> listaPlanoConta) {
        this.listaPlanoConta = listaPlanoConta;
    }

    public void gerarListaPlanoConta() {
        listaPlanoConta = planoContasDao.list("Select p From Planoconta p");
        if (listaPlanoConta == null || listaPlanoConta.isEmpty()) {
            listaPlanoConta = new ArrayList<Planoconta>();
        }
    } 

    public String gerarRelatorio() throws SQLException, IOException {
        String nomePlanoConta = "";
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "";
        Map<String, Object> parameters = new HashMap<String, Object>();
        caminhoRelatorio = "reports/relatorios/contasPagar/reportContasPagas.jasper"; 
        parameters.put("sql", gerarSql());
        if (planocontas == null || planocontas.getIdplanoconta() == null) {
            nomePlanoConta = "Todos";
        }else{
            nomePlanoConta = planocontas.getDescricao();
        }
        parameters.put("planocontas", nomePlanoConta);
        File f = new File(servletContext.getRealPath("resources/img/logo.png"));
        BufferedImage logo = ImageIO.read(f);
        parameters.put("logo", logo);
        String periodo = null;
        if (dataInicio != null && dataFinal != null) {
            periodo = "Periodo : " + Formatacao.ConvercaoDataPadrao(dataInicio)
                    + "    " + Formatacao.ConvercaoDataPadrao(dataFinal);
        }
        parameters.put("periodo", periodo);
        GerarRelatorios gerarRelatorio = new GerarRelatorios();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "fluxocaixa", null);
        } catch (JRException ex) {
            Logger.getLogger(ImprimirContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }  

    public String gerarSql() {
        String sql = "";
        sql = "Select distinct contaspagar.datavencimento, contaspagar.credor, ";
        sql = sql + "contaspagar.idcontaspagar, contaspagar.valor, contaspagar.numeroparcela from contaspagar where ";
        
        if ((dataInicio != null) && (dataFinal != null)) {
            sql = sql + "contaspagar.datavencimento>='" + Formatacao.ConvercaoDataSql(dataInicio)
                    + "' and contaspagar.datavencimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
            if (planocontas != null && planocontas.getIdplanoconta() != null) {
                sql = sql + " and ";
            }
        }  
        if (planocontas != null && planocontas.getIdplanoconta() != null) {
            sql = sql + " contaspagar.planoconta_idplanoconta=" + planocontas.getIdplanoconta();
        } 

        return sql;
    }  
    
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
    } 

}
