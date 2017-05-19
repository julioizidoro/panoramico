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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Anderson
 */
@Entity
@Table(name = "remessacontas")
public class Remessacontas implements Serializable{
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idremessacontas")
    private Integer idremessacontas;
    @Size(max = 3)
    @Column(name = "codigoocorrencia")
    private String codigoocorrencia;
    @JoinColumn(name = "remessaarquivo_idremessaarquivo", referencedColumnName = "idremessaarquivo")
    @ManyToOne(optional = false)
    private Remessaarquivo remessaarquivo;
    @JoinColumn(name = "contasreceber_idcontasreceber", referencedColumnName = "idcontasreceber")
    @ManyToOne(optional = false)
    private Contasreceber contasreceber;
    @Transient
    private boolean selecionado;

    public Remessacontas() {
    }

    public Remessacontas(Integer idremessacontas) {
        this.idremessacontas = idremessacontas;
    }

    public Integer getIdremessacontas() {
        return idremessacontas;
    }

    public void setIdremessacontas(Integer idremessacontas) {
        this.idremessacontas = idremessacontas;
    }

    

    public String getCodigoocorrencia() {
		return codigoocorrencia;
	}

	public void setCodigoocorrencia(String codigoocorrencia) {
		this.codigoocorrencia = codigoocorrencia;
	}

	public Remessaarquivo getRemessaarquivo() {
        return remessaarquivo;
    }

    public void setRemessaarquivo(Remessaarquivo remessaarquivo) {
        this.remessaarquivo = remessaarquivo;
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idremessacontas != null ? idremessacontas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Remessacontas)) {
            return false;
        }
        Remessacontas other = (Remessacontas) object;
        if ((this.idremessacontas == null && other.idremessacontas != null) || (this.idremessacontas != null && !this.idremessacontas.equals(other.idremessacontas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Remessacontas[ idremessacontas=" + idremessacontas + " ]";
    }
}
