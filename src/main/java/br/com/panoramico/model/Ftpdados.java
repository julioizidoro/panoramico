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
@Table(name = "ftpdados")
public class Ftpdados implements Serializable{
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idftpdados")
    private Integer idftpdados;
    @Column(name = "host")
    private String host;
    @Column(name = "user")
    private String user;
    @Column(name = "password")
    private String password;
    @Column(name = "hostupload")
    private String hostupload;

    public Ftpdados() {
    }

    public Integer getIdftpdados() {
        return idftpdados;
    }

    public void setIdftpdados(Integer idftpdados) {
        this.idftpdados = idftpdados;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostupload() {
        return hostupload;
    }

    public void setHostupload(String hostupload) {
        this.hostupload = hostupload;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idftpdados != null ? idftpdados.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medico)) {
            return false;
        }
        Ftpdados other = (Ftpdados) object;
        if ((this.idftpdados == null && other.idftpdados != null) || (this.idftpdados != null && !this.idftpdados.equals(other.idftpdados))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Ftpdados[ idftpdados=" + idftpdados + " ]";
    }
    
}
