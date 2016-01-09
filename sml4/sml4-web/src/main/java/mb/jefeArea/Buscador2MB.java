/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
 
/**
 *
 * @author Aracelly
 */
 
@Named(value = "buscador2MB")
@RequestScoped
public class Buscador2MB {
 
    @EJB
    private UsuarioEJBLocal usuarioEJB;
    @EJB
    private FormularioEJBLocal formularioEJB;
 
    static final Logger logger = Logger.getLogger(Buscador2MB.class.getName());
 
    private Usuario usuarioSesion;
 
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
 
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;
 
    private String usuarioSis;
    private int nue;
   
    //tipo de valor a buscar (rut rit o parte)
    private String seleccion;
    private String valorBuscar;
   
    private List<Formulario> formulariosList;
 
    public Buscador2MB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "BusquedaJefeAreaMB");
        /**/
        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
 
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }
       
        this.formulariosList = new ArrayList<>();
 
        logger.exiting(this.getClass().getName(), "BusquedaJefeAreaMB");
    }
 
    @PostConstruct
    public void loadUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadUsuarioJefeArea");
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioSis);
        logger.exiting(this.getClass().getName(), "loadUsuarioJefeArea");
    }
 
    public String buscar() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscarJefeArea");
        logger.log(Level.INFO, "NUE CAPTURADO:{0}", this.nue);
        formulariosList = formularioEJB.findByNParteRR(valorBuscar, seleccion);
       
        if(formulariosList.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Sin resultados", "Sin resultados"));        
        }
        logger.exiting(this.getClass().getName(), "buscarJefeArea", "buscadorJefeArea");
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
 
    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirJefeArea");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirJefeArea", "/indexListo");
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
 
    public String getSeleccion() {
        return seleccion;
    }
 
    public void setSeleccion(String seleccion) {
        this.seleccion = seleccion;
    }
 
    public String getValorBuscar() {
        return valorBuscar;
    }
 
    public void setValorBuscar(String valorBuscar) {
        this.valorBuscar = valorBuscar;
    }  
 
    public List<Formulario> getFormulariosList() {
        return formulariosList;
    }
 
    public void setFormulariosList(List<Formulario> formulariosList) {
        this.formulariosList = formulariosList;
    }
   
}