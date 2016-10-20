/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.dao;

import br.com.panoramico.model.Cobranca;
import javax.ejb.Stateless;

@Stateless
public class CobrancaDao extends AbstractDao<Cobranca>{
    
    public CobrancaDao() {
        super(Cobranca.class);
    }
    
}
