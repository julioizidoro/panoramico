/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Anderson
 */
@Entity
@Table(name = "retornoarquivo")
public class Retornoarquivo implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idretornoarquivo")
    private Integer idretornoarquivo;
    @Column(name = "dataretorno")
    @Temporal(TemporalType.DATE)
    private Date dataretorno;
    @Size(max = 15)
    @Column(name = "nomearquivo")
    private String nomeaquivo;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "retornoarquivo")
    private List<Retornocontas> retornocontasList;

    public Retornoarquivo() {
    }

    public Retornoarquivo(Integer idretornoarquivo) {
        this.idretornoarquivo = idretornoarquivo;
    }

    public Integer getIdretornoarquivo() {
        return idretornoarquivo;
    }

    public void setIdretornoarquivo(Integer idretornoarquivo) {
        this.idretornoarquivo = idretornoarquivo;
    }

    public Date getDataretorno() {
        return dataretorno;
    }

    public void setDataretorno(Date dataretorno) {
        this.dataretorno = dataretorno;
    }

    public String getNomeaquivo() {
        return nomeaquivo;
    }

    public void setNomeaquivo(String nomeaquivo) {
        this.nomeaquivo = nomeaquivo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Retornocontas> getRetornocontasList() {
        return retornocontasList;
    }

    public void setRetornocontasList(List<Retornocontas> retornocontasList) {
        this.retornocontasList = retornocontasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idretornoarquivo != null ? idretornoarquivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Retornoarquivo)) {
            return false;
        }
        Retornoarquivo other = (Retornoarquivo) object;
        if ((this.idretornoarquivo == null && other.idretornoarquivo != null) || (this.idretornoarquivo != null && !this.idretornoarquivo.equals(other.idretornoarquivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Retornoarquivo[ idretornoarquivo=" + idretornoarquivo + " ]";
    }
}
