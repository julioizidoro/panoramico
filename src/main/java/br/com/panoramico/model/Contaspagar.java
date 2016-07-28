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

/**
 *
 * @author Kamilla Rodrigues
 */

@Entity
@Table(name = "contaspagar")
public class Contaspagar implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontaspagar")
    private Integer idcontaspagar;
    @Column(name = "credor")
    private String credor;
    @Column(name = "numerodocumento")
    private String numerodocumento;
    @Column(name = "numeroparcela")
    private String numeroparcela;
    @Column(name = "datalancamento")
    @Temporal(TemporalType.DATE)
    private Date datalancamento;
    @Column(name = "datavencimento")
    @Temporal(TemporalType.DATE)
    private Date datavencimento;
    @Column(name = "valor")
    private Float valor;
    @Column(name = "formapagamento")
    private String formapagamento;
    @JoinColumn(name = "planoconta_idplanoconta", referencedColumnName = "idplanoconta")
    @ManyToOne(optional = false)
    private Planoconta planoconta;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Contaspagar() {
    }

    public Integer getIdcontaspagar() {
        return idcontaspagar;
    }

    public void setIdcontaspagar(Integer idcontaspagar) {
        this.idcontaspagar = idcontaspagar;
    }

    public String getCredor() {
        return credor;
    }

    public void setCredor(String credor) {
        this.credor = credor;
    }

    public String getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    public String getNumeroparcela() {
        return numeroparcela;
    }

    public void setNumeroparcela(String numeroparcela) {
        this.numeroparcela = numeroparcela;
    }

    public Date getDatalancamento() {
        return datalancamento;
    }

    public void setDatalancamento(Date datalancamento) {
        this.datalancamento = datalancamento;
    }

    public Date getDatavencimento() {
        return datavencimento;
    }

    public void setDatavencimento(Date datavencimento) {
        this.datavencimento = datavencimento;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getFormapagamento() {
        return formapagamento;
    }

    public void setFormapagamento(String formapagamento) {
        this.formapagamento = formapagamento;
    }

    public Planoconta getPlanoconta() {
        return planoconta;
    }

    public void setPlanoconta(Planoconta planoconta) {
        this.planoconta = planoconta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontaspagar != null ? idcontaspagar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contaspagar)) {
            return false;
        }
        Contaspagar other = (Contaspagar) object;
        if ((this.idcontaspagar == null && other.idcontaspagar != null) || (this.idcontaspagar != null && !this.idcontaspagar.equals(other.idcontaspagar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Contaspagar[ idcontaspagar=" + idcontaspagar + " ]";
    }
    
}
