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
 * @author Wolverine
 */
@Entity
@Table(name = "dependente")
public class Dependente implements Serializable {

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddependente")
    private Integer iddependente;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Size(max = 14)
    @Column(name = "cep")
    private String cep;
    @Column(name = "datanascimento")
    @Temporal(TemporalType.DATE)
    private Date datanascimento;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @Size(max = 20)
    @Column(name = "telefone")
    private String telefone;
    @JoinColumn(name = "associado_idassociado", referencedColumnName = "idassociado")
    @ManyToOne(optional = false)
    private Associado associado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dependente")
    private List<Examedependente> examedependenteList;

    public Dependente() {
    }

    public Dependente(Integer iddependente) {
        this.iddependente = iddependente;
    }

    public Integer getIddependente() {
        return iddependente;
    }

    public void setIddependente(Integer iddependente) {
        this.iddependente = iddependente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddependente != null ? iddependente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dependente)) {
            return false;
        }
        Dependente other = (Dependente) object;
        if ((this.iddependente == null && other.iddependente != null) || (this.iddependente != null && !this.iddependente.equals(other.iddependente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Dependente[ iddependente=" + iddependente + " ]";
    }

    public List<Examedependente> getExamedependenteList() {
        return examedependenteList;
    }

    public void setExamedependenteList(List<Examedependente> examedependenteList) {
        this.examedependenteList = examedependenteList;
    }
    
}
