/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "exameassociado")
public class Exameassociado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idexameassociado")
    private Integer idexameassociado;
    @JoinColumn(name = "exame_idexame", referencedColumnName = "idexame")
    @ManyToOne(optional = false)
    private Exame exame;
    @JoinColumn(name = "associado_idassociado", referencedColumnName = "idassociado")
    @ManyToOne(optional = false)
    private Associado associado;

    public Exameassociado() {
    }

    public Exameassociado(Integer idexameassociado) {
        this.idexameassociado = idexameassociado;
    }

    public Integer getIdexameassociado() {
        return idexameassociado;
    }

    public void setIdexameassociado(Integer idexameassociado) {
        this.idexameassociado = idexameassociado;
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idexameassociado != null ? idexameassociado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exameassociado)) {
            return false;
        }
        Exameassociado other = (Exameassociado) object;
        if ((this.idexameassociado == null && other.idexameassociado != null) || (this.idexameassociado != null && !this.idexameassociado.equals(other.idexameassociado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Exameassociado[ idexameassociado=" + idexameassociado + " ]";
    }
    
}
