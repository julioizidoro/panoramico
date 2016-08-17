package br.com.panoramico.dao;

import br.com.panoramico.model.Notificacao;
import javax.ejb.Stateless;

@Stateless
public class NotificacaoDao extends AbstractDao<Notificacao>{
    
    public NotificacaoDao() {
        super(Notificacao.class);
    }
    
}
