/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aracelly
 */
@Entity
@Table(name = "formulario_evidencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormularioEvidencia.findAll", query = "SELECT f FROM FormularioEvidencia f"),
    @NamedQuery(name = "FormularioEvidencia.findByFormularioNUE", query = "SELECT f FROM FormularioEvidencia f WHERE f.formularioEvidenciaPK.formularioNUE = :formularioNUE"),
    @NamedQuery(name = "FormularioEvidencia.findByEvidenciaidEvidencia", query = "SELECT f FROM FormularioEvidencia f WHERE f.formularioEvidenciaPK.evidenciaidEvidencia = :evidenciaidEvidencia"),
    @NamedQuery(name = "FormularioEvidencia.findByCantidad", query = "SELECT f FROM FormularioEvidencia f WHERE f.cantidad = :cantidad")})
public class FormularioEvidencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FormularioEvidenciaPK formularioEvidenciaPK;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "Formulario_NUE", referencedColumnName = "NUE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Formulario formulario;
    @JoinColumn(name = "Evidencia_idEvidencia", referencedColumnName = "idEvidencia", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Evidencia evidencia;

    public FormularioEvidencia() {
    }

    public FormularioEvidencia(FormularioEvidenciaPK formularioEvidenciaPK) {
        this.formularioEvidenciaPK = formularioEvidenciaPK;
    }

    public FormularioEvidencia(int formularioNUE, int evidenciaidEvidencia) {
        this.formularioEvidenciaPK = new FormularioEvidenciaPK(formularioNUE, evidenciaidEvidencia);
    }

    public FormularioEvidenciaPK getFormularioEvidenciaPK() {
        return formularioEvidenciaPK;
    }

    public void setFormularioEvidenciaPK(FormularioEvidenciaPK formularioEvidenciaPK) {
        this.formularioEvidenciaPK = formularioEvidenciaPK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public Evidencia getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(Evidencia evidencia) {
        this.evidencia = evidencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formularioEvidenciaPK != null ? formularioEvidenciaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormularioEvidencia)) {
            return false;
        }
        FormularioEvidencia other = (FormularioEvidencia) object;
        if ((this.formularioEvidenciaPK == null && other.formularioEvidenciaPK != null) || (this.formularioEvidenciaPK != null && !this.formularioEvidenciaPK.equals(other.formularioEvidenciaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FormularioEvidencia[ formularioEvidenciaPK=" + formularioEvidenciaPK + " ]";
    }
    
}
