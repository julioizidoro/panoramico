/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.dao;

import br.com.panoramico.model.Controleacesso;
import javax.ejb.Stateless;

@Stateless
public class ControleAcessoDao extends AbstractDao<Controleacesso>{
    
    public ControleAcessoDao() {
        super(Controleacesso.class);
    }
    
}