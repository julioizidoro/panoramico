/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.model.Associado;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Kamilla Rodrigues
 */

@FacesConverter(value = "AssociadoConverter")
public class AssociadoConverter {
    
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Associado> listaAssociado = (List<Associado>) arg1.getAttributes().get("listaAssociado");
	    if (listaAssociado != null) {
	        for (Associado associado : listaAssociado) {
	            if (associado.getBairro().equalsIgnoreCase(arg2)) {
	                return associado;
	            }
	        }
	    } else {
	        Associado associado = new Associado();
	        return associado;
	    }
	    Associado associado = new Associado();
	    return associado;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Associado associado = (Associado) arg2;
	        return associado.getBairro();
	    }
	}
}
