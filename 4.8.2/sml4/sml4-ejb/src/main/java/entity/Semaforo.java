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
@Table(name = "semaforo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Semaforo.findAll", query = "SELECT s FROM Semaforo s"),
    @NamedQuery(name = "Semaforo.findByIdSemaforo", query = "SELECT s FROM Semaforo s WHERE s.idSemaforo = :idSemaforo"),
    @NamedQuery(name = "Semaforo.findBySemaforo", query = "SELECT s FROM Semaforo s WHERE s.semaforo = :semaforo"),
    @NamedQuery(name = "Semaforo.findByDescripcionSemaforo", query = "SELECT s FROM Semaforo s WHERE s.descripcionSemaforo = :descripcionSemaforo")})
public class Semaforo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idSemaforo")
    private Integer idSemaforo;
    @Size(max = 45)
    @Column(name = "semaforo")
    private String semaforo;
    @Size(max = 45)
    @Column(name = "descripcionSemaforo")
    private String descripcionSemaforo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semaforoidSemaforo", fetch = FetchType.EAGER)
    private List<Formulario> formularioList;

    public Semaforo() {
    }

    public Semaforo(Integer idSemaforo) {
        this.idSemaforo = idSemaforo;
    }

    public Integer getIdSemaforo() {
        return idSemaforo;
    }

    public void setIdSemaforo(Integer idSemaforo) {
        this.idSemaforo = idSemaforo;
    }

    public String getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(String semaforo) {
        this.semaforo = semaforo;
    }

    public String getDescripcionSemaforo() {
        return descripcionSemaforo;
    }

    public void setDescripcionSemaforo(String descripcionSemaforo) {
        this.descripcionSemaforo = descripcionSemaforo;
    }

    @XmlTransient
    public List<Formulario> getFormularioList() {
        return formularioList;
    }

    public void setFormularioList(List<Formulario> formularioList) {
        this.formularioList = formularioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSemaforo != null ? idSemaforo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semaforo)) {
            return false;
        }
        Semaforo other = (Semaforo) object;
        if ((this.idSemaforo == null && other.idSemaforo != null) || (this.idSemaforo != null && !this.idSemaforo.equals(other.idSemaforo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Semaforo[ idSemaforo=" + idSemaforo + " ]";
    }
    
}
