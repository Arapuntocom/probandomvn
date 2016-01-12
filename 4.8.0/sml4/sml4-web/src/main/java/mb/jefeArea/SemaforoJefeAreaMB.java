//nombre: SemaforoJefeAreaMB
package mb.jefeArea;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.Formulario;
import entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import static mb.jefeArea.BuscadorJefeAreaMB.logger;

/**
 *
 * @author sebastian
 */
@Named(value = "semaforoJefeAreaMB")
@RequestScoped
@ManagedBean
public class SemaforoJefeAreaMB {

	@EJB
	private UsuarioEJBLocal usuarioEJB;
	@EJB
	private FormularioEJBLocal formularioEJB;

	private Usuario usuarioSesion;

	private HttpServletRequest httpServletRequest;
	private FacesContext facesContext;

	private HttpServletRequest httpServletRequest1;
	private FacesContext facesContext1;

	private String usuarioSis;

	private List<Formulario> formularios;

	public SemaforoJefeAreaMB() {

    	logger.setLevel(Level.ALL);
    	logger.entering(this.getClass().getName(), "BusquedaJefeAreaMB");

    	this.formularios = new ArrayList();
    	this.facesContext = FacesContext.getCurrentInstance();
    	this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

    	this.facesContext1 = FacesContext.getCurrentInstance();
    	this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
    	if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
        	this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
        	logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
    	}

    	logger.exiting(this.getClass().getName(), "BusquedaJefeAreaMB");
	}

	@PostConstruct
	public void loadDatos() {
    	logger.setLevel(Level.ALL);
    	logger.entering(this.getClass().getName(), "loadDatosJefeArea");
    	this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioSis);
    	this.formularios = formularioEJB.findAllFormularios();
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

	public List<Formulario> getFormularios() {
    	return formularios;
	}

	public void setFormularios(List<Formulario> formularios) {
    	this.formularios = formularios;
	}

}


