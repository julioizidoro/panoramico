/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.managebean.evento.BolinhaBean;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "BolinhaConverter")
public class BolinhaConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<BolinhaBean> listaBolinha = (List<BolinhaBean>) component.getAttributes().get("listaBolinha");
        if (listaBolinha != null) {
            for (BolinhaBean bolinha : listaBolinha) {
                if (bolinha.getCor().equalsIgnoreCase(value)) {
                    return bolinha;
                }
            }
        }
        return new BolinhaBean();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
            BolinhaBean bolinha = (BolinhaBean) value;
            return bolinha.getCor();
        }
    }

}
