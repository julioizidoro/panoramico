package br.com.panoramico.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.com.panoramico.model.Cliente;

@FacesConverter(value="ClienteConverter")
public class ClienteConverter implements Converter {
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Cliente> listaCliente = (List<Cliente>) arg1.getAttributes().get("listaCliente");
	    if (listaCliente != null) {
	        for (Cliente cliente : listaCliente) {
	            if (cliente.getNome().equalsIgnoreCase(arg2)) {
	                return cliente;
	            }
	        }
	    } else {
	        Cliente cliente = new Cliente();
	        return cliente;
	    }
	    Cliente cliente = new Cliente();
	    return cliente;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Cliente cliente = (Cliente) arg2;
	        return cliente.getNome();
	    }
	}
}
