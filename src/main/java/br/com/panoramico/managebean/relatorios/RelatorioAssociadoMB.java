package br.com.panoramico.managebean.relatorios;

import br.com.panoramico.managebean.contasreceber.ImprimieContasRecebidasMB;
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
public class RelatorioAssociadoMB implements Serializable {
 
    private Date dataInicio;
    private Date dataFinal;
    
    
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
        String caminhoRelatorio = "reports/relatorios/associado/relatorioassociado.jasper";
        Map<String, Object> parameters = new HashMap<String, Object>();
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
                Logger.getLogger(RelatorioAssociadoMB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(RelatorioAssociadoMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JRException ex) {
            Logger.getLogger(ImprimieContasRecebidasMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
 
    public String gerarSQL() {
        String sql = "SELECT distinct associado.dataassociacao, associado.matricula, cliente.nome, cliente.telefone"
                + " from associado"
                + " join cliente on associado.cliente_idcliente = cliente.idcliente";
        sql = sql + " where associado.situacao='Ativo'";
        if (dataInicio != null && dataInicio != null) {
            sql = sql + " and associado.dataassociacao>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'"
                    + " and associado.dataassociacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'"; 
        }
        sql = sql + " order by cliente.nome";
        return sql;
    }

    public void fechar() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }
}
