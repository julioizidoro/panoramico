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
 * @author Wolverine
 */
@Entity
@Table(name = "exameconvidado")
public class Exameconvidado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idexameconvidado")
    private Integer idexameassociado;
    @JoinColumn(name = "exame_idexame", referencedColumnName = "idexame")
    @ManyToOne(optional = false)
    private Exame exame;
    @JoinColumn(name = "eventoconvidados_ideventoconvidados", referencedColumnName = "ideventoconvidados")
    @ManyToOne(optional = false)
    private Eventoconvidados eventoconvidados;

    public Exameconvidado() {
    }

    public Exameconvidado(Integer idexameassociado) {
        this.idexameassociado = idexameassociado;
    }

    public Integer getIdexameassociado() {
        return idexameassociado;
    }

    public void setIdexameassociado(Integer idexameassociado) {
        this.idexameassociado = idexameassociado;
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public Eventoconvidados getEventoconvidados() {
        return eventoconvidados;
    }

    public void setEventoconvidados(Eventoconvidados eventoconvidados) {
        this.eventoconvidados = eventoconvidados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idexameassociado != null ? idexameassociado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exameconvidado)) {
            return false;
        }
        Exameconvidado other = (Exameconvidado) object;
        if ((this.idexameassociado == null && other.idexameassociado != null) || (this.idexameassociado != null && !this.idexameassociado.equals(other.idexameassociado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.panoramico.model.Exameassociado[ idexameassociado=" + idexameassociado + " ]";
    }
    
}
