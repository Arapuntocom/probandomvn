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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aracelly
 */
@Entity
@Table(name = "tipo_evidencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEvidencia.findAll", query = "SELECT t FROM TipoEvidencia t"),
    @NamedQuery(name = "TipoEvidencia.findByIdTipoEvidencia", query = "SELECT t FROM TipoEvidencia t WHERE t.idTipoEvidencia = :idTipoEvidencia"),
    @NamedQuery(name = "TipoEvidencia.findByNombreTipoEvidencia", query = "SELECT t FROM TipoEvidencia t WHERE t.nombreTipoEvidencia = :nombreTipoEvidencia")})
public class TipoEvidencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoEvidencia")
    private Integer idTipoEvidencia;
    @Size(max = 45)
    @Column(name = "nombreTipoEvidencia")
    private String nombreTipoEvidencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoEvidenciaidTipoEvidencia")
    private List<Evidencia> evidenciaList;

    public TipoEvidencia() {
    }

    public TipoEvidencia(Integer idTipoEvidencia) {
        this.idTipoEvidencia = idTipoEvidencia;
    }

    public Integer getIdTipoEvidencia() {
        return idTipoEvidencia;
    }

    public void setIdTipoEvidencia(Integer idTipoEvidencia) {
        this.idTipoEvidencia = idTipoEvidencia;
    }

    public String getNombreTipoEvidencia() {
        return nombreTipoEvidencia;
    }

    public void setNombreTipoEvidencia(String nombreTipoEvidencia) {
        this.nombreTipoEvidencia = nombreTipoEvidencia;
    }

    @XmlTransient
    public List<Evidencia> getEvidenciaList() {
        return evidenciaList;
    }

    public void setEvidenciaList(List<Evidencia> evidenciaList) {
        this.evidenciaList = evidenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoEvidencia != null ? idTipoEvidencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEvidencia)) {
            return false;
        }
        TipoEvidencia other = (TipoEvidencia) object;
        if ((this.idTipoEvidencia == null && other.idTipoEvidencia != null) || (this.idTipoEvidencia != null && !this.idTipoEvidencia.equals(other.idTipoEvidencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TipoEvidencia[ idTipoEvidencia=" + idTipoEvidencia + " ]";
    }
    
}
