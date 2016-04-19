/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.model.Cliente;
import br.com.panoramico.model.Empresa;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Julio
 */
@FacesConverter(value="EmpresaConverter")
public class EmpresaConverter implements Converter {
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Empresa> listaEmpresa = (List<Empresa>) arg1.getAttributes().get("listaEmpresa");
	    if (listaEmpresa != null) {
	        for (Empresa empresa : listaEmpresa) {
	            if (empresa.getRazao().equalsIgnoreCase(arg2)) {
	                return empresa;
	            }
	        }
	    } else {
	        Empresa empresa = new Empresa();
	        return empresa;
	    }
	    Empresa empresa = new Empresa();
	    return empresa;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Empresa empresa = (Empresa) arg2;
	        return empresa.getRazao();
	    }
	}
}
