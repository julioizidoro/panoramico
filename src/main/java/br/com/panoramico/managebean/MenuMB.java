package br.com.panoramico.managebean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class MenuMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@PostConstruct
	public void init(){
		
	}
	
	
	public String PaginaTeste(){
		return "incial";
	}
        
        
        public String CadastroProprietario(){
            return "cadProprietario";
        }
        
        public String CadastroProprietarioPrincipal(){
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 800);
            RequestContext.getCurrentInstance().openDialog("cadProprietarioPrincipal", options, null);
            return "";
        }
        
        public String ConsCliente(){
            return "consCliente";
        }

}
