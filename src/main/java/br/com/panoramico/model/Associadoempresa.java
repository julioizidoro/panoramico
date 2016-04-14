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
@Table(name = "associadoempresa")
public class Associadoempresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idassociadoempresa")
    private Integer idassociadoempresa;
    @JoinColumn(name = "empresa_idempresa", referencedColumnName = "idempresa")
    @ManyToOne(optional = false)
    private Empresa empresa;
    @JoinColumn(name = "associado_idassociado", referencedColumnName = "idassociado")
    @ManyToOne(optional = false)
    private Associado associado;

    public Associadoempresa() {
    }

    public Associadoempresa(Integer idassociadoempresa) {
        this.idassociadoempresa = idassociadoempresa;
    }

    public Integer getIdassociadoempresa() {
        return idassociadoempresa;
    }

    public void setIdassociadoempresa(Integer idassociadoempresa) {
        this.idassociadoempresa = idassociadoempresa;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
        hash += (idassociadoempresa != null ? idassociadoempresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Associadoempresa)) {
            return false;
        }
        Associadoempresa other = (Associadoempresa) object;
        if ((this.idassociadoempresa == null && other.idassociadoempresa != null) || (this.idassociadoempresa != null && !this.idassociadoempresa.equals(other.idassociadoempresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Associadoempresa[ idassociadoempresa=" + idassociadoempresa + " ]";
    }
    
}
