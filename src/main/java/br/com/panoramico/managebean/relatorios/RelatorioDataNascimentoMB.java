/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.relatorios;

import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.managebean.evento.ImprimirEventoMB;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Notificacao;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.GerarRelatorios;
import br.com.panoramico.uil.Mensagem;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class RelatorioDataNascimentoMB implements Serializable {

    @EJB
    private ClienteDao clienteDao;
    private String mes;
    private String tipo;
    private RelatorioDataNascimentoFactory relatorioData;
    private String[] listaMesSelecionado;

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String[] getListaMesSelecionado() {
        return listaMesSelecionado;
    }

    public void setListaMesSelecionado(String[] listaMesSelecionado) {
        this.listaMesSelecionado = listaMesSelecionado;
    }

    public String iniciarConsulta() throws IOException, SQLException {
        relatorioData = new RelatorioDataNascimentoFactory();
        relatorioData.setLista(new ArrayList<RelatorioDataNascimnetoBean>());
        String sqlCliente = ""; 
        List<Cliente> listaCliente = new ArrayList<Cliente>();
        for (int m = 0; m < listaMesSelecionado.length; m++) {
            sqlCliente = "SELECT c FROM Cliente c where MONTH(c.datanascimento)=" + listaMesSelecionado[m] + " order by c.datanascimento, c.nome";
            List<Cliente> lista = clienteDao.list(sqlCliente);
            if (lista != null) {
                for (int c = 0; c < lista.size(); c++) {
                    listaCliente.add(lista.get(c));
                }
            }
        } 
        if (listaCliente != null) {
            for (int i = 0; i < listaCliente.size(); i++) {
                RelatorioDataNascimnetoBean relatorio = new RelatorioDataNascimnetoBean();
                relatorio.setDatanascimento(Formatacao.ConvercaoDataPadrao(listaCliente.get(i).getDatanascimento()));
                relatorio.setEmail(listaCliente.get(i).getEmail());
                relatorio.setNome(listaCliente.get(i).getNome());
                relatorio.setTelefone(listaCliente.get(i).getTelefone());
                if (listaCliente.get(i).getAssociado() == null) {
                    relatorio.setTipo("Passaporte");
                } else {
                    relatorio.setTipo("Associado");
                }
                int numeroMes = Formatacao.getMesData(listaCliente.get(i).getDatanascimento());
                relatorio.setNumeromes(numeroMes+1);
                relatorio.setMes(Formatacao.nomeMes(relatorio.getNumeromes()));
                if (tipo.equalsIgnoreCase("Todos")) {
                    relatorioData.getLista().add(relatorio);
                } else if (tipo.equalsIgnoreCase(relatorio.getTipo())) {
                    relatorioData.getLista().add(relatorio);
                }
            }
        }
        if (relatorioData.getLista() != null && relatorioData.getLista().size() > 0) {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
                    .getContext();
            String caminhoRelatorio;
            caminhoRelatorio = "/reports/relatorios/aniversariantes/relatorioaniversariantes.jasper";
            Map<String, Object> parameters = new HashMap<String, Object>();
            File f = new File(servletContext.getRealPath("resources/img/logo.png"));
            BufferedImage logo = ImageIO.read(f);
            parameters.put("logo", logo);
            JRDataSource jrds = new JRBeanCollectionDataSource(relatorioData.getLista());
            GerarRelatorios gerarRelatorio = new GerarRelatorios();
            try {
                gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "Relatório de Aniversariantes.pdf");
            } catch (JRException ex) {
                Logger.getLogger(RelatorioDataNascimentoMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Mensagem.lancarMensagemInfo("", "Nenhuma informação localizada.");
        }
        return "";
    }

    public void fechar() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

}
