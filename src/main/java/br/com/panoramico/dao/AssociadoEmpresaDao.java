/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.dao;

import br.com.panoramico.model.Associadoempresa;
import java.sql.SQLException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.JOptionPane;

/**
 *
 * @author Julio
 */

@Stateless
public class AssociadoEmpresaDao extends AbstractDao<Associadoempresa>{
    
    @PersistenceContext
    private EntityManager em;

    public AssociadoEmpresaDao() {
        super(Associadoempresa.class);
    }
    
    
    public Associadoempresa consultar(String sql)throws SQLException{
        Query q = em.createQuery(sql);
        Associadoempresa associadoempresa = null;
        if (q.getResultList().size()>0){
            associadoempresa = (Associadoempresa) q.getResultList().get(0);
        }
        return associadoempresa;
    } 
    
    
}
