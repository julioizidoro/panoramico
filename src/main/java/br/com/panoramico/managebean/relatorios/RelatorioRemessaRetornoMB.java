/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.relatorios;

import br.com.panoramico.bean.RetornoBean;
import br.com.panoramico.dao.RemessaContasDao;
import br.com.panoramico.dao.RetornoContasDao;
import br.com.panoramico.managebean.RelatorioErroBean;
import br.com.panoramico.model.Remessaarquivo;
import br.com.panoramico.model.Remessacontas;
import br.com.panoramico.model.Retornoarquivo;
import br.com.panoramico.model.Retornocontas;
import br.com.panoramico.uil.Formatacao;
import br.com.panoramico.uil.GerarRelatorios;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Anderson
 */
@Named
@ViewScoped
public class RelatorioRemessaRetornoMB implements Serializable {

    private String tipo;
    private boolean tabelaRemessa;
    private boolean tabelaRetorno;
    private Date dataInicial;
    private Date dataFinal;
    private List<RetornoBean> listaEnviada;
    private List<Remessacontas> listaRemessaContas;
    private List<Retornocontas> listaRetornoContas;
    private Retornoarquivo retornoaruqivo;
    private Remessaarquivo remessaarquivo;
    private Remessacontas remessacontas;
    private Retornocontas retornocontas;
    @EJB
    private RemessaContasDao remessaContasDao;
    @EJB
    private RetornoContasDao retornoContasDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        tipo = (String) session.getAttribute("tipo");
        session.removeAttribute("tipo");
        if (tipo == null) {
            tipo = "";
        }
        if (tipo.equalsIgnoreCase("Remessa")) {
            tabelaRemessa = true;
        } else if (tipo.equalsIgnoreCase("Retorno")) {
            tabelaRetorno = true;
        }
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Remessaarquivo getRemessaarquivo() {
        return remessaarquivo;
    }

    public void setRemessaarquivo(Remessaarquivo remessaarquivo) {
        this.remessaarquivo = remessaarquivo;
    }

    public Remessacontas getRemessacontas() {
        return remessacontas;
    }

    public void setRemessacontas(Remessacontas remessacontas) {
        this.remessacontas = remessacontas;
    }

    public Retornoarquivo getRetornoaruqivo() {
        return retornoaruqivo;
    }

    public void setRetornoaruqivo(Retornoarquivo retornoaruqivo) {
        this.retornoaruqivo = retornoaruqivo;
    }

    public Retornocontas getRetornocontas() {
        return retornocontas;
    }

    public void setRetornocontas(Retornocontas retornocontas) {
        this.retornocontas = retornocontas;
    }

    public List<Retornocontas> getListaRetornoContas() {
        return listaRetornoContas;
    }

    public void setListaRetornoContas(List<Retornocontas> listaRetornoContas) {
        this.listaRetornoContas = listaRetornoContas;
    }

    public boolean isTabelaRemessa() {
        return tabelaRemessa;
    }

    public void setTabelaRemessa(boolean tabelaRemessa) {
        this.tabelaRemessa = tabelaRemessa;
    }

    public boolean isTabelaRetorno() {
        return tabelaRetorno;
    }

