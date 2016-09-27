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

@Entity
@Table(name = "passaportevalor")
public class Passaportevalor implements Serializable{
    
    
     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpassaportevalor")
    private Integer idpassaportevalor;
    @Column(name = "valoradulto")
    private float valoradulto;
    @Column(name = "valorcrianca")
    private float valorcrianca;
    @Column(name = "descriccao")
    private String descriccao;
    @Column(name = "situacao")
    private boolean situacao;
    @JoinColumn(name = "proprietario_idproprietario", referencedColumnName = "idproprietario")
    @ManyToOne(optional = false)
    private Proprietario proprietario;

    public Passaportevalor() {
    }

    public Integer getIdpassaportevalor() {
        return idpassaportevalor;
    }

    public void setIdpassaportevalor(Integer idpassaportevalor) {
        this.idpassaportevalor = idpassaportevalor;
    }

    public float getValoradulto() {
        return valoradulto;
    }

    public void setValoradulto(float valoradulto) {
        this.valoradulto = valoradulto;
    }

    public float getValorcrianca() {
        return valorcrianca;
    }

    public void setValorcrianca(float valorcrianca) {
        this.valorcrianca = valorcrianca;
    }

    public String getDescriccao() {
        return descriccao;
    }

    public void setDescriccao(String descriccao) {
        this.descriccao = descriccao;
    }

    
    
    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }
    
    
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpassaportevalor != null ? idpassaportevalor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Passaportevalor)) {
            return false;
        }
        Passaportevalor other = (Passaportevalor) object;
        if ((this.idpassaportevalor == null && other.idpassaportevalor != null) || (this.idpassaportevalor != null && !this.idpassaportevalor.equals(other.idpassaportevalor))) {
            return false;
        }
        return true;
    }

  

    @Override
    public String toString() {
        return "br.com.panoramico.model.Passaportevalor[ idpassaportevalor=" + idpassaportevalor + " ]";
    }
    
}
