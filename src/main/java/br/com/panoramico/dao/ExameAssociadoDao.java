/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.dao;

import br.com.panoramico.model.Exameassociado;
import javax.ejb.Stateless;

@Stateless
public class ExameAssociadoDao extends AbstractDao<Exameassociado>{
    
    public ExameAssociadoDao() {
        super(Exameassociado.class);
    }
    
}
