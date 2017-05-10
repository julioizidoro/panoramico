/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.dao;

import br.com.panoramico.model.Remessacontas;
import javax.ejb.Stateless;

/**
 *
 * @author Anderson
 */

@Stateless
public class RemessaContasDao extends AbstractDao<Remessacontas>{
    
    public RemessaContasDao() {
        super(Remessacontas.class);
    }
    
}
