/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idempresa")
    private Integer idempresa;
    @Size(max = 45)
    @Column(name = "razaosocial")
    private String razaosocial;
    @Column(name = "nomefantasia")
    private String nomefantasia;
    @Column(name = "tipologradouro")
    private String tipologradouro;
    @Column(name = "logradouro")
    private String logradouro;
    @Column(name = "complemento")
    private String complemento;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "estado")
    private String estado;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "pessoacontato")
    private String pessoacontato;
    @Column(name = "email")
    private String email;
    @Column(name = "cep")
    private String cep;
    @Column(name = "numero")
    private String numero;
    @Column(name = "ie")
    private String ie;
    @Column(name = "valormensalidade")
    private Float valormensalidade;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private List<Associadoempresa> associadoempresaList;
    @JoinColumn(name = "plano_idplano", referencedColumnName = "idplano")
    @ManyToOne(optional = false)
    private Plano plano;

    public Empresa() {
    }

    public Empresa(Integer idempresa) {
        this.idempresa = idempresa;
    }

    public Integer getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Integer idempresa) {
        this.idempresa = idempresa;
    }

    public String getRazaosocial() {
        return razaosocial;
    }

    public void setRazaosocial(String razaosocial) {
        this.razaosocial = razaosocial;
    }

    public String getNomefantasia() {
        return nomefantasia;
    }

    public void setNomefantasia(String nomefantasia) {
        this.nomefantasia = nomefantasia;
    }

    public String getTipologradouro() {
        return tipologradouro;
    }

    public void setTipologradouro(String tipologradouro) {
        this.tipologradouro = tipologradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPessoacontato() {
        return pessoacontato;
    }

    public void setPessoacontato(String pessoacontato) {
        this.pessoacontato = pessoacontato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Float getValormensalidade() {
        return valormensalidade;
    }

    public void setValormensalidade(Float valormensalidade) {
        this.valormensalidade = valormensalidade;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public List<Associadoempresa> getAssociadoempresaList() {
        return associadoempresaList;
    }

    public void setAssociadoempresaList(List<Associadoempresa> associadoempresaList) {
        this.associadoempresaList = associadoempresaList;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idempresa != null ? idempresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.idempresa == null && other.idempresa != null) || (this.idempresa != null && !this.idempresa.equals(other.idempresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Empresa[ idempresa=" + idempresa + " ]";
    }
    
}
