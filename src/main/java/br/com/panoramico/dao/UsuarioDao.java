package br.com.panoramico.dao;



import br.com.panoramico.model.Usuario;
import javax.ejb.Stateless;


@Stateless
public class UsuarioDao extends AbstractDao<Usuario> {
    
    public UsuarioDao() {
        super(Usuario.class);
    }
}
