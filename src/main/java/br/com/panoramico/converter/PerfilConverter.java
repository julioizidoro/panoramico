/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.model.Perfil;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Kamilla Rodrigues
 */

@FacesConverter(value="PerfilConverter")
public class PerfilConverter implements Converter{
    
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Perfil> listaPerfil = (List<Perfil>) arg1.getAttributes().get("listaPerfil");
	    if (listaPerfil != null) {
	        for (Perfil perfil : listaPerfil) {
	            if (perfil.getNome().equalsIgnoreCase(arg2)) {
	                return perfil;
	            }
	        }
	    } else {
	        Perfil perfil = new Perfil();
	        return perfil;
	    }
	    Perfil perfil = new Perfil();
	    return perfil;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Perfil perfil = (Perfil) arg2;
	        return perfil.getNome();
	    }
	}
}
