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

@Entity
@Table(name = "crcancelamento")
public class Crcancelamento implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcrcancelamento")
    private Integer idcrcancelamento;
    @Column(name = "formarecebimento")
    private String formarecebimento;
    @Column(name = "hora")
    private String hora;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "motivo")
    private String motivo;
    @JoinColumn(name = "contasreceber_idcontasreceber", referencedColumnName = "idcontasreceber")
    @ManyToOne(optional = false)
    private Contasreceber contasreceber;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Crcancelamento() {
    }

    public Integer getIdcrcancelamento() {
        return idcrcancelamento;
    }

    public void setIdcrcancelamento(Integer idcrcancelamento) {
        this.idcrcancelamento = idcrcancelamento;
    }

    public String getFormarecebimento() {
        return formarecebimento;
    }

    public void setFormarecebimento(String formarecebimento) {
        this.formarecebimento = formarecebimento;
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

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
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
        hash += (idcrcancelamento != null ? idcrcancelamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Crcancelamento)) {
            return false;
        }
        Crcancelamento other = (Crcancelamento) object;
        if ((this.idcrcancelamento == null && other.idcrcancelamento != null) || (this.idcrcancelamento != null && !this.idcrcancelamento.equals(other.idcrcancelamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Crcancelamento[ idcrcancelamento=" + idcrcancelamento + " ]";
    }
}
