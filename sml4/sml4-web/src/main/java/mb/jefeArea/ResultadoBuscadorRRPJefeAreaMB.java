package mb.jefeArea;
 
import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.Formulario;
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
import static mb.jefeArea.BuscadorJefeAreaMB.logger;
 
 
/**
 *
 * @author sebastian
 */
@Named(value = "resultadoBuscadorRRJefeAreaMB")
@RequestScoped
@ManagedBean
public class ResultadoBuscadorRRPJefeAreaMB {
 
    @EJB
    private FormularioEJBLocal formularioEJB;
    @EJB
    private UsuarioEJBLocal usuarioEJB;
 
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
 
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;
 
    private HttpServletRequest httpServletRequest2;
    private FacesContext facesContext2;
 
    private String usuarioS;
    private Usuario usuarioSesion;
 
    private String input;
    private String buscar;
 
    private List<Formulario> formularios;
 
    static final Logger logger = Logger.getLogger(ResultadoBuscadorRRPJefeAreaMB.class.getName());
 
    public ResultadoBuscadorRRPJefeAreaMB() {
 
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "ResultadoBuscadorRRJefeAreMB");
 
        this.formularios = new ArrayList<>();
 
        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
 
        this.facesContext2 = FacesContext.getCurrentInstance();
        this.httpServletRequest2 = (HttpServletRequest) facesContext2.getExternalContext().getRequest();
 
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioS = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioS);
        }
 
        if (httpServletRequest2.getSession().getAttribute("buscar") != null) {
            this.buscar = (String) httpServletRequest2.getSession().getAttribute("buscar");
            logger.log(Level.FINEST, "Buscar recibido {0}", this.buscar);
        }
 
        if (httpServletRequest.getSession().getAttribute("input") != null) {
            this.input = (String) httpServletRequest.getSession().getAttribute("input");
            logger.log(Level.FINEST, "Input recibido {0}", this.input);
        }
 
        logger.exiting(this.getClass().getName(), "ResultadoBuscadorRRJefeAreMB");
 
    }
 
    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CargarDatosJefeArea");
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(this.usuarioS);
        this.formularios = formularioEJB.findByNParteRR(this.input, this.buscar);
        logger.exiting(this.getClass().getName(), "CargarDatosJefeArea");
    }
   
     //retorna a la vista para realizar busqueda
    public String buscador() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorJefeArea");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "buscadorJefeArea", "buscadorJefeArea");
        return "buscadorJefeArea?faces-redirect=true";
    }
   
   
    public String crearUsuario(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorJefeArea");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "buscadorJefeArea", "crearUsuario");
       return "crearUsuarioJefeArea.xhtml?faces-redirect=true";
    }
   
    public String semaforo(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorJefeArea");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "buscadorJefeArea", "semaforo");
        return "semaforoJefeArea.xhtml?faces-redirect=true";
    }
   
    public String estadisticas(){
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
     
     public String buscarFormulario(int nue) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscarFormularioJefeArea");
        logger.log(Level.INFO, "NUE CAPTURADO:{0}", nue);
        Formulario formulario = formularioEJB.findFormularioByNue(nue);
 
        if (formulario != null) {
            httpServletRequest.getSession().setAttribute("nueF", nue);
            httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
            logger.exiting(this.getClass().getName(), "buscarFormularioJefeArea", "buscadorJefeAreaResultTE");
            return "buscadorJefeAreaResultTE.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "no existe", "Datos no válidos"));
        logger.info("formulario no encontrado");
        logger.exiting(this.getClass().getName(), "buscarFormularioJefeArea", "buscadorJefeArea");
        return "";
    }
   
    public String getUsuarioS() {
        return usuarioS;
    }
 
    public void setUsuarioS(String usuarioS) {
        this.usuarioS = usuarioS;
    }
 
    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }
 
    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }
 
    public String getInput() {
        return input;
    }
 
    public void setInput(String input) {
        this.input = input;
    }
 
    public String getBuscar() {
        return buscar;
    }
 
    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }
 
    public List<Formulario> getFormularios() {
        return formularios;
    }
 
    public void setFormularios(List<Formulario> formularios) {
        this.formularios = formularios;
    }
 
}
