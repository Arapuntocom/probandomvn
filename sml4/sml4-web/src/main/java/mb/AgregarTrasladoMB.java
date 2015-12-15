/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.Formulario;
import entity.Traslado;
import entity.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aracelly
 */
@Named(value = "agregarTrasladoMB")
@RequestScoped
@ManagedBean
public class AgregarTrasladoMB {
    @EJB
    private UsuarioEJBLocal usuarioEJB;

    @EJB
    private FormularioEJBLocal formularioEJB;

    static final Logger logger = Logger.getLogger(AgregarTrasladoMB.class.getName());

    private Formulario formulario;

    private Usuario usuarioEntrega;
    private Usuario usuarioRecibe;
    private String motivo;
    private String obs;
    private Date fechaT;
    private Usuario usuarioSesion;

    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    private int nueAbuscar;
    private String cuentaUsuario;

    private List<Traslado> listaTraslados;

    public AgregarTrasladoMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "AgregarTrasladoMB");
        this.usuarioEntrega = new Usuario();
        this.usuarioRecibe = new Usuario();
        this.usuarioSesion = new Usuario();
        this.listaTraslados = new ArrayList<>();
        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if (this.httpServletRequest.getSession().getAttribute("cuentaUsuario") != null) {
            this.cuentaUsuario = (String) this.httpServletRequest.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "CuentaUsuario recibido {0}", this.cuentaUsuario);
        }
        logger.exiting(this.getClass().getName(), "AgregarTrasladoMB");
    }
    
    @PostConstruct
    public void cargarUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "cargarUsuarioSesion");
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(cuentaUsuario);
        logger.exiting(this.getClass().getName(), "cargarUsuarioSesion");
    }
 
    public void buscarFormulario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscarFormulario");
        this.formulario = formularioEJB.findFormularioByNue(nueAbuscar);
        logger.exiting(this.getClass().getName(), "buscarFormulario");
    }
 
    public void agregarTraslado() { 
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "agregarTraslado");
        logger.log(Level.FINEST, "rut usuario entrega {0}", this.usuarioEntrega.getRutUsuario());
        logger.log(Level.FINEST, "rut usuario recibe {0}", this.usuarioRecibe.getRutUsuario());
        logger.log(Level.FINEST, "motivo {0}", this.motivo);
        formularioEJB.crearTraslado(this.nueAbuscar, usuarioEntrega, usuarioRecibe, fechaT, formulario.getFechaOcurrido(), obs, motivo);
        logger.exiting(this.getClass().getName(), "agregarTraslado");      
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public Usuario getUsuarioEntrega() {
        return usuarioEntrega;
    }

    public void setUsuarioEntrega(Usuario usuarioEntrega) {
        this.usuarioEntrega = usuarioEntrega;
    }

    public Usuario getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(Usuario usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Date getFechaT() {
        return fechaT;
    }

    public void setFechaT(Date fechaT) {
        this.fechaT = fechaT;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public int getNueAbuscar() {
        return nueAbuscar;
    }

    public void setNueAbuscar(int nueAbuscar) {
        this.nueAbuscar = nueAbuscar;
    }

    public String getCuentaUsuario() {
        return cuentaUsuario;
    }

    public void setCuentaUsuario(String cuentaUsuario) {
        this.cuentaUsuario = cuentaUsuario;
    }

    public List<Traslado> getListaTraslados() {
        return listaTraslados;
    }

    public void setListaTraslados(List<Traslado> listaTraslados) {
        this.listaTraslados = listaTraslados;
    }

    
}
