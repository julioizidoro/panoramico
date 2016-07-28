/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.model.Planoconta;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "PlanoContaConverter")
public class PlanoContaConverter implements Converter{
    
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Planoconta> listaPlanoContas = (List<Planoconta>) arg1.getAttributes().get("listaPlanoContas");
	    if (listaPlanoContas != null) {
	        for (Planoconta planoconta : listaPlanoContas) {
	            if (planoconta.getDescricao().equalsIgnoreCase(arg2)) {
	                return planoconta;
	            }
	        }
	    } else {
	        Planoconta planoconta = new Planoconta();
	        return planoconta;
	    }
	    Planoconta planoconta = new Planoconta();
	    return planoconta;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Planoconta planoconta = (Planoconta) arg2;
	        return planoconta.getDescricao();
	    }
	}
}
