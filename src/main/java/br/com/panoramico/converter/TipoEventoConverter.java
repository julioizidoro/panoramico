/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.model.Tipoenvento;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Kamilla Rodrigues
 */

@FacesConverter(value = "TipoEventoConverter")
public class TipoEventoConverter implements Converter{
    
    
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Tipoenvento> listaTipoEvento = (List<Tipoenvento>) arg1.getAttributes().get("listaTipoEvento");
	    if (listaTipoEvento != null) {
	        for (Tipoenvento tipoEnvento : listaTipoEvento) {
	            if (tipoEnvento.getDescricao().equalsIgnoreCase(arg2)) {
	                return tipoEnvento;
	            }
	        }
	    } else {
	        Tipoenvento tipoenvento = new Tipoenvento();
	        return tipoenvento;
	    }
	    Tipoenvento tipoenvento = new Tipoenvento();
	    return tipoenvento;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Tipoenvento tipoenvento = (Tipoenvento) arg2;
	        return tipoenvento.getDescricao();
	    }
	}

}
