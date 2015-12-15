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
@Table(name = "tipo_motivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoMotivo.findAll", query = "SELECT t FROM TipoMotivo t"),
    @NamedQuery(name = "TipoMotivo.findByIdMotivo", query = "SELECT t FROM TipoMotivo t WHERE t.idMotivo = :idMotivo"),
    @NamedQuery(name = "TipoMotivo.findByTipoMotivo", query = "SELECT t FROM TipoMotivo t WHERE t.tipoMotivo = :tipoMotivo")})
public class TipoMotivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMotivo")
    private Integer idMotivo;
    @Size(max = 45)
    @Column(name = "tipoMotivo")
    private String tipoMotivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoMotivoidMotivo")
    private List<Traslado> trasladoList;

    public TipoMotivo() {
    }

    public TipoMotivo(Integer idMotivo) {
        this.idMotivo = idMotivo;
    }

    public Integer getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(Integer idMotivo) {
        this.idMotivo = idMotivo;
    }

    public String getTipoMotivo() {
        return tipoMotivo;
    }

    public void setTipoMotivo(String tipoMotivo) {
        this.tipoMotivo = tipoMotivo;
    }

    @XmlTransient
    public List<Traslado> getTrasladoList() {
        return trasladoList;
    }

    public void setTrasladoList(List<Traslado> trasladoList) {
        this.trasladoList = trasladoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMotivo != null ? idMotivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMotivo)) {
            return false;
        }
        TipoMotivo other = (TipoMotivo) object;
        if ((this.idMotivo == null && other.idMotivo != null) || (this.idMotivo != null && !this.idMotivo.equals(other.idMotivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TipoMotivo[ idMotivo=" + idMotivo + " ]";
    }
    
}
