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
@Table(name = "recebimento")
public class Recebimento implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrecebimento")
    private Integer idrecebimento;
    @Column(name = "formarecebimento")
    private String formarecebimento;
    @Column(name = "juros")
    private Float juros;
    @Column(name = "datarecebimento")
    @Temporal(TemporalType.DATE)
    private Date datarecebimento;
    @Column(name = "desagio")
    private Float desagio;
    @JoinColumn(name = "contasreceber_idcontasreceber", referencedColumnName = "idcontasreceber")
    @ManyToOne(optional = false)
    private Contasreceber contasreceber;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Recebimento() {
    }

    public Integer getIdrecebimento() {
        return idrecebimento;
    }

    public void setIdrecebimento(Integer idrecebimento) {
        this.idrecebimento = idrecebimento;
    }

    public String getFormarecebimento() {
        return formarecebimento;
    }

    public void setFormarecebimento(String formarecebimento) {
        this.formarecebimento = formarecebimento;
    }

    public Float getJuros() {
        return juros;
    }

    public void setJuros(Float juros) {
        this.juros = juros;
    }

    public Date getDatarecebimento() {
        return datarecebimento;
    }

    public void setDatarecebimento(Date datarecebimento) {
        this.datarecebimento = datarecebimento;
    }

    public Float getDesagio() {
        return desagio;
    }

    public void setDesagio(Float desagio) {
        this.desagio = desagio;
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
        hash += (idrecebimento != null ? idrecebimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recebimento)) {
            return false;
        }
        Recebimento other = (Recebimento) object;
        if ((this.idrecebimento == null && other.idrecebimento != null) || (this.idrecebimento != null && !this.idrecebimento.equals(other.idrecebimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Recebimento[ idrecebimento=" + idrecebimento + " ]";
    }
}
