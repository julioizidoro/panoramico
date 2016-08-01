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
@Table(name = "examedependente")
public class Examedependente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idexamedependente")
    private Integer idexamedependente;
    @JoinColumn(name = "exame_idexame", referencedColumnName = "idexame")
    @ManyToOne(optional = false)
    private Exame exame;
    @JoinColumn(name = "dependente_iddependente", referencedColumnName = "iddependente")
    @ManyToOne(optional = false)
    private Dependente dependente;

    public Examedependente() {
    }

    public Examedependente(Integer idexamedependente) {
        this.idexamedependente = idexamedependente;
    }

    public Integer getIdexamedependente() {
        return idexamedependente;
    }

    public void setIdexamedependente(Integer idexamedependente) {
        this.idexamedependente = idexamedependente;
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public Dependente getDependente() {
        return dependente;
    }

    public void setDependente(Dependente dependente) {
        this.dependente = dependente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idexamedependente != null ? idexamedependente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Examedependente)) {
            return false;
        }
        Examedependente other = (Examedependente) object;
        if ((this.idexamedependente == null && other.idexamedependente != null) || (this.idexamedependente != null && !this.idexamedependente.equals(other.idexamedependente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Examedependente[ idexamedependente=" + idexamedependente + " ]";
    }
    
}
