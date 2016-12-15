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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "banco")
@NamedQueries({
    @NamedQuery(name = "Banco.findAll", query = "SELECT b FROM Banco b")})
public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbanco")
    private Integer idbanco;
    @Size(max = 3)
    @Column(name = "numero")
    private String numero;
    @Size(max = 50)
    @Column(name = "nome")
    private String nome;
    @Size(max = 10)
    @Column(name = "agencia")
    private String agencia;
    @Size(max = 20)
    @Column(name = "conta")
    private String conta;
    @Size(max = 30)
    @Column(name = "chave")
    private String chave;
    @Size(max = 200)
    @Column(name = "senha")
    private String senha;
    @Size(max = 50)
    @Column(name = "gerente")
    private String gerente;
    @Size(max = 100)
    @Column(name = "emailgerente")
    private String emailgerente;
    @Size(max = 1)
    @Column(name = "digitoagencia")
    private String digitoagencia;
    @Size(max = 1)
    @Column(name = "digitoconta")
    private String digitoconta;
    @Size(max = 3)
    @Column(name = "carteira")
    private String carteira;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorjuros")
    private Float valorjuros;
    @Column(name = "valormulta")
    private Float valormulta;
    @JoinColumn(name = "proprietario_idproprietario", referencedColumnName = "idproprietario")
    @ManyToOne(optional = false)
    private Proprietario proprietario;
    @Column(name = "observacao1")
    private String observacao1;
    @Column(name = "observacao2")
    private String observacao2;
    @Column(name = "observacao3")
    private String observacao3;
    @Column(name = "observacao4")
    private String observacao4;

    public Banco() {
    }

    public Banco(Integer idbanco) {
        this.idbanco = idbanco;
    }

    public Integer getIdbanco() {
        return idbanco;
    }

    public void setIdbanco(Integer idbanco) {
        this.idbanco = idbanco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

    public String getEmailgerente() {
        return emailgerente;
    }

    public void setEmailgerente(String emailgerente) {
        this.emailgerente = emailgerente;
    }

    public String getDigitoagencia() {
        return digitoagencia;
    }

    public void setDigitoagencia(String digitoagencia) {
        this.digitoagencia = digitoagencia;
    }

    public String getDigitoconta() {
        return digitoconta;
    }

    public void setDigitoconta(String digitoconta) {
        this.digitoconta = digitoconta;
    }

    public String getCarteira() {
        return carteira;
    }

    public void setCarteira(String carteira) {
        this.carteira = carteira;
    }

    public Float getValorjuros() {
        return valorjuros;
    }

    public void setValorjuros(Float valorjuros) {
        this.valorjuros = valorjuros;
    }

    public Float getValormulta() {
        return valormulta;
    }

    public void setValormulta(Float valormulta) {
        this.valormulta = valormulta;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public String getObservacao1() {
        return observacao1;
    }

    public void setObservacao1(String observacao1) {
        this.observacao1 = observacao1;
    }

    public String getObservacao2() {
        return observacao2;
    }

    public void setObservacao2(String observacao2) {
        this.observacao2 = observacao2;
    }

    public String getObservacao3() {
        return observacao3;
    }

    public void setObservacao3(String observacao3) {
        this.observacao3 = observacao3;
    }

    public String getObservacao4() {
        return observacao4;
    }

    public void setObservacao4(String observacao4) {
        this.observacao4 = observacao4;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbanco != null ? idbanco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Banco)) {
            return false;
        }
        Banco other = (Banco) object;
        if ((this.idbanco == null && other.idbanco != null) || (this.idbanco != null && !this.idbanco.equals(other.idbanco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Banco[ idbanco=" + idbanco + " ]";
    }
    
}
