/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.perito;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.EdicionFormulario;
import entity.Formulario;
import entity.Traslado;
import entity.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author sebastian
 */
@Named(value = "recibirPeritoMB")
@RequestScoped
@ManagedBean
public class RecibirPeritoMB {

    @EJB
    private UsuarioEJBLocal usuarioEJB;

    @EJB
    private FormularioEJBLocal formularioEJB;

    static final Logger logger = Logger.getLogger(RecibirPeritoMB.class.getName());

    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;

    private String usuarioEntrega;
    private String usuarioEntregaUnidad;
    private String usuarioEntregaCargo;
    private String usuarioEntregaRut;
    private String usuarioRecibe;
    private String usuarioRecibeUnidad;
    private String usuarioRecibeCargo;
    private String usuarioRecibeRut;
    private String motivo;
    private String observacionesT;
    private Date fechaT;

    private String usuarioSis;
    private Usuario usuarioSesion;
    
    private Usuario userEntrega;

    private int nue;

    private Formulario formulario;
    
    private List<Traslado> trasladosList;
    private List<EdicionFormulario> edicionesList;

    public RecibirPeritoMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "RecibirPeritoMB");
        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("nueF") != null) {
            this.nue = (int) httpServletRequest.getSession().getAttribute("nueF");
            logger.log(Level.FINEST, "nue recibido {0}", this.nue);
        }
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }
        this.usuarioSesion = new Usuario();
        this.userEntrega = new Usuario();
        this.trasladosList = new ArrayList<>();
        this.edicionesList = new ArrayList<>();
        this.formulario = new Formulario();
        
        logger.exiting(this.getClass().getName(), "RecibirPeritoMB");
    }
    
    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "cargarDatosPerito");
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioSis);
        this.trasladosList = formularioEJB.traslados(this.formulario);
        this.edicionesList = formularioEJB.listaEdiciones(nue);
        
        GregorianCalendar c = new GregorianCalendar();
        this.fechaT = c.getTime();
        
        this.usuarioRecibe = usuarioSesion.getNombreUsuario();
        this.usuarioRecibeCargo = usuarioSesion.getCargoidCargo().getNombreCargo();
        this.usuarioRecibeRut = usuarioSesion.getRutUsuario();
        this.usuarioRecibeUnidad = usuarioSesion.getUnidad();
        
        this.userEntrega = formularioEJB.obtenerPoseedorFormulario(formulario);
        
        this.usuarioEntrega = userEntrega.getNombreUsuario();
        this.usuarioEntregaCargo = userEntrega.getCargoidCargo().getNombreCargo();
        this.usuarioEntregaRut = userEntrega.getRutUsuario();
        this.usuarioEntregaUnidad = userEntrega.getUnidad();
        
        logger.exiting(this.getClass().getName(), "cargarDatosPerito");
    }

    public String agregarTraslado() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "agregarTrasladoPerito");
        logger.log(Level.FINEST, "rut usuario entrega {0}", this.usuarioEntrega);
        logger.log(Level.FINEST, "rut usuario recibe {0}", this.usuarioRecibe);
        logger.log(Level.FINEST, "rut motivo {0}", this.motivo);
        String resultado = formularioEJB.crearTraslado(formulario, usuarioEntrega, usuarioEntregaUnidad, usuarioEntregaCargo, usuarioEntregaRut, usuarioRecibe, usuarioRecibeUnidad, usuarioRecibeCargo, usuarioRecibeRut, fechaT, observacionesT, motivo, usuarioSesion);
        if (resultado.equals("Exito")) {
            httpServletRequest.getSession().setAttribute("nueF", this.nue);
            logger.exiting(this.getClass().getName(), "agregarTrasladoPerito", "todoPerito?faces-redirect=true");
            return "todoPerito?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado, "Uno o más datos inválidos"));
        logger.exiting(this.getClass().getName(), "agregarTrasladoPerito", "");
        return "";
    }

    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirPerito");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirPerito", "/indexListo");
        return "/indexListo?faces-redirect=true";
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public int getNue() {
        return nue;
    }

    public void setNue(int nue) {
        this.nue = nue;
    }

    public String getUsuarioEntrega() {
        return usuarioEntrega;
    }

    public void setUsuarioEntrega(String usuarioEntrega) {
        this.usuarioEntrega = usuarioEntrega;
    }

    public String getUsuarioEntregaUnidad() {
        return usuarioEntregaUnidad;
    }

    public void setUsuarioEntregaUnidad(String usuarioEntregaUnidad) {
        this.usuarioEntregaUnidad = usuarioEntregaUnidad;
    }

    public String getUsuarioEntregaCargo() {
        return usuarioEntregaCargo;
    }

    public void setUsuarioEntregaCargo(String usuarioEntregaCargo) {
        this.usuarioEntregaCargo = usuarioEntregaCargo;
    }

    public String getUsuarioEntregaRut() {
        return usuarioEntregaRut;
    }

    public void setUsuarioEntregaRut(String usuarioEntregaRut) {
        this.usuarioEntregaRut = usuarioEntregaRut;
    }

    public String getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(String usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    public String getUsuarioRecibeUnidad() {
        return usuarioRecibeUnidad;
    }

    public void setUsuarioRecibeUnidad(String usuarioRecibeUnidad) {
        this.usuarioRecibeUnidad = usuarioRecibeUnidad;
    }

    public String getUsuarioRecibeCargo() {
        return usuarioRecibeCargo;
    }

    public void setUsuarioRecibeCargo(String usuarioRecibeCargo) {
        this.usuarioRecibeCargo = usuarioRecibeCargo;
    }

    public String getUsuarioRecibeRut() {
        return usuarioRecibeRut;
    }

    public void setUsuarioRecibeRut(String usuarioRecibeRut) {
        this.usuarioRecibeRut = usuarioRecibeRut;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservacionesT() {
        return observacionesT;
    }

    public void setObservacionesT(String observacionesT) {
        this.observacionesT = observacionesT;
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

    public Usuario getUserEntrega() {
        return userEntrega;
    }

    public void setUserEntrega(Usuario userEntrega) {
        this.userEntrega = userEntrega;
    }

    public List<Traslado> getTrasladosList() {
        return trasladosList;
    }

    public void setTrasladosList(List<Traslado> trasladosList) {
        this.trasladosList = trasladosList;
    }

    public List<EdicionFormulario> getEdicionesList() {
        return edicionesList;
    }

    public void setEdicionesList(List<EdicionFormulario> edicionesList) {
        this.edicionesList = edicionesList;
    }

}
