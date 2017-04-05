package br.com.panoramico.managebean.relatorios;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.managebean.contasreceber.ImprimieContasRecebidasMB;
import br.com.panoramico.model.Associado;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.GerarRelatorios;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException; 
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
    @EJB
    private AssociadoDao associadoDao;
    
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
        if (buscarListaAssociado()) {
            caminhoRelatorio = "reports/relatorios/associado/relatorioassociado.jasper";
            parameters.put("sql", gerarSQL());
        }else{
            caminhoRelatorio = "reports/relatorios/associado/relatoriosemassociado.jasper";
            parameters.put("dataInicio", dataInicio);
            parameters.put("dataFinal", dataFinal);
        }   
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
        String sqlData = "";
        String sql = "SELECT distinct associado.dataassociacao, associado.matricula, cliente.nome, cliente.telefone"
                + " from associado"
                + " join cliente on associado.cliente_idcliente = cliente.idcliente";
        sql = sql + " where associado.situacao='Ativo'";
        if (dataInicio != null && dataInicio != null) {
            sql = sql + " and associado.dataassociacao>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'"
                    + " and associado.dataassociacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'"; 
            sqlData = sqlData + " and a.dataassociacao>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'"
                    + " and a.dataassociacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'"; 
        }
        sql = sql + " order by cliente.nome";
        return sql;
    }  

    public void fechar() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }
    
    public boolean buscarListaAssociado(){
        String sqlData = "";
        if (dataInicio != null && dataInicio != null) {
            sqlData = sqlData + " and a.dataassociacao>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'"
                    + " and a.dataassociacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'"; 
        }
        List<Associado> lista = associadoDao.list("select a from Associado a Where a.situacao='Ativo' " + sqlData);
        if (lista != null) {
            if (lista.size() > 0) {
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}
