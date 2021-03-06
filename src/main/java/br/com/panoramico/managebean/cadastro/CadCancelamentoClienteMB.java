/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.CCancelamentoDao;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.DependenteDao;
import br.com.panoramico.dao.MotivoCancelamentoDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.managebean.relatorios.RelatorioAssociadoMB;
import br.com.panoramico.managebean.relatorios.RelatorioCancelamentoClienteMB;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Ccancelamento;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Motivocancelamento;
import br.com.panoramico.uil.GerarRelatorios;
import br.com.panoramico.uil.Mensagem;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Anderson
 */
@Named
@ViewScoped
public class CadCancelamentoClienteMB implements Serializable {

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private String mensagem = "";
    private Cliente cliente;
    private Ccancelamento ccancelamento;
    @EJB
    private CCancelamentoDao cCancelamentoDao;
    private List<Contasreceber> listaContasReceber;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private DependenteDao dependenteDao;
    @EJB
    private AssociadoDao associadoDao;
    private Motivocancelamento motivocancelamento;
    private List<Motivocancelamento> listaMotivoCancelamento;
    @EJB
    private MotivoCancelamentoDao motivoCancelamentoDao;
    private Associado associado;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        associado = (Associado) session.getAttribute("associado");
        session.removeAttribute("associado");
        gerarListaMotivoCancelamento();
        if (associado == null) {
            Mensagem.lancarMensagemInfo("", " Associado não selecionado");
            RequestContext.getCurrentInstance().closeDialog(new Ccancelamento());
        } else {
            ccancelamento = new Ccancelamento();
            ccancelamento.setData(new Date());
        }
        retornarHoraAtual();
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Ccancelamento getCcancelamento() {
        return ccancelamento;
    }

    public void setCcancelamento(Ccancelamento ccancelamento) {
        this.ccancelamento = ccancelamento;
    }

    public List<Contasreceber> getListaContasReceber() {
        return listaContasReceber;
    }

    public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
        this.listaContasReceber = listaContasReceber;
    }

    public Motivocancelamento getMotivocancelamento() {
        return motivocancelamento;
    }

    public void setMotivocancelamento(Motivocancelamento motivocancelamento) {
        this.motivocancelamento = motivocancelamento;
    }

    public List<Motivocancelamento> getListaMotivoCancelamento() {
        return listaMotivoCancelamento;
    }

    public void setListaMotivoCancelamento(List<Motivocancelamento> listaMotivoCancelamento) {
        this.listaMotivoCancelamento = listaMotivoCancelamento;
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(new Ccancelamento());
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public void salvar() {
        ccancelamento.setUsuario(usuarioLogadoMB.getUsuario());
        ccancelamento.setCliente(associado.getCliente());
        String mensagem = validarDados(ccancelamento);
        if (mensagem.length() < 5) {
            ccancelamento.setMotivocancelamento(motivocancelamento);
            ccancelamento = cCancelamentoDao.update(ccancelamento);
            associado.setSituacao("Inativo");
            associado = associadoDao.update(associado);
            if (associado.getDependenteList() != null && associado.getDependenteList().size() > 0) {
                for (int i = 0; i < associado.getDependenteList().size(); i++) {
                    associado.getDependenteList().get(i).setSituacao("Inativo");
                    dependenteDao.update(associado.getDependenteList().get(i));
                }
            }
            listaContasReceber = contasReceberDao.list("select c from Contasreceber c where c.cliente.idcliente=" + associado.getCliente().getIdcliente());
            if (listaContasReceber == null) {
                listaContasReceber = new ArrayList<>();
            }

            for (int i = 0; i < listaContasReceber.size(); i++) {
                listaContasReceber.get(i).setSituacao("CANCELADO");
                contasReceberDao.update(listaContasReceber.get(i));
            }

            RequestContext.getCurrentInstance().closeDialog(ccancelamento);
        }
    }

    public String validarDados(Ccancelamento ccancelamento) {
        String msg = "";
        if (ccancelamento.getMotivo().equalsIgnoreCase("")) {
            msg = msg + " você não informou o motivo do cancelamento \r\n";
        }

        if (motivocancelamento == null) {
            msg = msg + " Selecione o motivo do cancelamento \r\n";
        }
        return msg;
    }

    public void retornarHoraAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date hora = Calendar.getInstance().getTime();
        ccancelamento.setHora(sdf.format(hora));
    }

    public void gerarListaMotivoCancelamento() {
        listaMotivoCancelamento = motivoCancelamentoDao.list("select m from Motivocancelamento m");
        if (listaMotivoCancelamento == null) {
            listaMotivoCancelamento = new ArrayList<>();
        }
    }

    public void iniciarRelatorio() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        Map<String, Object> parameters = new HashMap<String, Object>();
        String caminhoRelatorio  = "reports/relatorios/cliente/termoCancelamento.jasper";
        parameters.put("sql", gerarSQL());
        if (associado == null) {
            parameters.put("matricula", 0);
        } else {
            parameters.put("matricula", associado.getMatricula());
        }
        if (motivocancelamento == null) {
            parameters.put("motivo", "");
        } else {
            parameters.put("motivo", motivocancelamento.getDescricao());
        }
        parameters.put("descricao", ccancelamento.getMotivo());
        parameters.put("data", ccancelamento.getData());
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
                gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "cliente", null);
            } catch (IOException | SQLException ex) {
                Logger.getLogger(RelatorioCancelamentoClienteMB.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JRException ex) {
            Logger.getLogger(RelatorioCancelamentoClienteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestContext.getCurrentInstance().closeDialog(ccancelamento);
    }

    public String gerarSQL() {
        String sql = "select distinct cliente.nome, cliente.cpf, cliente.rg"
                + " from cliente";
        sql = sql + " where cliente.idcliente=" + associado.getCliente().getIdcliente();
//        if (dataInicio != null && dataInicio != null) {
//            sql = sql + " and ccancelamento.data>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'"
//                    + " and ccancelamento.data<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'"; 
//        }
        sql = sql + " order by cliente.nome";
        return sql;
    }

}
