/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.dao;

import br.com.panoramico.model.Tipoenvento;
import javax.ejb.Stateless;

/**
 *
 * @author Julio
 */


@Stateless
public class TipoEventoDao extends AbstractDao<Tipoenvento>{

    public TipoEventoDao() {
        super(Tipoenvento.class);
    }
    
    
}
