/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan
 */
@Entity
@Table(name = "traslado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Traslado.findAll", query = "SELECT t FROM Traslado t"),
    @NamedQuery(name = "Traslado.findByIdTraslado", query = "SELECT t FROM Traslado t WHERE t.idTraslado = :idTraslado"),
    @NamedQuery(name = "Traslado.findByFechaEntrega", query = "SELECT t FROM Traslado t WHERE t.fechaEntrega = :fechaEntrega"),
    @NamedQuery(name = "Traslado.findByObservaciones", query = "SELECT t FROM Traslado t WHERE t.observaciones = :observaciones"),
    @NamedQuery(name = "Traslado.findByNue", query = "SELECT t FROM Traslado t WHERE t.formularioNUE = :nue")
})
public class Traslado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTraslado")
    private Integer idTraslado;
    @Column(name = "fechaEntrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @Size(max = 300)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "Usuario_idUsuarioRecibe", referencedColumnName = "idUsuario")
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuarioidUsuarioRecibe;
    @JoinColumn(name = "Usuario_idUsuarioEntrega", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Usuario usuarioidUsuarioEntrega;
    @JoinColumn(name = "Tipo_Motivo_idMotivo", referencedColumnName = "idMotivo")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TipoMotivo tipoMotivoidMotivo;
    @JoinColumn(name = "Formulario_NUE", referencedColumnName = "NUE")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Formulario formularioNUE;

    public Traslado() {
    }

    public Traslado(Integer idTraslado) {
        this.idTraslado = idTraslado;
    }

    public Integer getIdTraslado() {
        return idTraslado;
    }

    public void setIdTraslado(Integer idTraslado) {
        this.idTraslado = idTraslado;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Usuario getUsuarioidUsuarioRecibe() {
        return usuarioidUsuarioRecibe;
    }

    public void setUsuarioidUsuarioRecibe(Usuario usuarioidUsuarioRecibe) {
        this.usuarioidUsuarioRecibe = usuarioidUsuarioRecibe;
    }

    public Usuario getUsuarioidUsuarioEntrega() {
        return usuarioidUsuarioEntrega;
    }

    public void setUsuarioidUsuarioEntrega(Usuario usuarioidUsuarioEntrega) {
        this.usuarioidUsuarioEntrega = usuarioidUsuarioEntrega;
    }

    public TipoMotivo getTipoMotivoidMotivo() {
        return tipoMotivoidMotivo;
    }

    public void setTipoMotivoidMotivo(TipoMotivo tipoMotivoidMotivo) {
        this.tipoMotivoidMotivo = tipoMotivoidMotivo;
    }

    public Formulario getFormularioNUE() {
        return formularioNUE;
    }

    public void setFormularioNUE(Formulario formularioNUE) {
        this.formularioNUE = formularioNUE;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTraslado != null ? idTraslado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Traslado)) {
            return false;
        }
        Traslado other = (Traslado) object;
        if ((this.idTraslado == null && other.idTraslado != null) || (this.idTraslado != null && !this.idTraslado.equals(other.idTraslado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Traslado[ idTraslado=" + idTraslado + " ]";
    }
    
}
