/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.UsuarioEJBLocal;
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
 
    public String login() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "login");
        logger.log(Level.FINEST, "login usuario: {0}", this.user);
        logger.log(Level.FINEST, "login p: {0}", this.pass);
        boolean response = usuarioEJB.verificarUsuario(user, pass);
 
        if (response == true) {
            httpServletRequest.getSession().setAttribute("cuentaUsuario", this.user);
            logger.log(Level.FINEST, "usuario: {0}", this.user);
            logger.exiting(this.getClass().getName(), "login", "digitadorFormularioHU11");
            return "digitadorFormularioHU11.xhtml?faces-redirect=true";
           
        } else {
            logger.log(Level.FINEST, "login false user: {0}", this.user);
            logger.exiting(this.getClass().getName(), "login", "");
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
