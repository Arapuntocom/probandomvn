package mb.jefeArea;
 
import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.Area;
import entity.Cargo;
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
 * @author sebastian
 */
@Named(value = "crearUsuarioJefeAreaMB")
@RequestScoped
@ManagedBean
public class CrearUsuarioJefeAreaMB {
 
    @EJB
    private UsuarioEJBLocal usuarioEJB;
    @EJB
    private FormularioEJBLocal formularioEJB;
 
    static final Logger logger = Logger.getLogger(CrearUsuarioJefeAreaMB.class.getName());
 
    private Usuario usuarioSesion;
 
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
 
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;
 
    private String usuarioSis;
 
    private String rut;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String pass;
    private String mail;
    private String cuentaUsuario;
    private String cargo;
    private String area;
 
    private List<String> cargos;
 
    private List<String> areas;
 
    public CrearUsuarioJefeAreaMB() {
 
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CrearUsuarioJefeAreaMB");
 
        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
       
        this.cargos = new ArrayList();
        this.areas = new ArrayList();
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }
 
        logger.exiting(this.getClass().getName(), "CrearUsuarioJefeAreaMB");
    }
 
    @PostConstruct
    public void loadDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadDatosJefeArea");
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioSis);
        //Cargando cargos
        List<Cargo> cargos1 = formularioEJB.findAllCargos();
       
        for(Cargo cargo : cargos1){
            this.cargos.add(cargo.getNombreCargo());
        }
       
        List<Area> areas1 = formularioEJB.findAllAreas(); 
       
       
        for(Area area : areas1){
            this.areas.add(area.getNombreArea());
        }
 
        //Cargando areas
        logger.exiting(this.getClass().getName(), "loadDatosJefeArea");
    }
 
    public String crearUsuario() {
 
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CrearUsuarioJefeAreaMB");
        String response = usuarioEJB.crearUsuario(nombreUsuario, apellidoUsuario, rut, pass, mail, cuentaUsuario, cargo, area);
 
        if (response.equals("Exito")) {
            httpServletRequest.getSession().setAttribute("rut", this.rut);
            httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
            logger.exiting(this.getClass().getName(), "crearUsuario", "crearUsuarioResultJefeArea");
            System.out.println("SE PUDO CREAR");
            return "crearUsuarioResultJefeArea?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, response, ""));
        logger.info("No se pudo crear el usuario");
        System.out.println("NO SE PUDO CREAR");
        logger.exiting(this.getClass().getName(), "CrearUsuarioJefeAreaMB", "crearUsuario");
        return "";
 
    }
 
   
    //retorna a la vista para realizar busqueda
    public String buscador() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorJefeArea");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "buscadorJefeArea", "buscadorJefeArea");
        return "buscadorJefeArea?faces-redirect=true";
    }
   
   
    public String crearUsuario1(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorJefeArea");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "buscadorJefeArea", "crearUsuario");
       return "crearUsuarioJefeArea.xhtml?faces-redirect=true";
    }
   
    public String semaforo(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorJefeArea");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
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
 
    public String getNombreUsuario() {
        return nombreUsuario;
    }
 
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
 
    public String getApellidoUsuario() {
        return apellidoUsuario;
    }
 
    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }
 
    public String getPass() {
        return pass;
    }
 
    public void setPass(String pass) {
        this.pass = pass;
    }
 
    public String getMail() {
        return mail;
    }
 
    public void setMail(String mail) {
        this.mail = mail;
    }
 
    public String getCuentaUsuario() {
        return cuentaUsuario;
    }
 
    public void setCuentaUsuario(String cuentaUsuario) {
        this.cuentaUsuario = cuentaUsuario;
    }
 
    public String getCargo() {
        return cargo;
    }
 
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
 
    public String getArea() {
        return area;
    }
 
    public void setArea(String area) {
        this.area = area;
    }
 
    public List<String> getCargos() {
        return cargos;
    }
 
    public void setCargos(List<String> cargos) {
        this.cargos = cargos;
    }
 
    public List<String> getAreas() {
        return areas;
    }
 
    public void setAreas(List<String> areas) {
        this.areas = areas;
    }
 
   
 
}