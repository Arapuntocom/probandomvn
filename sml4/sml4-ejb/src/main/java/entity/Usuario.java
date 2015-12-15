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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByNombreUsuario", query = "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "Usuario.findByApellidoUsuario", query = "SELECT u FROM Usuario u WHERE u.apellidoUsuario = :apellidoUsuario"),
    @NamedQuery(name = "Usuario.findByRutUsuario", query = "SELECT u FROM Usuario u WHERE u.rutUsuario = :rutUsuario"),
    @NamedQuery(name = "Usuario.findByPassUsuario", query = "SELECT u FROM Usuario u WHERE u.passUsuario = :passUsuario"),
    @NamedQuery(name = "Usuario.findByMailUsuario", query = "SELECT u FROM Usuario u WHERE u.mailUsuario = :mailUsuario"),
    @NamedQuery(name = "Usuario.findByCuentaUsuario", query = "SELECT u FROM Usuario u WHERE u.cuentaUsuario = :cuentaUsuario"),
    @NamedQuery(name = "Usuario.findByEstadoUsuario", query = "SELECT u FROM Usuario u WHERE u.estadoUsuario = :estadoUsuario"),
    @NamedQuery(name = "Usuario.findByUnidad", query = "SELECT u FROM Usuario u WHERE u.unidad = :unidad")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUsuario")
    private Integer idUsuario;
    @Size(max = 45)
    @Column(name = "nombreUsuario")
    private String nombreUsuario;
    @Size(max = 45)
    @Column(name = "apellidoUsuario")
    private String apellidoUsuario;
    @Size(max = 30)
    @Column(name = "rutUsuario")
    private String rutUsuario;
    @Size(max = 45)
    @Column(name = "passUsuario")
    private String passUsuario;
    @Size(max = 45)
    @Column(name = "mailUsuario")
    private String mailUsuario;
    @Size(max = 30)
    @Column(name = "cuentaUsuario")
    private String cuentaUsuario;
    @Column(name = "estadoUsuario")
    private Boolean estadoUsuario;
    @Size(max = 45)
    @Column(name = "unidad")
    private String unidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioidUsuario")
    private List<Traslado> trasladoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioidUsuario1")
    private List<Traslado> trasladoList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioidUsuario1")
    private List<Formulario> formularioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioidUsuario")
    private List<Formulario> formularioList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioidUsuario")
    private List<Peritaje> peritajeList;
    @JoinColumn(name = "Tipo_Usuario_idTipoUsuario", referencedColumnName = "idTipoUsuario")
    @ManyToOne(optional = false)
    private TipoUsuario tipoUsuarioidTipoUsuario;
    @JoinColumn(name = "Cargo_idCargo", referencedColumnName = "idCargo")
    @ManyToOne(optional = false)
    private Cargo cargoidCargo;
    @JoinColumn(name = "Area_idArea", referencedColumnName = "idArea")
    @ManyToOne(optional = false)
    private Area areaidArea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioidUsuario")
    private List<EdicionFormulario> edicionFormularioList;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(String rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    public String getPassUsuario() {
        return passUsuario;
    }

    public void setPassUsuario(String passUsuario) {
        this.passUsuario = passUsuario;
    }

    public String getMailUsuario() {
        return mailUsuario;
    }

    public void setMailUsuario(String mailUsuario) {
        this.mailUsuario = mailUsuario;
    }

    public String getCuentaUsuario() {
        return cuentaUsuario;
    }

    public void setCuentaUsuario(String cuentaUsuario) {
        this.cuentaUsuario = cuentaUsuario;
    }

    public Boolean getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(Boolean estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    @XmlTransient
    public List<Traslado> getTrasladoList() {
        return trasladoList;
    }

    public void setTrasladoList(List<Traslado> trasladoList) {
        this.trasladoList = trasladoList;
    }

    @XmlTransient
    public List<Traslado> getTrasladoList1() {
        return trasladoList1;
    }

    public void setTrasladoList1(List<Traslado> trasladoList1) {
        this.trasladoList1 = trasladoList1;
    }

    @XmlTransient
    public List<Formulario> getFormularioList() {
        return formularioList;
    }

    public void setFormularioList(List<Formulario> formularioList) {
        this.formularioList = formularioList;
    }

    @XmlTransient
    public List<Formulario> getFormularioList1() {
        return formularioList1;
    }

    public void setFormularioList1(List<Formulario> formularioList1) {
        this.formularioList1 = formularioList1;
    }

    @XmlTransient
    public List<Peritaje> getPeritajeList() {
        return peritajeList;
    }

    public void setPeritajeList(List<Peritaje> peritajeList) {
        this.peritajeList = peritajeList;
    }

    public TipoUsuario getTipoUsuarioidTipoUsuario() {
        return tipoUsuarioidTipoUsuario;
    }

    public void setTipoUsuarioidTipoUsuario(TipoUsuario tipoUsuarioidTipoUsuario) {
        this.tipoUsuarioidTipoUsuario = tipoUsuarioidTipoUsuario;
    }

    public Cargo getCargoidCargo() {
        return cargoidCargo;
    }

    public void setCargoidCargo(Cargo cargoidCargo) {
        this.cargoidCargo = cargoidCargo;
    }

    public Area getAreaidArea() {
        return areaidArea;
    }

    public void setAreaidArea(Area areaidArea) {
        this.areaidArea = areaidArea;
    }

    @XmlTransient
    public List<EdicionFormulario> getEdicionFormularioList() {
        return edicionFormularioList;
    }

    public void setEdicionFormularioList(List<EdicionFormulario> edicionFormularioList) {
        this.edicionFormularioList = edicionFormularioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Usuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
