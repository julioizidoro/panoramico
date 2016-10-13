/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.relatorios;

import br.com.panoramico.managebean.contasreceber.ImprimieContasRecebidasMB;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.GerarRelatorios;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Wolverine
 */
public class RelatorioDependentesMB {
    
    public String iniciarRelatorioMaior21(){
        Calendar c = Calendar.getInstance();
	c.setTime(new Date());
	c.add(Calendar.YEAR,-21);
	Date d = c.getTime();
        String dataSql = Formatacao.ConvercaoDataSql(d);
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "reports/relatorios/dependentes/relatoriomaior21.jasper";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("datasql", dataSql);
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
    
}
