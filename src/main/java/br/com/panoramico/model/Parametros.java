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

@Entity
@Table(name = "parametros")
public class Parametros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idparametros")
    private Integer idparametros;
    @Column(name = "planocontamensalidade")
    private Integer planocontamensalidade;
    @Column(name = "planocontaevento")
    private Integer planocontaevento;
    @Column(name = "planocontaavulso")
    private Integer planocontaavulso;
    @Column(name = "medico")
    private Integer medico;
    @Column(name = "cliente")
    private Integer cliente;
    @Column(name = "usuario")
    private Integer usuario;
    @Column(name = "banco")
    private Integer banco;

    public Parametros() {
    }

    public Integer getIdparametros() {
        return idparametros;
    }

    public void setIdparametros(Integer idparametros) {
        this.idparametros = idparametros;
    }

    public Integer getPlanocontamensalidade() {
        return planocontamensalidade;
    }

    public void setPlanocontamensalidade(Integer planocontamensalidade) {
        this.planocontamensalidade = planocontamensalidade;
    }

    public Integer getPlanocontaevento() {
        return planocontaevento;
    }

    public void setPlanocontaevento(Integer planocontaevento) {
        this.planocontaevento = planocontaevento;
    }

    public Integer getPlanocontaavulso() {
        return planocontaavulso;
    }

    public void setPlanocontaavulso(Integer planocontaavulso) {
        this.planocontaavulso = planocontaavulso;
    }

    public Integer getMedico() {
        return medico;
    }

    public void setMedico(Integer medico) {
        this.medico = medico;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Integer getBanco() {
        return banco;
    }

    public void setBanco(Integer banco) {
        this.banco = banco;
    }
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idparametros != null ? idparametros.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametros)) {
            return false;
        }
        Parametros other = (Parametros) object;
        if ((this.idparametros == null && other.idparametros != null) || (this.idparametros != null && !this.idparametros.equals(other.idparametros))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Parametros[ idparametros=" + idparametros + " ]";
    }

}
