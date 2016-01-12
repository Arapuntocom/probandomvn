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
import mb.jefeArea.BuscadorJefeAreaMB;

/**
 *
 * @author sebastian
 */
@Named(value = "buscadorAdministradorMB")
@RequestScoped
@ManagedBean
public class BuscadorAdministradorMB {

    @EJB
    private UsuarioEJBLocal usuarioEJB;
    @EJB
    private FormularioEJBLocal formularioEJB;

    static final Logger logger = Logger.getLogger(BuscadorJefeAreaMB.class.getName());

    private Usuario usuarioSesion;

    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;

    private HttpServletRequest httpServletRequest2;
    private FacesContext facesContext2;

    private String usuarioSis;
    //para busqueda de nue
    private int nue;

    //busqueda ruc ric y nparte
    private String buscar;
    private String input;

    //busqueda usuario
    private String rut;

    public BuscadorAdministradorMB() {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "BuscadorAdministradorMB");
        /**/
        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        this.facesContext2 = FacesContext.getCurrentInstance();
        this.httpServletRequest2 = (HttpServletRequest) facesContext2.getExternalContext().getRequest();

        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }

        logger.exiting(this.getClass().getName(), "BuscadorAdministradorMB");

    }

    @PostConstruct
    public void loadUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadUsuarioAdministrador");
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioSis);
        logger.exiting(this.getClass().getName(), "loadUsuarioAdmministrador");
    }

    public String buscarUsuario() {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscarUsuarioJefeArea");
        logger.log(Level.INFO, "RUT CAPTURADO:{0}", this.rut);
        Usuario user = usuarioEJB.findUserByRut(rut);

        if (user != null) {
            httpServletRequest.getSession().setAttribute("rut", this.rut);
            httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
            logger.exiting(this.getClass().getName(), "buscarUsuarioJefeArea", "buscadorJefeAreaResultUsuario");
            return "buscadorJefeAreaResultUsuario.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "no existe", "Datos no válidos"));
        logger.info("formulario no encontrado");
        logger.exiting(this.getClass().getName(), "buscarUsuarioJefeArea", "buscarUsuario");
        return "";

    }

    //retorna a la vista para realizar busqueda
    public String buscador() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorAdministrador");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "buscadorAdministrador", "buscador");
        return "buscadorAdministrador?faces-redirect=true";
    }

    public String crearUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorAdministrador");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "buscadorAdministrador", "crearUsuario");
        return "crearUsuarioAdministrador.xhtml?faces-redirect=true";
    }

    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirAdministrador");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirAdministrador", "/indexListo");
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

    public int getNue() {
        return nue;
    }

    public void setNue(int nue) {
        this.nue = nue;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

}
