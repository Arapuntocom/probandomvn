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
 * @author Aracelly
 */
@Entity
@Table(name = "traslado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Traslado.findAll", query = "SELECT t FROM Traslado t"),
    @NamedQuery(name = "Traslado.findByIdInvolucrado", query = "SELECT t FROM Traslado t WHERE t.idInvolucrado = :idInvolucrado"),
    @NamedQuery(name = "Traslado.findByFechaEntrega", query = "SELECT t FROM Traslado t WHERE t.fechaEntrega = :fechaEntrega"),
    @NamedQuery(name = "Traslado.findByObservaciones", query = "SELECT t FROM Traslado t WHERE t.observaciones = :observaciones"),

    @NamedQuery(name = "Traslado.findByNue", query="SELECT t FROM Traslado t WHERE t.formularioNUE = :nue")
})
public class Traslado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "idInvolucrado")
    private Integer idInvolucrado;
    @Column(name = "fechaEntrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @Size(max = 300)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuario usuarioidUsuario;
    @JoinColumn(name = "Usuario_idUsuario1", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuario usuarioidUsuario1;
    @JoinColumn(name = "Tipo_Motivo_idMotivo", referencedColumnName = "idMotivo")
    @ManyToOne(optional = false)
    private TipoMotivo tipoMotivoidMotivo;
    @JoinColumn(name = "Formulario_NUE", referencedColumnName = "NUE")
    @ManyToOne(optional = false)
    private Formulario formularioNUE;

    public Traslado() {
    }

    public Traslado(Integer idInvolucrado) {
        this.idInvolucrado = idInvolucrado;
    }

    public Integer getIdInvolucrado() {
        return idInvolucrado;
    }

    public void setIdInvolucrado(Integer idInvolucrado) {
        this.idInvolucrado = idInvolucrado;
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

    public Usuario getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(Usuario usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public Usuario getUsuarioidUsuario1() {
        return usuarioidUsuario1;
    }

    public void setUsuarioidUsuario1(Usuario usuarioidUsuario1) {
        this.usuarioidUsuario1 = usuarioidUsuario1;
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
        hash += (idInvolucrado != null ? idInvolucrado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Traslado)) {
            return false;
        }
        Traslado other = (Traslado) object;
        if ((this.idInvolucrado == null && other.idInvolucrado != null) || (this.idInvolucrado != null && !this.idInvolucrado.equals(other.idInvolucrado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Traslado[ idInvolucrado=" + idInvolucrado + " ]";
    }
    
}
