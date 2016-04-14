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
@Table(name = "ambiente")
public class Ambiente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idambiente")
    private Integer idambiente;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Column(name = "capacidade")
    private Integer capacidade;

    public Ambiente() {
    }

    public Ambiente(Integer idambiente) {
        this.idambiente = idambiente;
    }

    public Integer getIdambiente() {
        return idambiente;
    }

    public void setIdambiente(Integer idambiente) {
        this.idambiente = idambiente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idambiente != null ? idambiente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ambiente)) {
            return false;
        }
        Ambiente other = (Ambiente) object;
        if ((this.idambiente == null && other.idambiente != null) || (this.idambiente != null && !this.idambiente.equals(other.idambiente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Ambiente[ idambiente=" + idambiente + " ]";
    }
    
}
