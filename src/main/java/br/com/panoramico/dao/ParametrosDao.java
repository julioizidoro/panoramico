/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.dao;

import br.com.panoramico.model.Parametros;
import javax.ejb.Stateless;


@Stateless
public class ParametrosDao extends AbstractDao<Parametros>{
    
    public ParametrosDao() {
        super(Parametros.class);
    }
    
}
