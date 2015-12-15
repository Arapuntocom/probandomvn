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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aracelly
 */
@Entity
@Table(name = "peritaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Peritaje.findAll", query = "SELECT p FROM Peritaje p"),
    @NamedQuery(name = "Peritaje.findByIdPeritaje", query = "SELECT p FROM Peritaje p WHERE p.idPeritaje = :idPeritaje"),
    @NamedQuery(name = "Peritaje.findByFechaPeritaje", query = "SELECT p FROM Peritaje p WHERE p.fechaPeritaje = :fechaPeritaje")})
public class Peritaje implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPeritaje")
    private Integer idPeritaje;
    @Column(name = "fechaPeritaje")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPeritaje;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuario usuarioidUsuario;
    @JoinColumn(name = "Formulario_NUE", referencedColumnName = "NUE")
    @ManyToOne(optional = false)
    private Formulario formularioNUE;

    public Peritaje() {
    }

    public Peritaje(Integer idPeritaje) {
        this.idPeritaje = idPeritaje;
    }

    public Integer getIdPeritaje() {
        return idPeritaje;
    }

    public void setIdPeritaje(Integer idPeritaje) {
        this.idPeritaje = idPeritaje;
    }

    public Date getFechaPeritaje() {
        return fechaPeritaje;
    }

    public void setFechaPeritaje(Date fechaPeritaje) {
        this.fechaPeritaje = fechaPeritaje;
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
        hash += (idPeritaje != null ? idPeritaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Peritaje)) {
            return false;
        }
        Peritaje other = (Peritaje) object;
        if ((this.idPeritaje == null && other.idPeritaje != null) || (this.idPeritaje != null && !this.idPeritaje.equals(other.idPeritaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Peritaje[ idPeritaje=" + idPeritaje + " ]";
    }
    
}
