/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
    @NamedQuery(name = "FormularioEvidencia.findByIdFormularioEvidencia", query = "SELECT f FROM FormularioEvidencia f WHERE f.idFormularioEvidencia = :idFormularioEvidencia"),
    @NamedQuery(name = "FormularioEvidencia.findByFormulario", query = "SELECT f FROM FormularioEvidencia f WHERE f.formularioNUE = :formularioNUE")
})
public class FormularioEvidencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFormularioEvidencia")
    private Integer idFormularioEvidencia;
    @JoinColumn(name = "Formulario_NUE", referencedColumnName = "NUE")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Formulario formularioNUE;
    @JoinColumn(name = "Evidencia_idEvidencia", referencedColumnName = "idEvidencia")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Evidencia evidenciaidEvidencia;

    public FormularioEvidencia() {
    }

    public FormularioEvidencia(Integer idFormularioEvidencia) {
        this.idFormularioEvidencia = idFormularioEvidencia;
    }

    public Integer getIdFormularioEvidencia() {
        return idFormularioEvidencia;
    }

    public void setIdFormularioEvidencia(Integer idFormularioEvidencia) {
        this.idFormularioEvidencia = idFormularioEvidencia;
    }

    public Formulario getFormularioNUE() {
        return formularioNUE;
    }

    public void setFormularioNUE(Formulario formularioNUE) {
        this.formularioNUE = formularioNUE;
    }

    public Evidencia getEvidenciaidEvidencia() {
        return evidenciaidEvidencia;
    }

    public void setEvidenciaidEvidencia(Evidencia evidenciaidEvidencia) {
        this.evidenciaidEvidencia = evidenciaidEvidencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormularioEvidencia != null ? idFormularioEvidencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormularioEvidencia)) {
            return false;
        }
        FormularioEvidencia other = (FormularioEvidencia) object;
        if ((this.idFormularioEvidencia == null && other.idFormularioEvidencia != null) || (this.idFormularioEvidencia != null && !this.idFormularioEvidencia.equals(other.idFormularioEvidencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FormularioEvidencia[ idFormularioEvidencia=" + idFormularioEvidencia + " ]";
    }
    
}
