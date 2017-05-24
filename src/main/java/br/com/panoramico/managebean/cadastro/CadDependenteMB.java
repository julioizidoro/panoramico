package br.com.panoramico.managebean.cadastro;

import br.com.panoramico.dao.AssociadoDao;
import br.com.panoramico.dao.DependenteDao;
import br.com.panoramico.dao.PlanoDao;
import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Dependente;
import br.com.panoramico.model.Plano;
import br.com.panoramico.uil.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Julio
 */

@Named
@ViewScoped
public class CadDependenteMB implements Serializable{
    
    @EJB
    private AssociadoDao associadoDao;
    private Associado associado;
    private List<Associado> listaAssociado;
    @EJB
    private DependenteDao dependenteDao;
    private Dependente dependente;
    @EJB
    private PlanoDao planoDao;
    private Plano plano;
    private List<Plano> listaPlano;
    private float valorPlano = 0.0f;
    private boolean noveDigito = false;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        dependente = (Dependente) session.getAttribute("dependente");
        session.removeAttribute("dependente"); 
        associado = (Associado) session.getAttribute("associado");
        session.removeAttribute("associado");
        if (dependente == null) {
            dependente = new Dependente(); 
        }else{
            associado = dependente.getAssociado();
            valorPlano = associado.getPlano().getValor();
        }
        gerarListaAssociado();
        gerarListaPlano();
    }
    
    public AssociadoDao getAssociadoDao() {
        return associadoDao;
    }

    public void setAssociadoDao(AssociadoDao associadoDao) {
        this.associadoDao = associadoDao;
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

    public DependenteDao getDependenteDao() {
        return dependenteDao;
    }

    public void setDependenteDao(DependenteDao dependenteDao) {
        this.dependenteDao = dependenteDao;
    }

    public Dependente getDependente() {
        return dependente;
    }

    public void setDependente(Dependente dependente) {
        this.dependente = dependente;
    }

    public PlanoDao getPlanoDao() {
        return planoDao;
    }

    public void setPlanoDao(PlanoDao planoDao) {
        this.planoDao = planoDao;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public List<Plano> getListaPlano() {
        return listaPlano;
    }

    public void setListaPlano(List<Plano> listaPlano) {
        this.listaPlano = listaPlano;
    }

    public float getValorPlano() {
        return valorPlano;
    }

    public void setValorPlano(float valorPlano) {
        this.valorPlano = valorPlano;
    }

    public boolean isNoveDigito() {
        return noveDigito;
    }

    public void setNoveDigito(boolean noveDigito) {
        this.noveDigito = noveDigito;
    }

   
    
    
    
    public void salvar(){
        dependente.setAssociado(associado);
        String msg = validarDados();
        if (msg.length() > 0) {
            Mensagem.lancarMensagemInfo("Atenção!! ", msg);
        }else{
            if(dependente.getIddependente()==null){
                dependente.setSituacao("Ativo");
                String matricula = associado.getMatricula()+"/";
                if(associado.getDependenteList()!=null && associado.getDependenteList().size()>0){
                    int numeroDepente = associado.getDependenteList().size()+1;
                    dependente.setMatricula(matricula+numeroDepente);
                }else{
                    dependente.setMatricula(matricula+"1");
                }
            }
            dependente = dependenteDao.update(dependente);
            RequestContext.getCurrentInstance().closeDialog(dependente);
        }
    }
    
    public void gerarListaAssociado(){
        listaAssociado = associadoDao.list("Select a from Associado a");
        if (listaAssociado == null) {
            listaAssociado = new ArrayList<Associado>();
        }
    }
    
    public void cancelar(){
        associado = associadoDao.find(1);
        RequestContext.getCurrentInstance().closeDialog(new Dependente());
    }
    
    public String validarDados(){
        String mensagem = "";
        if (associado == null || associado.getIdassociado() == null) {
            mensagem = mensagem + " Associado não selecionado \r\n";
            associado = associadoDao.find(1);
        }
        return mensagem;
    }
    
    public void gerarListaPlano(){
        listaPlano = planoDao.list("Select p From Plano p");
        if (listaPlano == null || listaPlano.isEmpty()) {
            listaPlano = new ArrayList<>();
        }
    }
    
    public void pegarValorPlano(){
        if (plano != null) {
            valorPlano = plano.getValor();
        }
    }
    
    
     public String habilitarNoveDigito(){
        if (noveDigito) {
            return "(99)999999999";
        }else{
            return "(99)99999999";
        }
    }
}
