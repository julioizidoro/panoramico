/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.model.Ambiente;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Kamilla Rodrigues
 */

@FacesConverter(value = "AmbienteConverter")
public class AmbienteConverter implements Converter{
    
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Ambiente> listaAmbiente = (List<Ambiente>) arg1.getAttributes().get("listaAmbiente");
	    if (listaAmbiente != null) {
	        for (Ambiente ambiente : listaAmbiente) {
	            if (ambiente.getNome().equalsIgnoreCase(arg2)) {
	                return ambiente;
	            }
	        }
	    } else {
	        Ambiente ambiente = new Ambiente();
	        return ambiente;
	    }
	    Ambiente ambiente = new Ambiente();
	    return ambiente;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Ambiente ambiente = (Ambiente) arg2;
	        return ambiente.getNome();
	    }
	}
}
