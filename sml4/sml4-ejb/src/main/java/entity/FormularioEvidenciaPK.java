/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Aracelly
 */
@Embeddable
public class FormularioEvidenciaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "Formulario_NUE")
    private int formularioNUE;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Evidencia_idEvidencia")
    private int evidenciaidEvidencia;

    public FormularioEvidenciaPK() {
    }

    public FormularioEvidenciaPK(int formularioNUE, int evidenciaidEvidencia) {
        this.formularioNUE = formularioNUE;
        this.evidenciaidEvidencia = evidenciaidEvidencia;
    }

    public int getFormularioNUE() {
        return formularioNUE;
    }

    public void setFormularioNUE(int formularioNUE) {
        this.formularioNUE = formularioNUE;
    }

    public int getEvidenciaidEvidencia() {
        return evidenciaidEvidencia;
    }

    public void setEvidenciaidEvidencia(int evidenciaidEvidencia) {
        this.evidenciaidEvidencia = evidenciaidEvidencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) formularioNUE;
        hash += (int) evidenciaidEvidencia;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormularioEvidenciaPK)) {
            return false;
        }
        FormularioEvidenciaPK other = (FormularioEvidenciaPK) object;
        if (this.formularioNUE != other.formularioNUE) {
            return false;
        }
        if (this.evidenciaidEvidencia != other.evidenciaidEvidencia) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FormularioEvidenciaPK[ formularioNUE=" + formularioNUE + ", evidenciaidEvidencia=" + evidenciaidEvidencia + " ]";
    }
    
}
