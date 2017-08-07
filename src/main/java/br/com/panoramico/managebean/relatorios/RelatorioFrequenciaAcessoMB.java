/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.relatorios;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.ControleAcessoDao;
import br.com.panoramico.managebean.acesso.*;
import br.com.panoramico.managebean.contasreceber.ImprimieContasRecebidasMB;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Controleacesso;
import br.com.panoramico.model.Dependente;
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
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class RelatorioFrequenciaAcessoMB implements Serializable {

    private Integer totalNAcesso;
    private Date dataInicio;
    private Date dataFinal;
    private Associado associado;
    @EJB
    private AssociadoDao associadoDao;
    private List<Associado> listaAssociado;
    @EJB
    private ControleAcessoDao controleAcessoDao;

    @PostConstruct
    public void init() {
        gerarListaAssociado();
    }

    public Integer getTotalNAcesso() {
        return totalNAcesso;
    }

    public void setTotalNAcesso(Integer totalNAcesso) {
        this.totalNAcesso = totalNAcesso;
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

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public List<Associado> getListaAssociado() {
        return listaAssociado;
    }

    public void setListaAssociado(List<Associado> listaAssociado) {
        this.listaAssociado = listaAssociado;
    }

    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "reports/relatorios/acesso/reportFrequenciaAcesso.jasper";
        Map<String, Object> parameters = new HashMap<String, Object>();
        File f = new File(servletContext.getRealPath("resources/img/logo.png"));
        BufferedImage logo = ImageIO.read(f);
        parameters.put("logo", logo);
        String periodo = null;
        if (dataInicio != null && dataFinal != null) {
            periodo = "Periodo : " + Formatacao.ConvercaoDataPadrao(dataInicio)
                    + "    " + Formatacao.ConvercaoDataPadrao(dataFinal);
        }
        parameters.put("periodo", periodo);
        parameters.put("sql", gerarSql());
        GerarRelatorios gerarRelatorio = new GerarRelatorios();
        try {
            try {
                gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "frequenciaAcesso", null);
            } catch (IOException ex) {
                Logger.getLogger(RelatorioDependentesMB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(RelatorioDependentesMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JRException ex) {
            Logger.getLogger(RelatorioFrequenciaAcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String gerarSql() {
        String sql = "select distinct controleacesso.data, controleacesso.hora, controleacesso.tipo, cliente.nome from controleacesso"
                + " join associado on controleacesso.associado_idassociado = associado.idassociado"
                + " join cliente on associado.cliente_idcliente = cliente.idcliente"
                + " where associado.situacao='Ativo' and controleacesso.situacao='LIBERADO'";
        if (associado != null && associado.getIdassociado() != null) {
            sql = sql + " and associado.idassociado=" + associado.getIdassociado();
        }
        if (dataInicio != null && dataFinal != null) {
            sql = sql + " and controleacesso.data>='" + Formatacao.ConvercaoDataSql(dataInicio) + "' and controleacesso.data<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
        }
        sql = sql + " order by cliente.nome";
        return sql;
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void gerarListaAssociado() {
        listaAssociado = associadoDao.list("select a from Associado a where a.situacao='Ativo'");
        if (listaAssociado == null) {
            listaAssociado = new ArrayList<Associado>();
        }
    }
}
