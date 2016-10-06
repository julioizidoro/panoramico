/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.acesso;


public class InformacoesFrequenciaBean {
    
    private String nomeAssociado;
    private String nomeDependente;
    private String tipo;
    private Integer idAcessoAssocioado;
    private Integer idAcessoDependente;
    private Integer idAssociado;

    public String getNomeAssociado() {
        return nomeAssociado;
    }

    public void setNomeAssociado(String nomeAssociado) {
        this.nomeAssociado = nomeAssociado;
    }

    public String getNomeDependente() {
        return nomeDependente;
    }

    public void setNomeDependente(String nomeDependente) {
        this.nomeDependente = nomeDependente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdAcessoAssocioado() {
        return idAcessoAssocioado;
    }

    public void setIdAcessoAssocioado(Integer idAcessoAssocioado) {
        this.idAcessoAssocioado = idAcessoAssocioado;
    }

    public Integer getIdAcessoDependente() {
        return idAcessoDependente;
    }

    public void setIdAcessoDependente(Integer idAcessoDependente) {
        this.idAcessoDependente = idAcessoDependente;
    }

    public Integer getIdAssociado() {
        return idAssociado;
    }

    public void setIdAssociado(Integer idAssociado) {
        this.idAssociado = idAssociado;
    }
    
    
    
}
