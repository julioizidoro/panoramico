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
import javax.validation.constraints.Size;

@Entity
@Table(name = "cobranca")
public class Cobranca implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcobranca")
    private Integer idcobranca;
    @Size(max = 100)
    @Column(name = "fone1")
    private String fone1;
    @Size(max = 100)
    @Column(name = "fone2")
    private String fone2;
    @Column(name = "email")
    private String email;

    public Cobranca() {
    }

    public Integer getIdcobranca() {
        return idcobranca;
    }

    public void setIdcobranca(Integer idcobranca) {
        this.idcobranca = idcobranca;
    }

    public String getFone1() {
        return fone1;
    }

    public void setFone1(String fone1) {
        this.fone1 = fone1;
    }

    public String getFone2() {
        return fone2;
    }

    public void setFone2(String fone2) {
        this.fone2 = fone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcobranca != null ? idcobranca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cobranca)) {
            return false;
        }
        Cobranca other = (Cobranca) object;
        if ((this.idcobranca == null && other.idcobranca != null) || (this.idcobranca != null && !this.idcobranca.equals(other.idcobranca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Cobranca[ idcobranca=" + idcobranca + " ]";
    }

}
