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
@Table(name = "historicocobranca")
public class Historicocobranca implements Serializable{
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhistoricocobranca")
    private Integer idhistoricocobranca;
    @Size(max = 100)
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "contato")
    private String contato;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "cobranca_idcobranca", referencedColumnName = "idcobranca")
    @ManyToOne(optional = false)
    private Cobranca cobranca;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Historicocobranca() {
    }

    public Integer getIdhistoricocobranca() {
        return idhistoricocobranca;
    }

    public void setIdhistoricocobranca(Integer idhistoricocobranca) {
        this.idhistoricocobranca = idhistoricocobranca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhistoricocobranca != null ? idhistoricocobranca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contasreceber)) {
            return false;
        }
        Historicocobranca other = (Historicocobranca) object;
        if ((this.idhistoricocobranca == null && other.idhistoricocobranca != null) || (this.idhistoricocobranca != null && !this.idhistoricocobranca.equals(other.idhistoricocobranca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Historicocobranca[ idhistoricocobranca=" + idhistoricocobranca + " ]";
    }
}
