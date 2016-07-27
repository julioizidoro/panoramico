/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.model;

import java.io.Serializable;
import java.util.Date;
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

/**
 *
 * @author Kamilla Rodrigues
 */

@Entity
@Table(name = "eventocancelamento")
public class Eventocancelamento implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ideventocancelamento")
    private Integer ideventocancelamento;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "hora")
    private String hora;
    @Column(name = "motivo")
    private String motivo;
    @JoinColumn(name = "evento_idevento", referencedColumnName = "idevento")
    @ManyToOne(optional = false)
    private Evento evento;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Eventocancelamento() {
    }

    public Integer getIdeventocancelamento() {
        return ideventocancelamento;
    }

    public void setIdeventocancelamento(Integer ideventocancelamento) {
        this.ideventocancelamento = ideventocancelamento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ideventocancelamento != null ? ideventocancelamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eventocancelamento)) {
            return false;
        }
        Eventocancelamento other = (Eventocancelamento) object;
        if ((this.ideventocancelamento == null && other.ideventocancelamento != null) || (this.ideventocancelamento != null && !this.ideventocancelamento.equals(other.ideventocancelamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Eventocancelamento[ ideventocancelamento=" + ideventocancelamento + " ]";
    }
    
}
