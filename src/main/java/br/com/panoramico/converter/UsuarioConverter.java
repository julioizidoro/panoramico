/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.model.Usuario;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "UsuarioConverter")
public class UsuarioConverter implements Converter{
    
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Usuario> listaUsuarios = (List<Usuario>) arg1.getAttributes().get("listaUsuarios");
	    if (listaUsuarios != null) {
	        for (Usuario usuario : listaUsuarios) {
	            if (usuario.getNome().equalsIgnoreCase(arg2)) {
	                return usuario;
	            }
	        }
	    } else {
	        Usuario usuario = new Usuario();
	        return usuario;
	    }
	    Usuario usuario = new Usuario();
	    return usuario;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Usuario usuario = (Usuario) arg2;
	        return usuario.getNome();
	    }
	}
}
