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

/**
 *
 * @author Anderson
 */

@Entity
@Table(name = "ccancelamento")
public class Ccancelamento implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idccancelamento")
    private Integer idccancelamento;
    @Column(name = "hora")
    private String hora;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "motivo")
    private String motivo;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "motivocancelamento_idmotivocancelamento", referencedColumnName = "idmotivocancelamento")
    @ManyToOne(optional = false)
    private Motivocancelamento motivocancelamento;

    public Ccancelamento() {
    }

    public Integer getIdccancelamento() {
        return idccancelamento;
    }

    public void setIdccancelamento(Integer idccancelamento) {
        this.idccancelamento = idccancelamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    

    


    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Motivocancelamento getMotivocancelamento() {
        return motivocancelamento;
    }

    public void setMotivocancelamento(Motivocancelamento motivocancelamento) {
        this.motivocancelamento = motivocancelamento;
    }
    
    
    
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (idccancelamento != null ? idccancelamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Crcancelamento)) {
            return false;
        }
        Ccancelamento other = (Ccancelamento) object;
        if ((this.idccancelamento == null && other.idccancelamento != null) || (this.idccancelamento != null && !this.idccancelamento.equals(other.idccancelamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Ccancelamento[ idccancelamento=" + idccancelamento + " ]";
    }
    
}