    public void setTabelaRetorno(boolean tabelaRetorno) {
        this.tabelaRetorno = tabelaRetorno;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public List<RetornoBean> getListaEnviada() {
        return listaEnviada;
    }

    public void setListaEnviada(List<RetornoBean> listaEnviada) {
        this.listaEnviada = listaEnviada;
    }

    public List<Remessacontas> getListaRemessaContas() {
        return listaRemessaContas;
    }

    public void setListaRemessaContas(List<Remessacontas> listaRemessaContas) {
        this.listaRemessaContas = listaRemessaContas;
    }
     
    
    

    public void direcionarPesquisa() {
        if (tipo.equalsIgnoreCase("Remessa")) {
            pesquisarRemessa();
        } else if (tipo.equalsIgnoreCase("Retorno")) {
            pesquisarRetorno();
        }
    }

    public void pesquisarRemessa() {
        String sql = "Select r From Remessacontas r ";
        if (dataInicial != null || dataFinal != null) {
            sql = sql + " Where ";
        }
        if (dataInicial != null) {
            sql = sql + " r.remessaarquivo.dataenvio>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' ";
            if (dataFinal != null) {
                sql = sql + " and ";
            }
        }

        if (dataFinal != null) {
            sql = sql + " r.remessaarquivo.dataenvio<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
        }

        listaRemessaContas = remessaContasDao.list(sql);

        if (listaRemessaContas == null) {
            listaRemessaContas = new ArrayList<>();
        }
    }  

    public void pesquisarRetorno() {
        String sql = "Select r From Retornocontas r ";
        if (dataInicial != null || dataFinal != null) {
            sql = sql + " Where ";
        }
        if (dataInicial != null) {
            sql = sql + " r.retornoarquivo.dataretorno>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' ";
            if (dataFinal != null) {
                sql = sql + " and ";
            }
        }

        if (dataFinal != null) {
            sql = sql + " r.retornoarquivo.dataretorno<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
        }

        listaRetornoContas = retornoContasDao.list(sql);

        if (listaRetornoContas == null) {
            listaRetornoContas = new ArrayList<>();
        }
    }

    public void imprimirRemessa(Remessacontas remessacontas) {
        listaEnviada = new ArrayList<>();
        RetornoBean retornoBean = new RetornoBean();
        retornoBean.setNomePagador(remessacontas.getContasreceber().getCliente().getNome());
        retornoBean.setValorJuros(0.0f);
        retornoBean.setNossoNumero(remessacontas.getContasreceber().getNossonumero());
        retornoBean.setValorTitulo(remessacontas.getContasreceber().getValorconta());
        retornoBean.setDataPagamento(Formatacao.ConvercaoDataPadrao(remessacontas.getContasreceber().getDatavencimento()));
        retornoBean.setCodigoOcorrencia(remessacontas.getCodigoocorrencia());
        listaEnviada.add(retornoBean);
        imprimirListaRemessa();
    }

    public void imprimirRetorno(Retornocontas retornocontas) {
        listaEnviada = new ArrayList<>();
        RetornoBean retornoBean = new RetornoBean();
        retornoBean.setNomePagador(retornocontas.getContasreceber().getCliente().getNome());
        retornoBean.setValorJuros(0.0f);
        retornoBean.setNossoNumero(retornocontas.getContasreceber().getNossonumero());
        retornoBean.setValorTitulo(retornocontas.getContasreceber().getValorconta());
        retornoBean.setDataPagamento(Formatacao.ConvercaoDataPadrao(retornocontas.getContasreceber().getDatavencimento()));
        retornoBean.setCodigoOcorrencia(retornocontas.getCodigoocorrencia());
        listaEnviada.add(retornoBean);
        imprimirListaRemessa();
    }

    public String imprimirListaRemessa() {
        if (listaEnviada != null && listaEnviada.size() > 0) {
            String caminhoRelatorio = "/reports/relatorios/contasReceber/reportboletositau.jasper";
            Map<String, Object> parameters = new HashMap<String, Object>();
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            File f = new File(servletContext.getRealPath("resources/img/logo.png"));
            BufferedImage logo = null;
            try {
                logo = ImageIO.read(f);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  
            parameters.put("logo", logo);
            parameters.put("titulo", "Remessa Enviada");
            parameters.put("tituloColunaData", "Data vcto.");
            JRDataSource jrds = new JRBeanCollectionDataSource(listaEnviada);
            GerarRelatorios gerarRelatorio = new GerarRelatorios();
            try {
                gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "BoletosItau.pdf");
            } catch (JRException ex) {
                Logger.getLogger(RelatorioRemessaRetornoMB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RelatorioRemessaRetornoMB.class.getName()).log(Level.SEVERE, null, ex);
            }
            listaEnviada = null;
        } else {
            FacesMessage msg = new FacesMessage("Lista de remessa vazia. ", " ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
            relatorioErroBean.iniciarRelatorioErro("Lista de remessa vazia.");
        }
        return "";
    }
}
