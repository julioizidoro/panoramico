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
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "tipoenvento")
public class Tipoenvento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipoenvento")
    private Integer idtipoenvento;
    @Size(max = 100)
    @Column(name = "descricao")
    private String descricao;

    public Tipoenvento() {
    }

    public Tipoenvento(Integer idtipoenvento) {
        this.idtipoenvento = idtipoenvento;
    }

    public Integer getIdtipoenvento() {
        return idtipoenvento;
    }

    public void setIdtipoenvento(Integer idtipoenvento) {
        this.idtipoenvento = idtipoenvento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoenvento != null ? idtipoenvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoenvento)) {
            return false;
        }
        Tipoenvento other = (Tipoenvento) object;
        if ((this.idtipoenvento == null && other.idtipoenvento != null) || (this.idtipoenvento != null && !this.idtipoenvento.equals(other.idtipoenvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Tipoenvento[ idtipoenvento=" + idtipoenvento + " ]";
    }
    
}
