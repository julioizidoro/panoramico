package br.com.panoramico.managebean;

import br.com.panoramico.uil.GerarRelatorios;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;

public class RelatorioErroBean {

    public void iniciarRelatorioErro(String erro) {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
                .getContext();
        String caminhoRelatorio = "/relatorios/erro/reportErro.jasper";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("erro", erro);
        GerarRelatorios gerarRelatorio = new GerarRelatorios();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "ERRO", null);
        } catch (JRException | IOException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
