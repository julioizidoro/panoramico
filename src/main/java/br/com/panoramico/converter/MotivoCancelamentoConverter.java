/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.model.Motivocancelamento;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Anderson
 */

@FacesConverter(value = "MotivoCancelamentoConverter")
public class MotivoCancelamentoConverter implements Converter{

   public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Motivocancelamento> listaMotivoCancelamento = (List<Motivocancelamento>) arg1.getAttributes().get("listaMotivoCancelamento");
	    if (listaMotivoCancelamento != null) {
	        for (Motivocancelamento motivocancelamento : listaMotivoCancelamento) {
	            if (motivocancelamento.getDescricao().equalsIgnoreCase(arg2)) {
	                return motivocancelamento;
	            }
	        }
	    } else {
	        Motivocancelamento motivocancelamento = new Motivocancelamento();
	        return motivocancelamento;
	    }
	    Motivocancelamento motivocancelamento = new Motivocancelamento();
	    return motivocancelamento;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Motivocancelamento motivocancelamento = (Motivocancelamento) arg2;
	        return motivocancelamento.getDescricao();
	    }
	}
}
