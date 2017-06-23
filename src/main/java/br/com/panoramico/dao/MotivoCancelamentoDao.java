/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.dao;

import br.com.panoramico.model.Motivocancelamento;
import javax.ejb.Stateless;

/**
 *
 * @author Anderson
 */

@Stateless
public class MotivoCancelamentoDao extends AbstractDao<Motivocancelamento>{
    
    public MotivoCancelamentoDao() {
        super(Motivocancelamento.class);
    }
    
}
 