/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.model.Passaportevalor;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "PassaporteValorConverter")
public class PassaporteValorConverter implements Converter{

    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Passaportevalor> listaPassaporteValor = (List<Passaportevalor>) arg1.getAttributes().get("listaPassaporteValor");
	    if (listaPassaporteValor != null) {
	        for (Passaportevalor passaportevalor : listaPassaporteValor) {
	            if (passaportevalor.getDescriccao().equalsIgnoreCase(arg2)) {
	                return passaportevalor;
	            }
	        }
	    } else {
	        Passaportevalor passaportevalor = new Passaportevalor();
	        return passaportevalor;
	    }
	    Passaportevalor passaportevalor = new Passaportevalor();
	    return passaportevalor;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Passaportevalor passaportevalor = (Passaportevalor) arg2;
	        return passaportevalor.getDescriccao();
	    }
	}
    
}
