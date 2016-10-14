/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.dao;

import br.com.panoramico.model.Controleacesso;
import java.sql.SQLException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ControleAcessoDao extends AbstractDao<Controleacesso>{
    
    @PersistenceContext
    private EntityManager manager;
    
    public ControleAcessoDao() {
        super(Controleacesso.class);
    }
    
    
    public Integer numeroFrequencia(String sql) throws SQLException{
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        Integer frequencia = 0;
        if (q.getResultList().size()>0){
           frequencia  = (Integer) q.getResultList().get(0);
        }
        manager.getTransaction().commit();
        return frequencia;
    }
}
