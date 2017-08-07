/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.relatorios;

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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Anderson
 */
@Named
@ViewScoped
public class RelatorioCancelamentoClienteMB implements Serializable {

    private Date dataInicio;
    private Date dataFinal;

    @PostConstruct
    public void init() {

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

    public String iniciarRelatorio() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        Map<String, Object> parameters = new HashMap<String, Object>();
        String caminhoRelatorio = "";
        String periodo = "";
        if (dataInicio != null && dataFinal != null) {
            periodo = Formatacao.ConvercaoDataPadrao(dataInicio) + " - " + Formatacao.ConvercaoDataPadrao(dataFinal);
        } else {
            periodo = "Sem periodo";
        }
        parameters.put("periodo", periodo);
        caminhoRelatorio = "reports/relatorios/cliente/motivoCancelamento.jasper";
        parameters.put("sql", gerarSQL());
        File f = new File(servletContext.getRealPath("resources/img/logo.png"));
        BufferedImage logo = null;
        try {
            logo = ImageIO.read(f);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioAssociadoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        parameters.put("logo", logo);
        GerarRelatorios gerarRelatorio = new GerarRelatorios();
        try {
            try {
                gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "associado", null);
            } catch (IOException ex) {
                Logger.getLogger(RelatorioCancelamentoClienteMB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(RelatorioCancelamentoClienteMB.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JRException ex) {
            Logger.getLogger(RelatorioCancelamentoClienteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String gerarSQL() {
        String sql = "select distinct ccancelamento.data, ccancelamento.hora, ccancelamento.motivo, usuario.nome, ccancelamento.idccancelamento, cliente.nome as cliente"
                + " from ccancelamento"
                + " join cliente on ccancelamento.cliente_idcliente = cliente.idcliente "
                + " join usuario on ccancelamento.usuario_idusuario = usuario.idusuario ";
        sql = sql + " where ccancelamento.idccancelamento>0";
        if (dataInicio != null && dataInicio != null) {
            sql = sql + " and ccancelamento.data>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'"
                    + " and ccancelamento.data<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
        }
        sql = sql + " order by cliente.nome";
        return sql;
    }

    public void fechar() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

}
