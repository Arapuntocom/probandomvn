 //nombre: ResultadoCrearJefeAreaMB
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
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author sebastian
 */
@Named(value = "resultadoCrearJefeAreaMB")
@RequestScoped
@ManagedBean
public class ResultadoCrearJefeAreaMB {

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

	public ResultadoCrearJefeAreaMB() {

    	logger.setLevel(Level.ALL);
    	logger.entering(this.getClass().getName(), "ResultadoCrearJefeAreaMB");

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

    	logger.exiting(this.getClass().getName(), "ResultadoCrearJefeAreaMB");
	}

	@PostConstruct
	public void loadDatos() {
    	logger.setLevel(Level.ALL);
    	logger.entering(this.getClass().getName(), "loadDatosJefeArea");
    	System.out.println("CARGANDO DATOS DESDE LE POST CONSTRUCTOR");
    	this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(this.usuarioSis);
    	System.out.println("USUARIO: "+this.usuarioSis);
    	this.userCreado = usuarioEJB.findUserByRut(this.rut);
    	System.out.println("rut: "+this.rut);
    	System.out.println("TERMINO DE CARGAR DATOS DESDE EL POST CONSTRUCTOR");
    	//Cargando areas
   	 
    	logger.exiting(this.getClass().getName(), "loadDatosJefeArea");
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

	public String crearUsuario() {
    	logger.setLevel(Level.ALL);
    	logger.entering(this.getClass().getName(), "buscadorJefeArea");
    	httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
    	logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
    	logger.exiting(this.getClass().getName(), "buscadorJefeArea", "crearUsuario");
    	return "crearUsuarioJefeArea.xhtml?faces-redirect=true";
	}

	public String semaforo() {
    	logger.setLevel(Level.ALL);
    	logger.entering(this.getClass().getName(), "buscadorJefeArea");
    	httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
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


