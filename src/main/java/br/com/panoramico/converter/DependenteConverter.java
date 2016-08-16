/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.model.Dependente;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "DependenteConverter")
public class DependenteConverter implements Converter{

    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Dependente> listaDependente = (List<Dependente>) arg1.getAttributes().get("listaDependente");
	    if (listaDependente != null) {
	        for (Dependente dependente : listaDependente) {
	            if (dependente.getNome().equalsIgnoreCase(arg2)) {
	                return dependente;
	            }
	        }
	    } else {
	        Dependente dependente = new Dependente();
	        return dependente;
	    }
	    Dependente dependente = new Dependente();
	    return dependente;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Dependente dependente = (Dependente) arg2;
	        return dependente.getNome();
	    }
	}
    
}
