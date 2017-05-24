/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.uil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager; 
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.context.RequestContext;

public class GerarRelatorios {

    @PersistenceContext
    EntityManager em;

    public void gerarRelatorioDSPDF(String caminhoRelatorio, Map parameters, JRDataSource jrds, String nomeArquivo) throws JRException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();  
        ServletContext servletContext = (ServletContext)facesContext.getExternalContext().getContext();
        InputStream reportStream = facesContext.getExternalContext()  
                .getResourceAsStream(caminhoRelatorio);  
        JasperPrint arquivoPrint=null;
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.reset();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=\"" + nomeArquivo +"\"");
        ServletOutputStream servletOutputStream = response.getOutputStream();  
        RequestContext.getCurrentInstance().closeDialog(null);
      
        // envia para o navegador o PDF gerado  
        JasperRunManager.runReportToPdfStream(reportStream,  
                servletOutputStream, parameters,jrds);  
          
        servletOutputStream.flush();  
        servletOutputStream.close();  
  
        facesContext.responseComplete(); 
    }

    public void gerarRelatorioSqlPDF(String caminhoRelatorio, Map<String, Object> parameters, String nomeArquivo, String subDir) throws JRException, IOException, SQLException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        InputStream reportStream = facesContext.getExternalContext().getResourceAsStream(caminhoRelatorio);
        if (subDir != null) {
            subDir = servletContext.getRealPath(subDir);
            subDir = subDir + File.separator + "a";
            subDir = subDir.substring(0, (subDir.length() - 1));
            System.out.println(subDir);
            parameters.put("SUBREPORT_DIR", subDir);
        } 
        JasperPrint arquivoPrint = null;
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.reset();
        response.setContentType("application/pdf");
        facesContext.responseComplete();    
        ServletOutputStream servletOutputStream = response.getOutputStream();
        RequestContext.getCurrentInstance().closeDialog(null);
        Connection conn = null;
        
            conn = getConexaoo();
        // envia para o navegador o PDF gerado  
        JasperRunManager.runReportToPdfStream(reportStream,
                servletOutputStream, parameters, conn);
  
        servletOutputStream.flush();
        servletOutputStream.close();
  
        facesContext.responseComplete();
    } 
    
    
    
    
      
    public static Connection getConexao() throws NamingException{
        InitialContext context = null;
        try {
            context = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(GerarRelatorios.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataSource ds = (DataSource) context.lookup("java:/panoramicoDS");
        try {
            return ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public static Connection getConexaoo() {
		Connection conexao = null;
		try { 
			conexao = DriverManager.getConnection("jdbc:mysql://localhost:8081/panoramico", "root", "simples");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return conexao;
	}
}
