/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.model.Plano;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Julio
 */
@FacesConverter(value="PlanoConverter")
public class PlanoConverter implements Converter {
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Plano> listaPlano = (List<Plano>) arg1.getAttributes().get("listaPlano");
	    if (listaPlano != null) {
	        for (Plano plano : listaPlano) {
	            if (plano.getDescricao().equalsIgnoreCase(arg2)) {
	                return plano;
	            }
	        }
	    } else {
	        Plano plano = new Plano();
	        return plano;
	    }
	    Plano plano = new Plano();
	    return plano;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Plano plano = (Plano) arg2;
	        return plano.getDescricao();
	    }
	}
}
