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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aracelly
 */
@Entity
@Table(name = "edicion_formulario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EdicionFormulario.findAll", query = "SELECT e FROM EdicionFormulario e"),
    @NamedQuery(name = "EdicionFormulario.findByIdEdicion", query = "SELECT e FROM EdicionFormulario e WHERE e.idEdicion = :idEdicion"),
    @NamedQuery(name = "EdicionFormulario.findByFechaEdicion", query = "SELECT e FROM EdicionFormulario e WHERE e.fechaEdicion = :fechaEdicion")})
public class EdicionFormulario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEdicion")
    private Integer idEdicion;
    @Column(name = "fechaEdicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuario usuarioidUsuario;
    @JoinColumn(name = "Formulario_NUE", referencedColumnName = "NUE")
    @ManyToOne(optional = false)
    private Formulario formularioNUE;

    public EdicionFormulario() {
    }

    public EdicionFormulario(Integer idEdicion) {
        this.idEdicion = idEdicion;
    }

    public Integer getIdEdicion() {
        return idEdicion;
    }

    public void setIdEdicion(Integer idEdicion) {
        this.idEdicion = idEdicion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public Usuario getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(Usuario usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
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
        hash += (idEdicion != null ? idEdicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EdicionFormulario)) {
            return false;
        }
        EdicionFormulario other = (EdicionFormulario) object;
        if ((this.idEdicion == null && other.idEdicion != null) || (this.idEdicion != null && !this.idEdicion.equals(other.idEdicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EdicionFormulario[ idEdicion=" + idEdicion + " ]";
    }
    
}
