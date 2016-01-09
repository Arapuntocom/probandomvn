/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.UsuarioEJBLocal;
import ejb.ValidacionEJBLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Named(value = "inicioSesionMB")
@RequestScoped
@ManagedBean
public class InicioSesionMB {

    @EJB
    private UsuarioEJBLocal usuarioEJB;
    @EJB
    private ValidacionEJBLocal validacionEJB;

    static final Logger logger = Logger.getLogger(InicioSesionMB.class.getName());
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    private String user;
    private String pass;

    public InicioSesionMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "InicioSesionMB");
        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        logger.exiting(this.getClass().getName(), "InicioSesionMB");
    }

    //Función para logear al usuario
    public String login() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "Función login");
        logger.log(Level.FINEST, "login1 usuario: {0}", this.user);
        logger.log(Level.FINEST, "login1 p: {0}", this.pass);
        String response = validacionEJB.verificarUsuario(user, pass);

        if (!response.equals("")) {
            httpServletRequest.getSession().setAttribute("cuentaUsuario", this.user);
            //guardo la cuenta de usuario para entregarla a la otra vista
            logger.log(Level.FINEST, "usuario: {0}", this.user);
            logger.exiting(this.getClass().getName(), "login1", response);
            return response;

        } else {
            logger.log(Level.FINEST, "login1 false user: {0}", this.user);
            logger.exiting(this.getClass().getName(), "login1", "");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña inválidos"));
            return "";
        }

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
