/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.dao;

import br.com.panoramico.model.Eventoconvidados;
import javax.ejb.Stateless;

/**
 *
 * @author Kamilla Rodrigues
 */

@Stateless
public class EventoConvidadosDao extends AbstractDao<Eventoconvidados>{
    
    public EventoConvidadosDao() {
        super(EventoDao.class);
    }
    
}
