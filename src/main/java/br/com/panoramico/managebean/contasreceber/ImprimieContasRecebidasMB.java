/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contasreceber;

import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.GerarRelatorios;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
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
public class ImprimieContasRecebidasMB implements Serializable {

    private Contasreceber contasreceber;
    @EJB
    private ContasReceberDao contasReceberDao;
    private Date dataInicio;
    private Date dataFinal;
    private String tipoRelatorio;

    @PostConstruct
    public void init() {

    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
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

    public String getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(String tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "";
        Map<String, Object> parameters = new HashMap<String, Object>();
        if (tipoRelatorio.equalsIgnoreCase("contasrecebidas")) {
            caminhoRelatorio = "reports/relatorios/contasReceber/reportContasReceber.jasper";
        } else if(tipoRelatorio.equalsIgnoreCase("inadimplentes")) {
            caminhoRelatorio = "reports/relatorios/contasReceber/reportInadimplentes.jasper";
        } else{
            caminhoRelatorio = "reports/relatorios/contasReceber/reportHistoricoCobranca.jasper";
        }
        parameters.put("sql", gerarSql());
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
            Logger.getLogger(ImprimieContasRecebidasMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String gerarSql() {
        String sql = "";
        if (tipoRelatorio.equalsIgnoreCase("contasrecebidas")) {
            sql = "Select distinct contasreceber.datalancamento, contasreceber.tipopagamento, ";
            sql = sql + "contasreceber.idcontasreceber, contasreceber.valorconta, contasreceber.numeroparcela, "
                    + "cliente.nome, recebimento.juros, recebimento.desagio, recebimento.valorrecebido, recebimento.datarecebimento, "
                    + "contasreceber.numerodocumento From recebimento Join contasreceber on recebimento.contasreceber_idcontasreceber"
                    + "= contasreceber.idcontasreceber Join cliente on contasreceber.cliente_idcliente= cliente.idcliente";

            if ((dataInicio != null) && (dataFinal != null)) {
                sql = sql + " Where recebimento.datarecebimento>='" + Formatacao.ConvercaoDataSql(dataInicio)
                        + "' and recebimento.datarecebimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
            }
        } else if (tipoRelatorio.equalsIgnoreCase("inadimplentes")) {
            sql = "Select distinct contasreceber.datalancamento, contasreceber.numeroparcela, contasreceber.valorconta, contasreceber.idcontasreceber, cliente.nome"
                    + " from contasreceber Join cliente on contasreceber.cliente_idcliente=cliente.idcliente Where  contasreceber.situacao='PAGAR' ";

            if ((dataInicio != null) && (dataFinal != null)) {
                if (dataInicio.before(new Date())) {
                    sql = sql + " and contasreceber.datalancamento>='" + Formatacao.ConvercaoDataSql(dataInicio) + "' ";
                    if (dataFinal.before(new Date())) {
                       sql = sql + " and contasreceber.datalancamento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
                    }else{
                        sql = sql + " and contasreceber.datalancamento<'" + Formatacao.ConvercaoDataSql(new Date()) + "' ";
                    }
                }
            }

            sql = sql + " Group by cliente.nome, contasreceber.idcontasreceber, contasreceber.valorconta, contasreceber.datalancamento"
                    + " ,contasreceber.numeroparcela";
        }else if(tipoRelatorio.equalsIgnoreCase("historicocob")){
            sql = "";
        }
        return sql;
    } 

}
