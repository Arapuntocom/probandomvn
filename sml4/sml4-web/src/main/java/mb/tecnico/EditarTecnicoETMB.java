/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.tecnico;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.EdicionFormulario;
import entity.Formulario;
import entity.Traslado;
import entity.Usuario;
import java.util.ArrayList;
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
 * @author Aracelly
 */
@Named(value = "editarTecnicoETMB")
@RequestScoped
@ManagedBean
public class EditarTecnicoETMB {

    @EJB
    private UsuarioEJBLocal usuarioEJB;

    @EJB
    private FormularioEJBLocal formularioEJB;

    //Para obtener nue
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;
    private int nue;
    private Formulario formulario;

    //Para obtener el usuario
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    private String usuarioS;
    private Usuario usuarioSesion;

    private String observacionEdicion;
    
    //Listado de  ediciones realizadas
    List<EdicionFormulario> ediciones;
    List<Traslado> traslados;
    
    static final Logger logger = Logger.getLogger(EditarTecnicoETMB.class.getName());
    
    public EditarTecnicoETMB() {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "EditarTecnicoETMB");
        /**/
        this.ediciones = new ArrayList();
        this.traslados = new ArrayList();
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();

        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        if (httpServletRequest1.getSession().getAttribute("nueF") != null) {
            this.nue = (int) httpServletRequest1.getSession().getAttribute("nueF");
            logger.log(Level.FINEST, "Nue recibido {0}", this.nue);
        }

        if (httpServletRequest.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioS = (String) httpServletRequest.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Cuenta Usuario recibido {0}", this.usuarioS);
        }

        logger.exiting(this.getClass().getName(), "EditarTecnicoETMB");
    }

    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "cargarDatosTecnico");
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioS);
        this.traslados = formularioEJB.traslados(formulario);
        this.ediciones = formularioEJB.listaEdiciones(this.nue);
        logger.exiting(this.getClass().getName(), "cargarDatosTecnico");
    }
    
    public String editarFormulario(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "editarFormularioTecnico");
        String response = formularioEJB.edicionFormulario(formulario, observacionEdicion, usuarioSesion);
        httpServletRequest.getSession().setAttribute("nueF", this.nue);
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
        if(response.equals("Exito")){
            logger.exiting(this.getClass().getName(), "editarFormularioTecnico", "editarTecnicoET");
            return "editarTecnicoET.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un problema al guardar los cambios, por favor intente más tarde.", "error al editar"));
        logger.exiting(this.getClass().getName(), "editarFormularioTecnico", "");
        return "";
    }
    
    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirTecnico");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirTecnico", "/indexListo");
        return "/indexListo.xhtml?faces-redirect=true";
    }
    
    public int getNue() {
        return nue;
    }

    public void setNue(int nue) {
        this.nue = nue;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public String getObservacionEdicion() {
        return observacionEdicion;
    }

    public void setObservacionEdicion(String observacionEdicion) {
        this.observacionEdicion = observacionEdicion;
    }

    public List<EdicionFormulario> getEdiciones() {
        return ediciones;
    }

    public void setEdiciones(List<EdicionFormulario> ediciones) {
        this.ediciones = ediciones;
    }

    public List<Traslado> getTraslados() {
        return traslados;
    }

    public void setTraslados(List<Traslado> traslados) {
        this.traslados = traslados;
    }
}
