/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.dao;

import br.com.panoramico.model.Planoconta;
import javax.ejb.Stateless;

@Stateless
public class PlanoContaDao extends AbstractDao<Planoconta>{
    
    public PlanoContaDao() {
        super(Planoconta.class);
    }
    
}
