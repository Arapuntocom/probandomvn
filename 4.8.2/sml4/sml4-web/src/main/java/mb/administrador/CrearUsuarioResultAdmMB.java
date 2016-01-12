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
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mb.jefeArea.ResultadoCrearJefeAreaMB;

/**
 *
 * @author sebastian
 */
@Named(value = "crearUsuarioResultAdmMB")
@RequestScoped
@ManagedBean
public class CrearUsuarioResultAdmMB {

    @EJB
    private UsuarioEJBLocal usuarioEJB;
    @EJB
    private FormularioEJBLocal formularioEJB;

    static final Logger logger = Logger.getLogger(ResultadoCrearJefeAreaMB.class.getName());

    private Usuario usuarioSesion;

    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;

    private String usuarioSis;

    //busqueda usuario
    private String rut;
    private Usuario userCreado;

    public CrearUsuarioResultAdmMB() {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CrearUsuarioResultAdmMB");

        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }

        if (httpServletRequest.getSession().getAttribute("rut") != null) {
            this.rut = (String) httpServletRequest.getSession().getAttribute("rut");
            logger.log(Level.FINEST, "Rut recibido {0}", this.rut);
        }

        logger.exiting(this.getClass().getName(), "CrearUsuarioResultAdmMB");

    }

    @PostConstruct
    public void loadDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadDatosJefeArea");

        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(this.usuarioSis);
        this.userCreado = usuarioEJB.findUserByRut(this.rut);

    	//Cargando areas
        logger.exiting(this.getClass().getName(), "loadDatosJefeArea");
    }

    //retorna a la vista para realizar busqueda
    public String buscador() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorADM");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "buscadorADM", "buscadorADM");
        return "buscadorAdministrador?faces-redirect=true";
    }

    public String crearUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearUsuarioADM");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
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

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public String getUsuarioSis() {
        return usuarioSis;
    }

    public void setUsuarioSis(String usuarioSis) {
        this.usuarioSis = usuarioSis;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Usuario getUserCreado() {
        return userCreado;
    }

    public void setUserCreado(Usuario userCreado) {
        this.userCreado = userCreado;
    }

}
