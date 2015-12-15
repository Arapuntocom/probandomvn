/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

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
 * @author Aracelly
 */
@Named(value = "crearFormularioMB")
@RequestScoped
@ManagedBean
public class CrearFormularioMB {
    @EJB
    private UsuarioEJBLocal usuarioEJB;
    @EJB
    private FormularioEJBLocal formularioEJB;

    static final Logger logger = Logger.getLogger(CrearFormularioMB.class.getName());
    
    private Formulario formulario;
    private Usuario usuarioInicia;
    private Usuario usuarioSesion;
 
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
 
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;
 
    private String usuarioSis;
    private String cargoInicia;
 
    public CrearFormularioMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CrearFormularioMB");
        this.formulario = new Formulario();
        this.usuarioInicia = new Usuario();
        /**/
        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
       
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }
 
        logger.exiting(this.getClass().getName(), "CrearFormularioMB"); 
    }
   
    @PostConstruct
    public void loadUsuario(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadUsuario");       
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioSis); 
        logger.exiting(this.getClass().getName(), "loadUsuario");   
    }
 
    public String iniciarFormulario() { 
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "iniciarFormulario");
        logger.log(Level.FINEST, "formulario nue {0}", this.formulario.getNue());
        logger.log(Level.FINEST, "usuario inicia rut {0}", this.usuarioInicia.getRutUsuario());
        httpServletRequest.getSession().setAttribute("nueF", this.formulario.getNue());
        logger.log(Level.FINEST, "formulario fecha {0}", this.formulario.getFechaOcurrido());
        logger.log(Level.FINEST, "usuario inicia cargo {0}", this.cargoInicia);
         String resultado = formularioEJB.crearFormulario(formulario, usuarioInicia, usuarioSesion);
        if(resultado.equals("Exito")            ){
            logger.exiting(this.getClass().getName(), "iniciarFormulario", "forAddTHU11");
            return "forAddTHU11?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado, "Datos no válidos"));
        logger.exiting(this.getClass().getName(), "iniciarFormulario", "");
        return "";
    }
    
    public String salir(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salir");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "salir", "indexListo");
        return "indexListo?faces-redirect=true";
    }
    
    public String getCargoInicia() {
        return cargoInicia;
    }

    public void setCargoInicia(String cargoInicia) {
        this.cargoInicia = cargoInicia;
    } 
    public Formulario getFormulario() {
        return formulario;
    }
 
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }
 
    public Usuario getUsuarioInicia() {
        return usuarioInicia;
    }
 
    public void setUsuarioInicia(Usuario usuarioInicia) {
        this.usuarioInicia = usuarioInicia;
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
    
}
