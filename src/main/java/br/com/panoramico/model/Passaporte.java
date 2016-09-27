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
@Table(name = "passaporte")
public class Passaporte implements Serializable{
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpassaporte")
    private Integer idpassaporte;
    @Column(name = "adultos")
    private int adultos;
    @Column(name = "datacompra")
    @Temporal(TemporalType.DATE)
    private Date datacompra;
    @Column(name = "dataacesso")
    @Temporal(TemporalType.DATE)
    private Date dataacesso;
    @Column(name = "formapagamento")
    private String formapagamento;
    @Column(name = "horaacesso")
    private String horaacesso;
    @Column(name = "localizador")
    private String localizador;
    @Column(name = "criancas")
    private int criancas;
     @Column(name = "valorpago")
    private float valorpago;
    @Column(name = "acessoadulto")
    private int acessoadulto;
    @Column(name = "acessocrianca")
    private int acessocrianca;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente;

    public Passaporte() {
    }

    public Integer getIdpassaporte() {
        return idpassaporte;
    }

    public void setIdpassaporte(Integer idpassaporte) {
        this.idpassaporte = idpassaporte;
    }

    public int getAdultos() {
        return adultos;
    }

    public void setAdultos(int adultos) {
        this.adultos = adultos;
    }

    public Date getDatacompra() {
        return datacompra;
    }

    public void setDatacompra(Date datacompra) {
        this.datacompra = datacompra;
    }

    public String getFormapagamento() {
        return formapagamento;
    }

    public void setFormapagamento(String formapagamento) {
        this.formapagamento = formapagamento;
    }

    public String getLocalizador() {
        return localizador;
    }

    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

    public int getCriancas() {
        return criancas;
    }

    public void setCriancas(int criancas) {
        this.criancas = criancas;
    }

    public int getAcessoadulto() {
        return acessoadulto;
    }

    public void setAcessoadulto(int acessoadulto) {
        this.acessoadulto = acessoadulto;
    }

    public int getAcessocrianca() {
        return acessocrianca;
    }

    public void setAcessocrianca(int acessocrianca) {
        this.acessocrianca = acessocrianca;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getDataacesso() {
        return dataacesso;
    }

    public void setDataacesso(Date dataacesso) {
        this.dataacesso = dataacesso;
    }

    public String getHoraacesso() {
        return horaacesso;
    }

    public void setHoraacesso(String horaacesso) {
        this.horaacesso = horaacesso;
    }

    public float getValorpago() {
        return valorpago;
    }

    public void setValorpago(float valorpago) {
        this.valorpago = valorpago;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpassaporte != null ? idpassaporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Passaporte)) {
            return false;
        }
        Passaporte other = (Passaporte) object;
        if ((this.idpassaporte == null && other.idpassaporte != null) || (this.idpassaporte != null && !this.idpassaporte.equals(other.idpassaporte))) {
            return false;
        }
        return true;
    }

  

    @Override
    public String toString() {
        return "br.com.panoramico.model.Passaporte[ idpassaporte=" + idpassaporte + " ]";
    }
    
}
