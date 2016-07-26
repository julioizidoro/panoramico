/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.dao;

import br.com.panoramico.model.Usuario;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Julio
 */

@Stateless
public class UsuarioDao extends  AbstractDao<Usuario>{

    @PersistenceContext
    private EntityManager em;
    
    public UsuarioDao() {
        super(Usuario.class);
    }
    
    
    public Usuario consultar(String sql) throws SQLException{
        em.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery(sql);
        Usuario usuario = null;
        if (q.getResultList().size()>0){
            usuario = (Usuario) q.getResultList().get(0);
        }
        em.getTransaction().commit();
        em.close();
        return usuario;
    }
    
}
