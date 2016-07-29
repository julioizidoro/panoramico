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



@Entity
@Table(name = "planoconta")
public class Planoconta implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idplanoconta")
    private Integer idplanoconta;
    @Size(max = 45)
    @Column(name = "descricao")
    private String descricao;

    public Planoconta() {
    }

    public Integer getIdplanoconta() {
        return idplanoconta;
    }

    public void setIdplanoconta(Integer idplanoconta) {
        this.idplanoconta = idplanoconta;
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
        hash += (idplanoconta != null ? idplanoconta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planoconta)) {
            return false;
        }
        Planoconta other = (Planoconta) object;
        if ((this.idplanoconta == null && other.idplanoconta != null) || (this.idplanoconta != null && !this.idplanoconta.equals(other.idplanoconta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Planoconta[ idplanoconta=" + idplanoconta + " ]";
    }
}
