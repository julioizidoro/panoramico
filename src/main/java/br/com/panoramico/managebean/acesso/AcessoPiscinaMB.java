/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.acesso;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.ExameAssociadoDao;
import br.com.panoramico.dao.ExameDao;
import br.com.panoramico.dao.ExameDependenteDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Exame;
import br.com.panoramico.model.Exameassociado;
import br.com.panoramico.model.Examedependente;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AcessoPiscinaMB implements Serializable {

    private String nomeCliente;
    private String matricula;
    private Exame exame;
    private List<Exame> listaExame;
    @EJB
    private ExameDao exameDao;
    private Associado associado;
    private List<Associado> listaAssociado;
    @EJB
    private AssociadoDao associadoDao;
    private Examedependente examedependente;
    private List<Examedependente> listaExameDependente;
    @EJB
    private ExameDependenteDao exameDependenteDao;
    private Exameassociado exameassociado;
    private List<Exameassociado> listaExameAssociado;
    @EJB
    private ExameAssociadoDao exameAssociadoDao;

    @PostConstruct
    public void init() {
        listaExameAssociado = new ArrayList<>();
        listaExameDependente = new ArrayList<>();
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public List<Exame> getListaExame() {
        return listaExame;
    }

    public void setListaExame(List<Exame> listaExame) {
        this.listaExame = listaExame;
    }

    public ExameDao getExameDao() {
        return exameDao;
    }

    public void setExameDao(ExameDao exameDao) {
        this.exameDao = exameDao;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public List<Associado> getListaAssociado() {
        return listaAssociado;
    }

    public void setListaAssociado(List<Associado> listaAssociado) {
        this.listaAssociado = listaAssociado;
    }

    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
    }

    public Examedependente getExamedependente() {
        return examedependente;
    }

    public void setExamedependente(Examedependente examedependente) {
        this.examedependente = examedependente;
    }

    public List<Examedependente> getListaExameDependente() {
        return listaExameDependente;
    }

    public void setListaExameDependente(List<Examedependente> listaExameDependente) {
        this.listaExameDependente = listaExameDependente;
    }

    public ExameDependenteDao getExameDependenteDao() {
        return exameDependenteDao;
    }

    public void setExameDependenteDao(ExameDependenteDao exameDependenteDao) {
        this.exameDependenteDao = exameDependenteDao;
    }

    public Exameassociado getExameassociado() {
        return exameassociado;
    }

    public void setExameassociado(Exameassociado exameassociado) {
        this.exameassociado = exameassociado;
    }

    public List<Exameassociado> getListaExameAssociado() {
        return listaExameAssociado;
    }

    public void setListaExameAssociado(List<Exameassociado> listaExameAssociado) {
        this.listaExameAssociado = listaExameAssociado;
    }

    public ExameAssociadoDao getExameAssociadoDao() {
        return exameAssociadoDao;
    }

    public void setExameAssociadoDao(ExameAssociadoDao exameAssociadoDao) {
        this.exameAssociadoDao = exameAssociadoDao;
    }

    public void pesquisar() {
        listaExame = new ArrayList<>();
        String sql = "select e from Exame e where ";
        String msg = "";
        if (nomeCliente.length() > 0) {
            sql = sql + " e.nomeCliente like '%" + nomeCliente + "%'";
            if (matricula.length() > 0) {
                sql = sql + " and ";
            }
        } else {
            if (matricula.length() < 0) {
                msg = msg + " Nome do cliente ou matricula não foram informadas \r\n";
            }
        }
        if (matricula.length() > 0) {
            sql = sql + " e.matricula='" + matricula + "'";
        }

        if (msg.length() > 1) {
            Mensagem.lancarMensagemInfo(msg, "");
        } else {
            listaExame = exameDao.list(sql);
            if (listaExame == null || listaExame.isEmpty()) {
                listaExame = new ArrayList<>();
            }
        }
    }

    public void limpar() {
        listaExameAssociado = new ArrayList<>();
        listaExameDependente = new ArrayList<>();
        matricula = "";
        nomeCliente = "";
    }

    public void pesquisarDependentes() {
        String msg = "";
        String sql = "select ed from Examedependente ed where ";

        if (nomeCliente.length() > 0) {
            sql = sql + " ed.dependente.nome like '%" + nomeCliente + "%'";
            if (matricula.length() > 0) {
                sql = sql + " and ";
            }
        } else {
            if (matricula.length() < 0) {
                msg = msg + " Nome do cliente ou matricula não foram informadas \r\n";
            }
        }
        if (matricula.length() > 0) {
            sql = sql + " ed.dependente.matricula='" + matricula + "'";
        }

        if (msg.length() > 1) {
            Mensagem.lancarMensagemInfo(msg, "");
        } else {
            listaExameDependente = exameDependenteDao.list(sql);
            if (listaExameDependente == null || listaExameDependente.isEmpty()) {
                listaExameDependente = new ArrayList<>();
            }
        }
    }

    public void pesquisarAssociado() {
        String msg = "";
        String sql = "select ea from Exameassociado ea where ";

        if (nomeCliente.length() > 0) {
            sql = sql + " ea.associado.cliente.nome like '%" + nomeCliente + "%'";
            if (matricula.length() > 0) {
                sql = sql + " and ";
            }
        } else {
            if (matricula.length() < 0) {
                msg = msg + " Nome do cliente ou matricula não foram informadas \r\n";
            }
        }
        if (matricula.length() > 0) {
            sql = sql + " ea.associado.matricula='" + matricula + "'";
        }

        if (msg.length() > 1) {
            Mensagem.lancarMensagemInfo(msg, "");
        } else {
            listaExameAssociado = exameAssociadoDao.list(sql);
            if (listaExameAssociado == null || listaExameAssociado.isEmpty()) {
                listaExameAssociado = new ArrayList<>();
            } else {
                if (listaExameDependente == null || listaExameDependente.isEmpty()) {
                    listaExameDependente = new ArrayList<>();
                    for (int i = 0; i < listaExameAssociado.size(); i++) {
                        List<Examedependente> listaExamedep = exameDependenteDao.list("select ed from Examedependente ed where ed.dependente.associado.idassociado="
                                + listaExameAssociado.get(i).getAssociado().getIdassociado());
                        for (int j = 0; j < listaExamedep.size(); j++) {
                            listaExameDependente.add(listaExamedep.get(j));
                        }
                    }
                }
            }
        }
    }

    public void pequisarAssociadoDependente() {
        pesquisarDependentes();
        pesquisarAssociado();
    }
}
