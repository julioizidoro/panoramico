/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.acesso;

import br.com.panoramico.dao.ControleAcessoDao;
import br.com.panoramico.dao.DependenteDao;
import br.com.panoramico.managebean.contaspagar.ImprimirContasPagarMB;
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
public class ImprimirAcessoMB implements Serializable {

    private Controleacesso controleacesso;
    @EJB
    private ControleAcessoDao controleAcessoDao;
    private String tipoAcesso;
    private Integer totalNAcesso;
    private Date dataInicio;
    private Date dataFinal;
    private String tipoRelatorio;
    private List<InformacoesFrequenciaBean> listaFrequenciaBean;
    @EJB
    private DependenteDao dependenteDao;

    @PostConstruct
    public void init() {

    }

    public Controleacesso getControleacesso() {
        return controleacesso;
    }

    public void setControleacesso(Controleacesso controleacesso) {
        this.controleacesso = controleacesso;
    }

    public ControleAcessoDao getControleAcessoDao() {
        return controleAcessoDao;
    }

    public void setControleAcessoDao(ControleAcessoDao controleAcessoDao) {
        this.controleAcessoDao = controleAcessoDao;
    }

    public String getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(String tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
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

    public String getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(String tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "";
        String nomeRelatorio = "";
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
        GerarRelatorios gerarRelatorio = new GerarRelatorios();
        if (tipoRelatorio.equalsIgnoreCase("frequencia")) {
            caminhoRelatorio = "reports/relatorios/acesso/reportFrequenciaAcesso.jasper";
            nomeRelatorio = "FrenquênciaAcessoParque";
            List<InformacoesFrequenciaBean> lista = gerarListaFrequencia();
            JRDataSource jrds = new JRBeanCollectionDataSource(lista);
            try {
                gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, nomeRelatorio);
            } catch (JRException ex) {
                Logger.getLogger(ImprimirAcessoMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {    
            caminhoRelatorio = "reports/relatorios/acesso/reportNumeroAcesso.jasper";
            parameters.put("sql", gerarSql());
            gerarTotalAcesso();
            parameters.put("total", totalNAcesso);
            try {
                gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "fluxocaixa", null);
            } catch (JRException ex) {
                Logger.getLogger(ImprimirAcessoMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "";
    }

    public String gerarSql() {
        String sql = "";
        sql = "Select distinct controleacesso.data, controleacesso.hora, controleacesso.tipo, controleacesso.idcontroleacesso From controleacesso Where "
                + " controleacesso.situacao='LIBERADO' ";

        if ((dataInicio != null) && (dataFinal != null)) {
            sql = sql + " and controleacesso.data>='" + Formatacao.ConvercaoDataSql(dataInicio)
                    + "' and controleacesso.data<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";

        }

        if (tipoAcesso == null) {
            tipoAcesso = "";
        } else if (!tipoAcesso.equalsIgnoreCase("")) {
            sql = sql + " and controleacesso.tipo='" + tipoAcesso + "' ";
        }

        return sql;
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void gerarTotalAcesso() {
        String sql = "Select c From Controleacesso c Where c.situacao='LIBERADO'";
        if (dataInicio != null && dataFinal != null) {
            sql = sql + " and c.data>='" + Formatacao.ConvercaoDataSql(dataInicio)
                    + "' and c.data<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
        }
        if (!tipoAcesso.equalsIgnoreCase("")) {
            sql = sql + " and c.tipo='" + tipoAcesso + "' ";
        }
        List<Controleacesso> lista = controleAcessoDao.list(sql);
        totalNAcesso = 0;
        for (int i = 0; i < lista.size(); i++) {
            totalNAcesso = totalNAcesso + 1;
        }
    }

    public List<InformacoesFrequenciaBean> gerarListaFrequencia() {
        List<InformacoesFrequenciaBean> lista = new ArrayList<>();
        List<Associado> listaAssociados = new ArrayList<>();
        List<Controleacesso> listaControleAcesso = new ArrayList<>();
        String sql = " Select c From Controleacesso c Where c.situacao='LIBERADO' ";
        if ((dataInicio != null) && (dataFinal != null)) {
            sql = sql + " and c.data>='" + Formatacao.ConvercaoDataSql(dataInicio)
                    + "' and c.data<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";

        }
        listaControleAcesso = controleAcessoDao.list(sql);
        for (int i = 0; i < listaControleAcesso.size(); i++) {
            InformacoesFrequenciaBean frequenciaBean = new InformacoesFrequenciaBean();
            if (listaControleAcesso.get(i).getIddependente() > 0) {
                Dependente dependente = buscarDependente(listaControleAcesso.get(i).getIddependente());
                frequenciaBean.setIdAcessoDependente(quantidadeFrequencia(listaControleAcesso.get(i)));
                frequenciaBean.setNomeDependente(dependente.getNome());
                frequenciaBean.setTipo(listaControleAcesso.get(i).getTipo());
                frequenciaBean.setIdAssociado(dependente.getAssociado().getIdassociado());
            } else {
                frequenciaBean.setIdAssociado(listaControleAcesso.get(i).getAssociado().getIdassociado());
                frequenciaBean.setNomeAssociado(listaControleAcesso.get(i).getAssociado().getCliente().getNome());
                frequenciaBean.setTipo(listaControleAcesso.get(i).getTipo());
                frequenciaBean.setIdAcessoAssocioado(quantidadeFrequencia(listaControleAcesso.get(i)));
            }
            lista.add(frequenciaBean);
        }
        return lista;
    }

    public Dependente buscarDependente(int iddependente) {
        Dependente dependente = dependenteDao.find(iddependente);
        return dependente;
    }

    public Integer quantidadeFrequencia(Controleacesso controleacesso) {
        String sql = "";
        if (controleacesso.getIddependente() > 0) {
            sql = " Select count(c.iddependente) From Controleacesso Where c.iddependente=" + controleacesso.getIddependente();
        } else {
            sql = " Select count(c.associado.idassociado) From Controleacesso Where c.associado.idassociado=" + controleacesso.getAssociado().getIdassociado();
        }
        try {
            Integer numeroFrequencia = controleAcessoDao.numeroFrequencia(sql);
            return numeroFrequencia;
        } catch (SQLException ex) {
            Logger.getLogger(ImprimirAcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
