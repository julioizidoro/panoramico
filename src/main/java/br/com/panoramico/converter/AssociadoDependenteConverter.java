/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.converter;

import br.com.panoramico.model.Associado;
import br.com.panoramico.model.Dependente;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Kamilla Rodrigues
 */
@FacesConverter(value = "AssociadoDependenteConverter")
public class AssociadoDependenteConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Object> listaAssociado = (List<Object>) component.getAttributes().get("listaAssociadoDependente");
        if (listaAssociado != null) {
            Object obj = listaAssociado.get(0);
            if (obj instanceof Associado) {
                for (int i = 0; i < listaAssociado.size(); i++) {
                    Associado a = (Associado) listaAssociado.get(i);
                    if (a.getCliente().getNome().equalsIgnoreCase(value)) {
                        return a;
                    }
                }
            } else if (obj instanceof Dependente) {
                for (int i = 0; i < listaAssociado.size(); i++) {
                    Dependente d = (Dependente) listaAssociado.get(i);
                    if (d.getNome().equalsIgnoreCase(value)) {
                        return d;
                    }
                }
            }
        } else {
            Object obj = new Object();
            return obj;
        }
        Object obj = new Object();
        return obj;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
            if (value instanceof Associado){
                Associado a = (Associado) value;
                return a.getCliente().getNome();
            }else if (value instanceof Dependente){
                Dependente d = (Dependente) value;
                return d.getNome();
            }
        }
        return "Selecione";
    }
    
}
