/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.chofer;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.Formulario;
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

/**
 *
 * @author sebastian
 */
@Named(value = "buscadorChofer")
@RequestScoped
@ManagedBean
public class BuscadorChoferMB {

    @EJB
    private UsuarioEJBLocal usuarioEJB;
    @EJB
    private FormularioEJBLocal formularioEJB;

    static final Logger logger = Logger.getLogger(BuscadorChoferMB.class.getName());

    private Usuario usuarioSesion;

    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;

    private String usuarioSis;
    private int nue;

    public BuscadorChoferMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "BuscadorChoferMB");
        /**/
        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }
        logger.exiting(this.getClass().getName(), "BuscadorChoferMB");
    }

    @PostConstruct
    public void loadUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadUsuarioChofer");
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioSis);
        logger.exiting(this.getClass().getName(), "loadUsuarioChofer");
    }

    public String buscarFormulario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscarFormularioChofer");
        logger.log(Level.INFO, "NUE CAPTURADO:{0}", this.nue);
        Formulario formulario = formularioEJB.findFormularioByNue(this.nue);

        if (formulario != null) {
            httpServletRequest.getSession().setAttribute("nueF", this.nue);
            httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
            
            logger.exiting(this.getClass().getName(), "buscarFormulario", "editarChoferET");
            return "editarChoferET.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "no existe", "Datos no válidos"));
        logger.info("formulario no encontrado");
        logger.exiting(this.getClass().getName(), "buscarFormularioChofer", "");
        return "";
    }

    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirChofer");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirChofer", "/indexListo");
        return "/indexListo?faces-redirect=true";
    }

    public String getUsuarioSis() {
        return usuarioSis;
    }

    public void setUsuarioSis(String usuarioSis) {
        this.usuarioSis = usuarioSis;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public int getNue() {
        return nue;
    }

    public void setNue(int nue) {
        this.nue = nue;
    }
}
