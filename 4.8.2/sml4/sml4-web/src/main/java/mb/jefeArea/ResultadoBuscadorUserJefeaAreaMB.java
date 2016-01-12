//nombre: ResultadoBuscadorUserJefeAreaMB
package mb.jefeArea;

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

/**
 *
 * @author sebastian
 */
@Named(value = "resultadoBuscadorUserJefeaAreaMB")
@RequestScoped
@ManagedBean
public class ResultadoBuscadorUserJefeaAreaMB {

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
    
    private String estadoUsuarioEspanol;

    private static final Logger logger = Logger.getLogger(ResultadoBuscadorUserJefeaAreaMB.class.getName());

    public ResultadoBuscadorUserJefeaAreaMB() {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "ResultadoBuscadorUserJefeaAreaMB");

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
        logger.exiting(this.getClass().getName(), "ResultadoBuscadorUserJefeaAreaMB");

    }

    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CargarDatosJefeArea");
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(this.userSesion);
        this.usuarioBuscado = usuarioEJB.findUserByRut(this.rut);
        latino();
                
        
        logger.log(Level.INFO, "Nombre usuario {0}", this.usuarioBuscado.getNombreUsuario());
        logger.log(Level.FINEST, "Rut usuario {0}", this.usuarioBuscado.getRutUsuario());
        logger.exiting(this.getClass().getName(), "CargarDatosJefeArea");
    }

    private void latino(){
    boolean estado = this.usuarioBuscado.getEstadoUsuario();
        if(estado)
            estadoUsuarioEspanol = "Habilitado";
        else
            estadoUsuarioEspanol = "Deshabilitado";
    }
    
    public String habilitarUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "habilitarUsuario");
        boolean response = usuarioEJB.edicionEstadoUsuario(this.rut, "Activo");

        if (response == true) {
            httpServletRequest.getSession().setAttribute("rut", this.rut);
            httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.userSesion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario ha sido habilitado correctamente", ""));
            logger.exiting(this.getClass().getName(), "habilitarUsuario", "buscadorJefeAreaResultUsuario");
            latino();
            return "buscadorJefeAreaResultUsuario.xhtml?faces-redirect=true";
            //return "";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario ya se encuentra habilitado", ""));
        logger.info("Usuario ya se encuentra habilitado");
        logger.exiting(this.getClass().getName(), "habilitarUsuario", "habilitarUsuario");

        return "";
    }

    public String deshabilitarUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "deshabilitarUsuario");
        boolean response = usuarioEJB.edicionEstadoUsuario(this.rut, "Inactivo");
        if (response == true) {
            httpServletRequest.getSession().setAttribute("rut", this.rut);
            httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.userSesion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario ha sido deshabilitado correctamente", ""));
            logger.exiting(this.getClass().getName(), "deshabilitarUsuario", "buscadorJefeAreaResultUsuario");
            return "buscadorJefeAreaResultUsuario.xhtml?faces-redirect=true";
           // return "";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario ya se encuentra deshabilitado", ""));
        logger.info("Usuario ya se encuentra deshabilitado");
        logger.exiting(this.getClass().getName(), "deshabilitarUsuario", "deshabilitarUsuario");
        return "";
    }

    //retorna a la vista para realizar busqueda
    public String buscador() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorJefeArea");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.userSesion);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "buscadorJefeArea", "buscadorJefeArea");
        return "buscadorJefeArea?faces-redirect=true";
    }

    public String crearUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorJefeArea");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.userSesion);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "buscadorJefeArea", "crearUsuario");
        return "crearUsuarioJefeArea.xhtml?faces-redirect=true";
    }

    public String semaforo() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorJefeArea");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.userSesion);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "buscadorJefeArea", "semaforo");
        return "semaforoJefeArea.xhtml?faces-redirect=true";
    }

    public String estadisticas() {
        return "";
    }

    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirJefeArea");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirJefeArea", "/indexListo");
        return "/indexListo?faces-redirect=true";
    }

    public String getEstadoUsuarioEspanol() {
        return estadoUsuarioEspanol;
    }

    public void setEstadoUsuarioEspanol(String estadoUsuarioEspanol) {
        this.estadoUsuarioEspanol = estadoUsuarioEspanol;
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
