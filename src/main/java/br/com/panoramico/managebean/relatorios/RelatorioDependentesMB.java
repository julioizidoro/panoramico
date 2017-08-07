package br.com.panoramico.managebean.relatorios;

import br.com.panoramico.managebean.contasreceber.ImprimieContasRecebidasMB;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.GerarRelatorios;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class RelatorioDependentesMB implements Serializable {

    private String grauparentesco;
    private boolean maior21;

    public String getGrauparentesco() {
        return grauparentesco;
    }

    public void setGrauparentesco(String grauparentesco) {
        this.grauparentesco = grauparentesco;
    }

    public boolean isMaior21() {
        return maior21;
    }

    public void setMaior21(boolean maior21) {
        this.maior21 = maior21;
    }

    public String iniciarRelatorioMaior21() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "reports/relatorios/dependentes/relatoriomaior21.jasper";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("sql", gerarSQL());
        File f = new File(servletContext.getRealPath("resources/img/logo.png"));
        BufferedImage logo = null;
        try {
            logo = ImageIO.read(f);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioDependentesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        parameters.put("logo", logo);
        GerarRelatorios gerarRelatorio = new GerarRelatorios();
        try {
            try {
                gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "dependentemaior21anos", null);
            } catch (IOException ex) {
                Logger.getLogger(RelatorioDependentesMB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(RelatorioDependentesMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JRException ex) {
            Logger.getLogger(ImprimieContasRecebidasMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String gerarSQL() {
        String sql = "select distinct dependente.nome as nomedependente, dependente.datanascimento, dependente.matricula, cliente.nome as nomecliente"
                + " from dependente"
                + " join associado on dependente.associado_idassociado = associado.idassociado"
                + " join cliente on associado.cliente_idcliente = cliente.idcliente";
        if (maior21 || !grauparentesco.equalsIgnoreCase("Todos")) {
            sql = sql + " where";
            if (maior21) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.YEAR, -21);
                Date d = c.getTime();
                String dataSql = Formatacao.ConvercaoDataSql(d);
                sql = sql + " dependente.datanascimento<='" + dataSql + "'";
                if (!grauparentesco.equalsIgnoreCase("Todos")) {
                    sql = sql + " and ";
                }
            }
            if (!grauparentesco.equalsIgnoreCase("Todos")) {
                sql = sql + " dependente.grauparentesco='" + grauparentesco + "'";
            }
        }
        sql = sql + " order by cliente.nome, dependente.nome";
        return sql;
    }

    public void fechar() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }
}
