/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.MedicoDao;
import br.com.panoramico.model.Medico;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class MedicoMB implements Serializable {

    private Medico medico;
    private List<Medico> listaMedicos;
    @EJB
    private MedicoDao medicoDao;
    private String nome;

    @PostConstruct
    public void init() {
        gerarListaMedico();
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public List<Medico> getListaMedicos() {
        return listaMedicos;
    }

    public void setListaMedicos(List<Medico> listaMedicos) {
        this.listaMedicos = listaMedicos;
    }

    public MedicoDao getMedicoDao() {
        return medicoDao;
    }

    public void setMedicoDao(MedicoDao medicoDao) {
        this.medicoDao = medicoDao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void gerarListaMedico() {
        listaMedicos = medicoDao.list("Select m from Medico m where m.idusuario>0 order by m.nome");
        if (listaMedicos == null) {
            listaMedicos = new ArrayList<Medico>();
        }
    }

    public String novoCadastroMedico() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 545);
        RequestContext.getCurrentInstance().openDialog("cadMedico", options, null);
        return "";
    }

    public void retornoDialogNovo(SelectEvent event) {
        Medico medico = (Medico) event.getObject();
        if (medico.getIdmedico() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Cadastro de medico realizado com sucesso");
        }
        gerarListaMedico();
    }

    public void retornoDialogAlteracao(SelectEvent event) {
        Medico medico = (Medico) event.getObject();
        if (medico.getIdmedico() != null) {
            Mensagem.lancarMensagemInfo("Salvou", "Alteração de medico realizado com sucesso");
        }
        gerarListaMedico();
    }

    public void editar(Medico medico) {
        if (medico != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("medico", medico);
            options.put("contentWidth", 545);
            RequestContext.getCurrentInstance().openDialog("cadMedico", options, null);
        }
    }

    public void excluir(Medico medico) {
        medicoDao.remove(medico.getIdmedico());
        Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
        gerarListaMedico();
    }

    public void pesquisar() {
        listaMedicos = medicoDao.list("Select m from Medico m where m.nome like '%" + nome + "%' order by m.nome");
        if (listaMedicos == null) {
            listaMedicos = new ArrayList<Medico>();
        }
    }

    public void limpar() {
        nome = "";
        gerarListaMedico();
    }
}
