/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.acesso;

import br.com.panoramico.dao.PassaporteDao;
import br.com.panoramico.model.Passaporte;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class UtilizadoPassaporteMB implements Serializable {

    private Passaporte passaporte;
    @EJB
    private PassaporteDao passaporteDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        passaporte = (Passaporte) session.getAttribute("passaporte");
        session.removeAttribute("passaporte");
        passaporte.setDataacesso(new Date());
        retornarHoraAtual();
    }

    public Passaporte getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(Passaporte passaporte) {
        this.passaporte = passaporte;
    }

    public void retornarHoraAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date hora = Calendar.getInstance().getTime();
        passaporte.setHoraacesso(sdf.format(hora));
    }

    public void salvar() {
        String msg = validarDados();
        if (msg.length() < 5) {
            passaporte = passaporteDao.update(passaporte);
            RequestContext.getCurrentInstance().closeDialog(passaporte);
        }else{
            Mensagem.lancarMensagemInfo(msg, "");
        }
    }

    public String validarDados() {
        String msg = "";
        if (passaporte.getAcessoadulto() > passaporte.getAdultos()) {
            msg = msg + " Quantidade de adulto maior que o passaporte permite \r\n";
        }
        if (passaporte.getAcessocrianca() > passaporte.getCriancas()) {
            msg = msg + " Quantidade de criança maior que o passaporte permite \r\n";
        }
        return msg;
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(new Passaporte());
    }
}
