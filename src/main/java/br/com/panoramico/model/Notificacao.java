/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "notificacao")
public class Notificacao implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnotificacao")
    private Integer idnotificacao;
    @Column(name = "assunto")
    private String assunto;
    @Size(max = 5)
    @Column(name = "hora")
    private String hora;
    @Column(name = "visto")
    private boolean visto;
    @JoinColumn(name = "usuario_envia", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioenvia;
    @JoinColumn(name = "usuario_recebe", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuariorecebe;
     @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;

    public Notificacao() {
    }
     
    
    public Integer getIdnotificacao() {
        return idnotificacao;
    }

    public void setIdnotificacao(Integer idnotificacao) {
        this.idnotificacao = idnotificacao;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }

    public Usuario getUsuarioenvia() {
        return usuarioenvia;
    }

    public void setUsuarioenvia(Usuario usuarioenvia) {
        this.usuarioenvia = usuarioenvia;
    }

    public Usuario getUsuariorecebe() {
        return usuariorecebe;
    }

    public void setUsuariorecebe(Usuario usuariorecebe) {
        this.usuariorecebe = usuariorecebe;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnotificacao != null ? idnotificacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificacao)) {
            return false;
        }
        Notificacao other = (Notificacao) object;
        if ((this.idnotificacao == null && other.idnotificacao != null) || (this.idnotificacao != null && !this.idnotificacao.equals(other.idnotificacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Notificacao[ idnotificacao=" + idnotificacao + " ]";
    }

}
 