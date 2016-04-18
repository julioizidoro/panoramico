/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.dao;

import br.com.panoramico.model.Empresa;
import javax.ejb.Stateless;

/**
 *
 * @author Julio
 */

@Stateless
public class EmpresaDao extends AbstractDao<Empresa>{

    public EmpresaDao() {
        super(Empresa.class);
    }
    
    
}
