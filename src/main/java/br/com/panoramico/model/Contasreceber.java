/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.model;

import java.io.Serializable;
import java.util.Date;
import javax.inject.Named;
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
import javax.persistence.Transient;


@Entity
@Table(name = "contasreceber")
public class Contasreceber implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontasreceber")
    private Integer idcontasreceber;
    @Column(name = "tipopagamento")
    private String tipopagamento;
    @Column(name = "numerodocumento")
    private String numerodocumento;
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "numeroparcela")
    private String numeroparcela;
    @Column(name = "datalancamento")
    @Temporal(TemporalType.DATE)
    private Date datalancamento;
    @Column(name = "datavencimento")
    @Temporal(TemporalType.DATE)
    private Date datavencimento;
    @Column(name = "valorconta")
    private Float valorconta;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "planoconta_idplanoconta", referencedColumnName = "idplanoconta")
    @ManyToOne(optional = false)
    private Planoconta planoconta;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @Column(name = "situacaoboleto")
    private String situacaoboleto;
    @Column(name = "enviado")
    private boolean enviado;
    @Transient
    private boolean selecionado;
    @Column(name = "nossonumero")
    private String nossonumero;
    @Column(name = "idempresa")
    private Integer idempresa;

    public Contasreceber() {
        situacaoboleto = "Não";
    }   

    public Integer getIdcontasreceber() {
        return idcontasreceber;
    }

    public void setIdcontasreceber(Integer idcontasreceber) {
        this.idcontasreceber = idcontasreceber;
    }

    public String getTipopagamento() {
        return tipopagamento;
    }

    public void setTipopagamento(String tipopagamento) {
        this.tipopagamento = tipopagamento;
    }

    public String getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    public String getNumeroparcela() {
        return numeroparcela;
    }

    public void setNumeroparcela(String numeroparcela) {
        this.numeroparcela = numeroparcela;
    }

    public Date getDatalancamento() {
        return datalancamento;
    }

    public void setDatalancamento(Date datalancamento) {
        this.datalancamento = datalancamento;
    }

    public Float getValorconta() {
        return valorconta;
    }

    public void setValorconta(Float valorconta) {
        this.valorconta = valorconta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Planoconta getPlanoconta() {
        return planoconta;
    }

    public void setPlanoconta(Planoconta planoconta) {
        this.planoconta = planoconta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getSituacaoboleto() {
        return situacaoboleto;
    }

    public void setSituacaoboleto(String situacaoboleto) {
        this.situacaoboleto = situacaoboleto;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public String getNossonumero() {
        return nossonumero;
    }

    public void setNossonumero(String nossonumero) {
        this.nossonumero = nossonumero;
    }

    public Date getDatavencimento() {
        return datavencimento;
    }

    public void setDatavencimento(Date datavencimento) {
        this.datavencimento = datavencimento;
    }

    public Integer getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Integer idempresa) {
        this.idempresa = idempresa;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontasreceber != null ? idcontasreceber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contasreceber)) {
            return false;
        }
        Contasreceber other = (Contasreceber) object;
        if ((this.idcontasreceber == null && other.idcontasreceber != null) || (this.idcontasreceber != null && !this.idcontasreceber.equals(other.idcontasreceber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Contasreceber[ idcontasreceber=" + idcontasreceber + " ]";
    }
}
