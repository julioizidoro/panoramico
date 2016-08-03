/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.model.Medico;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "MedicoConverter")
public class MedicoConverter implements Converter{
    
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Medico> listaMedico = (List<Medico>) arg1.getAttributes().get("listaMedico");
	    if (listaMedico != null) {
	        for (Medico medico : listaMedico) {
	            if (medico.getNome().equalsIgnoreCase(arg2)) {
	                return medico;
	            }
	        }
	    } else {
	        Medico medico = new Medico();
	        return medico;
	    }
	    Medico medico = new Medico();
	    return medico;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Medico medico = (Medico) arg2;
	        return medico.getNome();
	    }
	}
}
