/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.digitador;

import ejb.FormularioDigitadorLocal;
import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.EdicionFormulario;
import entity.Formulario;
import entity.FormularioEvidencia;
import entity.Traslado;
import entity.Usuario;
import java.util.ArrayList;
import java.util.Date;
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
 * @author Aracelly
 */
@Named(value = "todoMB")
@RequestScoped
@ManagedBean
public class TodoMB {

    @EJB
    private UsuarioEJBLocal usuarioEJB;

   @EJB
    private FormularioDigitadorLocal formularioDigitador;

   @EJB
    private FormularioEJBLocal formularioEJB;
   
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;

    private HttpServletRequest httpServletRequest2;
    private FacesContext facesContext2;

    private String usuarioEntrega;
    private String usuarioEntregaUnidad;
    private String usuarioEntregaCargo;
    private String usuarioEntregaRut;
    private String usuarioRecibe;
    private String usuarioRecibeUnidad;
    private String usuarioRecibeCargo;
    private String usuarioRecibeRut;
    private String motivo;
    private String observacionesT;
    private Date fechaT;

    private String usuarioSis;
    private Usuario usuarioSesion;

    private int nue;

    private String rutInicia;
    private Usuario usuarioInicia;
    private Formulario formulario;

    private List<FormularioEvidencia> evidenciasList;
    private String evidencia;
    
    private List<Traslado> trasladosList;
    
    private boolean bloqueada;
    
    private int contador = 1;
    private String cambia;
    private List<Traslado> intercalado;

    static final Logger logger = Logger.getLogger(TodoMB.class.getName());

    public TodoMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "TodoMB");
        this.trasladosList = new ArrayList<>();

        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("nueF") != null) {
            this.nue = (int) httpServletRequest.getSession().getAttribute("nueF");
            logger.log(Level.FINEST, "todo nue recibido {0}", this.nue);
        }
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }

        facesContext2 = FacesContext.getCurrentInstance();
        httpServletRequest2 = (HttpServletRequest) facesContext2.getExternalContext().getRequest();
        if (httpServletRequest2.getSession().getAttribute("rutInicia") != null) {
            this.rutInicia = (String) httpServletRequest2.getSession().getAttribute("rutInicia");
            logger.log(Level.FINEST, "RutInicia recibido {0}", this.rutInicia);
        }
        this.evidenciasList = new ArrayList();
        this.usuarioInicia = new Usuario();
        this.usuarioSesion = new Usuario();
        logger.exiting(this.getClass().getName(), "TodoMB");
    }

    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "cargarDatosDigitador");
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioSis);
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
        this.trasladosList = formularioEJB.traslados(this.formulario);
        this.usuarioInicia = usuarioEJB.findUserByRut(rutInicia);
                
        this.evidenciasList = formularioEJB.findEvidenciaFormularioByFormulario(formulario);
        if (!evidenciasList.isEmpty()) {
            this.evidencia = evidenciasList.get(0).getEvidenciaidEvidencia().getNombreEvidencia();
        }
        
        intercalado = new ArrayList<>();
         intercalado(trasladosList);
        
        this.bloqueada = formulario.getBloqueado();
        logger.exiting(this.getClass().getName(), "cargarDatosDigitador");
    }

    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirDigitador");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirDigitador", "/indexListo");
        return "/indexListo?faces-redirect=true";
    }
    
    //redirecciona a la pagina para iniciar cadena de custodia
    public String nuevaCadena(){
        logger.entering(this.getClass().getName(), "iniciarCadena");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);        
        logger.exiting(this.getClass().getName(), "iniciarCadena", "digitadorFormularioHU11");
        return "digitadorFormularioHU11?faces-redirect=true";
    }
    
     public String agregarTraslado() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "agregarTrasladoDigitador");
        logger.log(Level.FINEST, "rut usuario entrega {0}", this.usuarioEntrega);
        logger.log(Level.FINEST, "rut usuario recibe {0}", this.usuarioRecibe);
        logger.log(Level.FINEST, "rut motivo {0}", this.motivo);
        String resultado = formularioDigitador.crearTraslado(formulario, usuarioInicia, usuarioRecibe, usuarioRecibeCargo, usuarioRecibeRut, fechaT, observacionesT, motivo, usuarioSesion);
        if (resultado.equals("Exito")) {
            httpServletRequest.getSession().setAttribute("nueF", this.nue);
            httpServletRequest.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
            httpServletRequest.getSession().setAttribute("rutInicia", this.rutInicia);
            logger.exiting(this.getClass().getName(), "agregarTrasladoDigitador", "todoHU11?faces-redirect=true");
            return "todoHU11?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado, "Uno o más datos inválidos"));
        logger.exiting(this.getClass().getName(), "agregarTrasladoDigitador", "");
        return "";
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

    public Usuario getUsuarioInicia() {
        return usuarioInicia;
    }

    public void setUsuarioInicia(Usuario usuarioInicia) {
        this.usuarioInicia = usuarioInicia;
    }

    public List<FormularioEvidencia> getEvidenciasList() {
        return evidenciasList;
    }

    public void setEvidenciasList(List<FormularioEvidencia> evidenciasList) {
        this.evidenciasList = evidenciasList;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
    }

    public String getCambia() {
        return cambia;
    }

    public void setCambia(String cambia) {
        this.cambia = cambia;
    }

    public List<Traslado> getIntercalado() {
        return intercalado;
    }

    public void setIntercalado(List<Traslado> intercalado) {
        this.intercalado = intercalado;
    }
    
    public String getUsuarioEntrega() {
        return usuarioEntrega;
    }

    public void setUsuarioEntrega(String usuarioEntrega) {
        this.usuarioEntrega = usuarioEntrega;
    }

    public String getUsuarioEntregaUnidad() {
        return usuarioEntregaUnidad;
    }

    public void setUsuarioEntregaUnidad(String usuarioEntregaUnidad) {
        this.usuarioEntregaUnidad = usuarioEntregaUnidad;
    }

    public String getUsuarioEntregaCargo() {
        return usuarioEntregaCargo;
    }

    public void setUsuarioEntregaCargo(String usuarioEntregaCargo) {
        this.usuarioEntregaCargo = usuarioEntregaCargo;
    }

    public String getUsuarioEntregaRut() {
        return usuarioEntregaRut;
    }

    public void setUsuarioEntregaRut(String usuarioEntregaRut) {
        this.usuarioEntregaRut = usuarioEntregaRut;
    }

    public String getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(String usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    public String getUsuarioRecibeUnidad() {
        return usuarioRecibeUnidad;
    }

    public void setUsuarioRecibeUnidad(String usuarioRecibeUnidad) {
        this.usuarioRecibeUnidad = usuarioRecibeUnidad;
    }

    public String getUsuarioRecibeCargo() {
        return usuarioRecibeCargo;
    }

    public void setUsuarioRecibeCargo(String usuarioRecibeCargo) {
        this.usuarioRecibeCargo = usuarioRecibeCargo;
    }

    public String getUsuarioRecibeRut() {
        return usuarioRecibeRut;
    }

    public void setUsuarioRecibeRut(String usuarioRecibeRut) {
        this.usuarioRecibeRut = usuarioRecibeRut;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservacionesT() {
        return observacionesT;
    }

    public void setObservacionesT(String observacionesT) {
        this.observacionesT = observacionesT;
    }

    public Date getFechaT() {
        return fechaT;
    }

    public void setFechaT(Date fechaT) {
        this.fechaT = fechaT;
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

}
