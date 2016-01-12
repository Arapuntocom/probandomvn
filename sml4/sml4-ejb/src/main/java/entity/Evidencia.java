/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alan
 */
@Entity
@Table(name = "evidencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evidencia.findAll", query = "SELECT e FROM Evidencia e"),
    @NamedQuery(name = "Evidencia.findByIdEvidencia", query = "SELECT e FROM Evidencia e WHERE e.idEvidencia = :idEvidencia"),
    @NamedQuery(name = "Evidencia.findByNombreEvidencia", query = "SELECT e FROM Evidencia e WHERE e.nombreEvidencia = :nombreEvidencia"),
    @NamedQuery(name = "Evidencia.findByNombreAndTipoEvidencia", query = "SELECT e FROM Evidencia e WHERE e.nombreEvidencia = :nombreEvidencia AND e.tipoEvidenciaidTipoEvidencia = :tipoEvidenciaidTipoEvidencia")
})
public class Evidencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEvidencia")
    private Integer idEvidencia;
    @Size(max = 45)
    @Column(name = "nombreEvidencia")
    private String nombreEvidencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evidenciaidEvidencia", fetch = FetchType.EAGER)
    private List<FormularioEvidencia> formularioEvidenciaList;
    @JoinColumn(name = "Tipo_Evidencia_idTipoEvidencia", referencedColumnName = "idTipoEvidencia")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TipoEvidencia tipoEvidenciaidTipoEvidencia;

    public Evidencia() {
    }

    public Evidencia(Integer idEvidencia) {
        this.idEvidencia = idEvidencia;
    }

    public Integer getIdEvidencia() {
        return idEvidencia;
    }

    public void setIdEvidencia(Integer idEvidencia) {
        this.idEvidencia = idEvidencia;
    }

    public String getNombreEvidencia() {
        return nombreEvidencia;
    }

    public void setNombreEvidencia(String nombreEvidencia) {
        this.nombreEvidencia = nombreEvidencia;
    }

    @XmlTransient
    public List<FormularioEvidencia> getFormularioEvidenciaList() {
        return formularioEvidenciaList;
    }

    public void setFormularioEvidenciaList(List<FormularioEvidencia> formularioEvidenciaList) {
        this.formularioEvidenciaList = formularioEvidenciaList;
    }

    public TipoEvidencia getTipoEvidenciaidTipoEvidencia() {
        return tipoEvidenciaidTipoEvidencia;
    }

    public void setTipoEvidenciaidTipoEvidencia(TipoEvidencia tipoEvidenciaidTipoEvidencia) {
        this.tipoEvidenciaidTipoEvidencia = tipoEvidenciaidTipoEvidencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvidencia != null ? idEvidencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evidencia)) {
            return false;
        }
        Evidencia other = (Evidencia) object;
        if ((this.idEvidencia == null && other.idEvidencia != null) || (this.idEvidencia != null && !this.idEvidencia.equals(other.idEvidencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Evidencia[ idEvidencia=" + idEvidencia + " ]";
    }
    
}
