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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "associado")
public class Associado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idassociado")
    private Integer idassociado;
    @Size(max = 30)
    @Column(name = "tipologradouro")
    private String tipologradouro;
    @Size(max = 100)
    @Column(name = "logradouro")
    private String logradouro;
    @Size(max = 30)
    @Column(name = "numero")
    private String numero;
    @Size(max = 50)
    @Column(name = "complemento")
    private String complemento;
    @Size(max = 50)
    @Column(name = "bairro")
    private String bairro;
    @Size(max = 10)
    @Column(name = "cep")
    private String cep;
    @Size(max = 50)
    @Column(name = "cidade")
    private String cidade;
    @Size(max = 2)
    @Column(name = "estado")
    private String estado;
    @Size(max = 20)
    @Column(name = "matricula")
    private String matricula;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "descotomensalidade")
    private Float descotomensalidade;
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "dataassociacao")
    private Date dataassociacao;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "ano")
    private Integer ano;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @OneToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "plano_idplano", referencedColumnName = "idplano")
    @ManyToOne(optional = false)
    private Plano plano;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "associado")
    private List<Dependente> dependenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "associado")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Associadoempresa> associadoempresaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "associado")
    private List<Exameassociado> exameassociadoList;

    public Associado() {
    }

    public Associado(Integer idassociado) {
        this.idassociado = idassociado;
    }

    public Integer getIdassociado() {
        return idassociado;
    }

    public void setIdassociado(Integer idassociado) {
        this.idassociado = idassociado;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public Float getDescotomensalidade() {
        return descotomensalidade;
    }

    public void setDescotomensalidade(Float descotomensalidade) {
        this.descotomensalidade = descotomensalidade;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public List<Dependente> getDependenteList() {
        return dependenteList;
    }

    public void setDependenteList(List<Dependente> dependenteList) {
        this.dependenteList = dependenteList;
    }

    public List<Associadoempresa> getAssociadoempresaList() {
        return associadoempresaList;
    }

    public void setAssociadoempresaList(List<Associadoempresa> associadoempresaList) {
        this.associadoempresaList = associadoempresaList;
    }

    public List<Exameassociado> getExameassociadoList() {
        return exameassociadoList;
    }

    public void setExameassociadoList(List<Exameassociado> exameassociadoList) {
        this.exameassociadoList = exameassociadoList;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Date getDataassociacao() {
        return dataassociacao;
    }

    public void setDataassociacao(Date dataassociacao) {
        this.dataassociacao = dataassociacao;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idassociado != null ? idassociado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Associado)) {
            return false;
        }
        Associado other = (Associado) object;
        if ((this.idassociado == null && other.idassociado != null) || (this.idassociado != null && !this.idassociado.equals(other.idassociado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Associado[ idassociado=" + idassociado + " ]";
    }

}
