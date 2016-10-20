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

@Entity
@Table(name = "cobrancasparcelas")
public class Cobrancasparcelas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcobrancasparcelas")
    private Integer idcobrancasparcelas;
    @JoinColumn(name = "cobranca_idcobranca", referencedColumnName = "idcobranca")
    @ManyToOne(optional = false)
    private Cobranca cobranca;
    @JoinColumn(name = "contasreceber_idcontasreceber", referencedColumnName = "idcontasreceber")
    @ManyToOne(optional = false)
    private Contasreceber contasreceber;

    public Cobrancasparcelas() {
    }

    public Integer getIdcobrancasparcelas() {
        return idcobrancasparcelas;
    }

    public void setIdcobrancasparcelas(Integer idcobrancasparcelas) {
        this.idcobrancasparcelas = idcobrancasparcelas;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcobrancasparcelas != null ? idcobrancasparcelas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contasreceber)) {
            return false;
        }
        Cobrancasparcelas other = (Cobrancasparcelas) object;
        if ((this.idcobrancasparcelas == null && other.idcobrancasparcelas != null) || (this.idcobrancasparcelas != null && !this.idcobrancasparcelas.equals(other.idcobrancasparcelas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Cobrancasparcelas[ idcobrancasparcelas=" + idcobrancasparcelas + " ]";
    }
}
