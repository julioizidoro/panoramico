/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.model;

import java.io.Serializable;
import java.util.Date;
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

/**
 *
 * @author Kamilla Rodrigues
 */
@Entity
@Table(name = "controleacesso")
public class Controleacesso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcontroleacesso")
    private Integer idcontroleacesso;
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data; 
    @Column(name = "hora")
    private String hora;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "iddependente")
    private int iddependente;
    @JoinColumn(name = "associado_idassociado", referencedColumnName = "idassociado")
    @ManyToOne(optional = false)
    private Associado associado;

    public Controleacesso() {
    }

    public Integer getIdcontroleacesso() {
        return idcontroleacesso;
    }

    public void setIdcontroleacesso(Integer idcontroleacesso) {
        this.idcontroleacesso = idcontroleacesso;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIddependente() {
        return iddependente;
    }

    public void setIddependente(int iddependente) {
        this.iddependente = iddependente;
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
        hash += (idcontroleacesso != null ? idcontroleacesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Controleacesso)) {
            return false;
        }
        Controleacesso other = (Controleacesso) object;
        if ((this.idcontroleacesso == null && other.idcontroleacesso != null) || (this.idcontroleacesso != null && !this.idcontroleacesso.equals(other.idcontroleacesso))) {
            return false;
        }
        return true;
    }

  

    @Override
    public String toString() {
        return "br.com.panoramico.model.Controleacesso[ idcontroleacesso=" + idcontroleacesso + " ]";
    }
    
}
