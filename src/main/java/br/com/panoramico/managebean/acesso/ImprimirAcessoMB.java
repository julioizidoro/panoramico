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
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class ImprimirAcessoMB implements Serializable{
    
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
    public void init(){
        
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
        Map<String, Object> parameters = new HashMap<String, Object>();
        caminhoRelatorio = "reports/relatorios/acesso/reportNumeroAcesso.jasper"; 
        parameters.put("sql", gerarSql());
        File f = new File(servletContext.getRealPath("resources/img/logo.png"));
        BufferedImage logo = ImageIO.read(f);
        parameters.put("logo", logo);
        String periodo = null;
        if (dataInicio != null && dataFinal != null) {
            periodo = "Periodo : " + Formatacao.ConvercaoDataPadrao(dataInicio)
                    + "    " + Formatacao.ConvercaoDataPadrao(dataFinal);
        }
        parameters.put("periodo", periodo);
        gerarTotalAcesso(); 
        parameters.put("total", totalNAcesso);
        GerarRelatorios gerarRelatorio = new GerarRelatorios();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "fluxocaixa", null);
        } catch (JRException ex) {
            Logger.getLogger(ImprimirAcessoMB.class.getName()).log(Level.SEVERE, null, ex);
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
        }else{
            if (!tipoAcesso.equalsIgnoreCase("")) {
                sql = sql + " and controleacesso.tipo='" + tipoAcesso + "' ";
            }
        }
        

        return sql;
    }  
    
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
    }
    
    
    public void gerarTotalAcesso(){
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
    
    public List<InformacoesFrequenciaBean> gerarListaFrequencia(){
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
            if (listaControleAcesso.get(i).getIddependente() > 0) {
                InformacoesFrequenciaBean frequenciaBean = new InformacoesFrequenciaBean();
                Dependente dependente = buscarDependente(listaControleAcesso.get(i).getIddependente());
                frequenciaBean.setIdAcessoDependente(listaControleAcesso.get(i).getIdcontroleacesso());
                frequenciaBean.setNomeDependente(dependente.getNome());
                frequenciaBean.setTipo(listaControleAcesso.get(i).getTipo());
                frequenciaBean.setIdAssociado(dependente.getAssociado().getIdassociado());
                frequenciaBean.setIdAcessoAssocioado(0);
            }else{
                InformacoesFrequenciaBean frequenciaBean = new InformacoesFrequenciaBean();
                frequenciaBean.setIdAssociado(listaControleAcesso.get(i).getAssociado().getIdassociado());
                frequenciaBean.setNomeAssociado(listaControleAcesso.get(i).getAssociado().getCliente().getNome());
                
            }
        }
        return lista;
    }
    
    
    public Dependente buscarDependente(int iddependente){
        Dependente dependente = dependenteDao.find(iddependente);
        return dependente;
    }
}
