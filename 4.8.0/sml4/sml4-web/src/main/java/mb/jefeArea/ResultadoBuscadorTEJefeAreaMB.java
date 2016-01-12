package mb.jefeArea;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.EdicionFormulario;
import entity.Formulario;
import entity.Traslado;
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
@Named(value = "resultadoBuscadorTEJefeAreaMB")
@RequestScoped
@ManagedBean
public class ResultadoBuscadorTEJefeAreaMB {

    @EJB
    private FormularioEJBLocal formularioEJB;
    @EJB
    private UsuarioEJBLocal usuarioEJB;

    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;

    private String usuarioS;
    private Usuario usuarioSesion;

    private int nue;

    private Formulario formulario;

    private List<Traslado> trasladosList;
    private List<EdicionFormulario> edicionesList;

    private int contador = 1;

    private String cambia;

    private List<Traslado> intercalado;

    static final Logger logger = Logger.getLogger(ResultadoBuscadorTEJefeAreaMB.class.getName());

    public ResultadoBuscadorTEJefeAreaMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "ResultadoBuscadorTJefeAreaMB");
        this.trasladosList = new ArrayList<>();
        this.edicionesList = new ArrayList<>();
        this.intercalado = new ArrayList<>();
        facesContext1 = FacesContext.getCurrentInstance();
        httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();

        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("nueF") != null) {
            this.nue = (int) httpServletRequest.getSession().getAttribute("nueF");
            logger.log(Level.FINEST, "todo nue recibido {0}", this.nue);
        }

        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioS = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioS);
        }
        logger.exiting(this.getClass().getName(), "ResultadoBuscadorTJefeAreaMB");
    }

    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CargarDatosJefeArea");
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(this.usuarioS);
        this.trasladosList = formularioEJB.traslados(this.formulario);

        intercalado(trasladosList);
        //trasladosList.remove(trasladosList.size()-1);        
        this.edicionesList = formularioEJB.listaEdiciones(this.nue);

        logger.log(Level.INFO, "formulario ruc {0}", this.formulario.getRuc());
        logger.log(Level.FINEST, "todos cant traslados {0}", this.trasladosList.size());
        logger.exiting(this.getClass().getName(), "CargarDatosJefeArea");
    }

    public String cambio() {

        if (contador == 1) {
            cambia = "Entrega";
            contador++;
        } else if (contador == 2) {
            cambia = "Recibe";
            contador++;
        } else {
            contador = 2;
            cambia = "Entrega";
        }

        return cambia;
    }

    //redirige a nuevamente a la vista de busqueda.
    public String nuevaBusqueda() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "nuevaBusquedaJA");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
        logger.exiting(this.getClass().getName(), "nuevaBusquedaJA", "buscadorJefeArea");
        return "buscadorJefeArea.xhtml?faces-redirect=true";
    }

    private void intercalado(List<Traslado> traslados) {

        for (int i = 0; i < traslados.size(); i++) {

            for (int j = 0; j < 2; j++) {
                Traslado tras = new Traslado();
                tras.setFechaEntrega(traslados.get(i).getFechaEntrega());
                tras.setFormularioNUE(traslados.get(i).getFormularioNUE());
                tras.setObservaciones(traslados.get(i).getObservaciones());
                tras.setTipoMotivoidMotivo(traslados.get(i).getTipoMotivoidMotivo());

                if (j == 0) {
                    tras.setUsuarioidUsuarioEntrega(traslados.get(i).getUsuarioidUsuarioEntrega());

                } else {
                    tras.setUsuarioidUsuarioEntrega(traslados.get(i).getUsuarioidUsuarioRecibe());

                }
                intercalado.add(tras);

            }

        }
        System.out.println(intercalado.toString());
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

    public String crearUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorJefeArea");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "buscadorJefeArea", "crearUsuario");
        return "crearUsuarioJefeArea.xhtml?faces-redirect=true";
    }

    public String semaforo() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "buscadorJefeArea");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
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

    public int getNue() {
        return nue;
    }

    public void setNue(int nue) {
        this.nue = nue;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public List<Traslado> getTrasladosList() {
        return trasladosList;
    }

    public void setTrasladosList(List<Traslado> trasladosList) {
        this.trasladosList = trasladosList;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public List<EdicionFormulario> getEdicionesList() {
        return edicionesList;
    }

    public void setEdicionesList(List<EdicionFormulario> edicionesList) {
        this.edicionesList = edicionesList;
    }

    public String getCambia() {
        return cambia;
    }

    public void setCambia(String cambia) {
        this.cambia = cambia;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public List<Traslado> getIntercalado() {
        return intercalado;
    }

    public void setIntercalado(List<Traslado> intercalado) {
        this.intercalado = intercalado;
    }

}
