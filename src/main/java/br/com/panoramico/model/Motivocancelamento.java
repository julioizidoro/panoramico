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

/**
 *
 * @author Anderson
 */


@Entity
@Table(name = "motivocancelamento")
public class Motivocancelamento implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmotivocancelamento")
    private Integer idmotivocancelamento;
    @Column(name = "descricao")
    private String descricao;

    public Motivocancelamento() {
    }

    public Integer getIdmotivocancelamento() {
        return idmotivocancelamento;
    }

    public void setIdmotivocancelamento(Integer idmotivocancelamento) {
        this.idmotivocancelamento = idmotivocancelamento;
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
        hash += (idmotivocancelamento != null ? idmotivocancelamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Motivocancelamento other = (Motivocancelamento) object;
        if ((this.idmotivocancelamento == null && other.idmotivocancelamento != null) || (this.idmotivocancelamento != null && !this.idmotivocancelamento.equals(other.idmotivocancelamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Motivocancelamento[ idmotivocancelamento=" + idmotivocancelamento + " ]";
    }
    
    
}
