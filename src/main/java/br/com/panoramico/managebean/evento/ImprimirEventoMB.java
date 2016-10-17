/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.evento;

import br.com.panoramico.dao.AmbienteDao;
import br.com.panoramico.dao.EventoDao;
import br.com.panoramico.model.Ambiente;
import br.com.panoramico.model.Evento;
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
public class ImprimirEventoMB implements Serializable {

    private Evento evento;
    @EJB
    private EventoDao eventoDao;
    @EJB
    private AmbienteDao ambienteDao;
    private Date dataInicio;
    private Date dataFinal;
    private List<Ambiente> listaAmbientes;
    private Ambiente ambiente;
    private String tipoRelatorio;
    private Float totalFaturmento;

    @PostConstruct
    public void init() {
        gerarListaAmbiente();
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public EventoDao getEventoDao() {
        return eventoDao;
    }

    public void setEventoDao(EventoDao eventoDao) {
        this.eventoDao = eventoDao;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public AmbienteDao getAmbienteDao() {
        return ambienteDao;
    }

    public void setAmbienteDao(AmbienteDao ambienteDao) {
        this.ambienteDao = ambienteDao;
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

    public List<Ambiente> getListaAmbientes() {
        return listaAmbientes;
    }

    public void setListaAmbientes(List<Ambiente> listaAmbientes) {
        this.listaAmbientes = listaAmbientes;
    }

    public String getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(String tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    public Float getTotalFaturmento() {
        return totalFaturmento;
    }

    public void setTotalFaturmento(Float totalFaturmento) {
        this.totalFaturmento = totalFaturmento;
    }

    public String gerarRelatorio() throws SQLException, IOException {
        String nomeAmbiente = "";
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "";
        Map<String, Object> parameters = new HashMap<String, Object>();
        if (tipoRelatorio.equalsIgnoreCase("faturamentoEvento")) {
            caminhoRelatorio = "reports/relatorios/eventos/reportFaturamentoEvento.jasper";
            gerarValorTotal();
            parameters.put("total", totalFaturmento);
        } else {
            caminhoRelatorio = "reports/relatorios/eventos/reportAgendaEventos.jasper";
        }
        parameters.put("sql", gerarSql());
        if (ambiente == null || ambiente.getIdambiente() == null) {
            nomeAmbiente = "Todos";
        } else {
            nomeAmbiente = ambiente.getNome();
        }
        parameters.put("ambiente", nomeAmbiente);
        File f = new File(servletContext.getRealPath("resources/img/logo.png"));
        BufferedImage logo = ImageIO.read(f);
        parameters.put("logo", logo);
        String periodo = null;
        if (dataInicio != null && dataFinal != null) {
            periodo = "Periodo : " + Formatacao.ConvercaoDataPadrao(dataInicio)
                    + "    " + Formatacao.ConvercaoDataPadrao(dataFinal);
        } else {
            periodo = " Sem Periodo";
        }
        parameters.put("periodo", periodo);
        GerarRelatorios gerarRelatorio = new GerarRelatorios();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "fluxocaixa", null);
        } catch (JRException ex) {
            Logger.getLogger(ImprimirEventoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String gerarSql() {
        String sql = "";
        if (tipoRelatorio.equalsIgnoreCase("faturamentoEvento")) {
            sql = " Select distinct evento.data, evento.valor, ambiente.nome, evento.idevento From evento "
                    + " Join ambiente on evento.ambiente_idambiente=ambiente.idambiente ";
            if (((dataInicio != null) && (dataFinal != null)) || (ambiente != null && ambiente.getIdambiente() != null)) {
                sql = sql + " Where ";
            }
            if ((dataInicio != null) && (dataFinal != null)) {
                sql = sql + "evento.data>='" + Formatacao.ConvercaoDataSql(dataInicio)
                        + "' and evento.data<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
                if (ambiente != null && ambiente.getIdambiente() != null) {
                    sql = sql + " and ";
                }
            }

            if (ambiente != null && ambiente.getIdambiente() != null) {
                sql = sql + " evento.ambiente_idambiente=" + ambiente.getIdambiente();
            }
            sql = sql + " order by evento.data";
        } else {
            sql = "Select distinct evento.data, ambiente.nome, tipoenvento.descricao, evento.idevento from evento "
                    + " Join ambiente on evento.ambiente_idambiente= ambiente.idambiente "
                    + " Join tipoenvento on evento.tipoenvento_idtipoenvento= tipoenvento.idtipoenvento ";
            if (((dataInicio != null) && (dataFinal != null)) || (ambiente != null && ambiente.getIdambiente() != null)) {
                sql = sql + " Where ";
            }
            if ((dataInicio != null) && (dataFinal != null)) {
                sql = sql + " evento.data>='" + Formatacao.ConvercaoDataSql(dataInicio)
                        + "' and evento.data<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
                if (ambiente != null && ambiente.getIdambiente() != null) {
                    sql = sql + " and ";
                }
            } 
            if (ambiente != null && ambiente.getIdambiente() != null) {
                sql = sql + " evento.ambiente_idambiente=" + ambiente.getIdambiente();
            }
            sql = sql + " order by evento.data";
        }
        return sql;
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void gerarListaAmbiente() {
        listaAmbientes = ambienteDao.list("Select a From Ambiente a");
        if (listaAmbientes == null || listaAmbientes.isEmpty()) {
            listaAmbientes = new ArrayList<Ambiente>();
        }
    }

    public void gerarValorTotal() {
        String sql = " Select e From Evento e ";
        if (((dataInicio != null) && (dataFinal != null)) || (ambiente != null && ambiente.getIdambiente() != null)) {
            sql = sql + " Where ";
        }
        if ((dataInicio != null) && (dataFinal != null)) {
            sql = sql + "e.data>='" + Formatacao.ConvercaoDataSql(dataInicio)
                    + "' and e.data<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";

            if (ambiente != null && ambiente.getIdambiente() != null) {
                sql = sql + " and ";
            }
        }

        if (ambiente != null && ambiente.getIdambiente() != null) {
            sql = sql + " e.ambiente.idambiente=" + ambiente.getIdambiente();
        }
        totalFaturmento = 0f;
        List<Evento> lista = eventoDao.list(sql);
        for (int i = 0; i < lista.size(); i++) {
            totalFaturmento = totalFaturmento + lista.get(i).getValor();
        }
    }
}
