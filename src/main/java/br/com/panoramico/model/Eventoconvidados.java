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
import javax.persistence.Table;

/**
 *
 * @author Kamilla Rodrigues
 */

@Entity
@Table(name = "eventoconvidados")
public class Eventoconvidados implements Serializable{
     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ideventoconvidados")
    private Integer ideventoconvidados;
    @Column(name = "nome")
    private String nome;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "email")
    private String email;
    @Column(name = "acompanhante")
    private Integer acompanhante;
    @Column(name = "situacao")
    private String situacao;
    @JoinColumn(name = "evento_idevento", referencedColumnName = "idevento")
    @ManyToOne(optional = false)
    private Evento evento;

    public Eventoconvidados() {
    }

    public Integer getIdeventoconvidados() {
        return ideventoconvidados;
    }

    public void setIdeventoconvidados(Integer ideventoconvidados) {
        this.ideventoconvidados = ideventoconvidados;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAcompanhante() {
        return acompanhante;
    }

    public void setAcompanhante(Integer acompanhante) {
        this.acompanhante = acompanhante;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ideventoconvidados != null ? ideventoconvidados.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eventoconvidados)) {
            return false;
        }
        Eventoconvidados other = (Eventoconvidados) object;
        if ((this.ideventoconvidados == null && other.ideventoconvidados != null) || (this.ideventoconvidados != null && !this.ideventoconvidados.equals(other.ideventoconvidados))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Eventoconvidados[ ideventoconvidados=" + ideventoconvidados + " ]";
    }
    
}
