/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.administrador;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.Usuario;
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
import mb.jefeArea.ResultadoBuscadorUserJefeaAreaMB;

/**
 *
 * @author sebastian
 */
@Named(value = "resultadoBuscadorUserAdmMB")
@RequestScoped
@ManagedBean
public class ResultadoBuscadorUserAdmMB {

    @EJB
    private FormularioEJBLocal formularioEJB;
    @EJB
    private UsuarioEJBLocal usuarioEJB;

    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;

    private String userSesion;
    private Usuario usuarioSesion;

    private String rut;
    private Usuario usuarioBuscado;

    private static final Logger logger = Logger.getLogger(ResultadoBuscadorUserJefeaAreaMB.class.getName());

    public ResultadoBuscadorUserAdmMB() {
        
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "ResultadoBuscadorUserAdmMB");

        facesContext1 = FacesContext.getCurrentInstance();
        httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();

        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("rut") != null) {
            this.rut = (String) httpServletRequest.getSession().getAttribute("rut");
            logger.log(Level.FINEST, "rut recibido {0}", this.rut);
        }

        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.userSesion = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.userSesion);
        }
        logger.exiting(this.getClass().getName(), "ResultadoBuscadorUserAdmMB");

        
    }
    
     @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CargarDatosADM");
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(this.userSesion);
        this.usuarioBuscado = usuarioEJB.findUserByRut(this.rut);
        logger.log(Level.INFO, "Nombre usuario {0}", this.usuarioBuscado.getNombreUsuario());
        logger.log(Level.FINEST, "Rut usuario {0}", this.usuarioBuscado.getRutUsuario());
        logger.exiting(this.getClass().getName(), "CargarDatosADM");
    }

    public String habilitarUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "habilitarUsuarioADM");
        boolean response = usuarioEJB.edicionEstadoUsuario(this.rut, "Activo");

        if (response == true) {
            httpServletRequest.getSession().setAttribute("rut", this.rut);
            httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.userSesion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario ha sido habilitado correctamente", ""));
            logger.exiting(this.getClass().getName(), "habilitarUsuarioADM", "buscadorJefeAreaResultUsuario");
            return "";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario ya se encuentra habilitado", ""));
        logger.info("Usuario ya se encuentra habilitado");
        logger.exiting(this.getClass().getName(), "habilitarUsuarioADM", "habilitarUsuario");

        return "";
    }

    public String deshabilitarUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "deshabilitarUsuarioADM");
        boolean response = usuarioEJB.edicionEstadoUsuario(this.rut, "Inactivo");
        if (response == true) {
            httpServletRequest.getSession().setAttribute("rut", this.rut);
            httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.userSesion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario ha sido deshabilitado correctamente", ""));
            logger.exiting(this.getClass().getName(), "deshabilitarUsuarioADM", "buscadorJefeAreaResultUsuario");
            return "";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario ya se encuentra deshabilitado", ""));
        logger.info("Usuario ya se encuentra deshabilitado");
        logger.exiting(this.getClass().getName(), "deshabilitarUsuarioADM", "deshabilitarUsuario");
        return "";
    }

    //retorna a la vista para realizar busqueda
    public String buscador() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorADM");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.userSesion);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "buscadorADM", "buscador");
        return "buscadorAdministrador?faces-redirect=true";
    }

    public String crearUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearUsuarioADM");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.userSesion);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "crearUsuarioADM", "crearUsuario");
        return "crearUsuarioAdministrador.xhtml?faces-redirect=true";
    }
    
     public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirADM");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirADM", "/indexListo");
        return "/indexListo?faces-redirect=true";
    }

     
     
     
    public String getUserSesion() {
        return userSesion;
    }

    public void setUserSesion(String userSesion) {
        this.userSesion = userSesion;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Usuario getUsuarioBuscado() {
        return usuarioBuscado;
    }

    public void setUsuarioBuscado(Usuario usuarioBuscado) {
        this.usuarioBuscado = usuarioBuscado;
    }
    
    

}
