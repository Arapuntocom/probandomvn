/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.tecnico;

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
 * @author Aracelly
 */
@Named(value = "editarTecnicoETMB")
@RequestScoped
@ManagedBean
public class EditarTecnicoETMB {

    @EJB
    private UsuarioEJBLocal usuarioEJB;

    @EJB
    private FormularioEJBLocal formularioEJB;

    //Para obtener nue
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;
    private int nue;
    private Formulario formulario;

    //Para obtener el usuario
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    private String usuarioS;
    private Usuario usuarioSesion;

    private String observacionEdicion;
    
    //Listado de  ediciones realizadas
    List<EdicionFormulario> ediciones;
    List<Traslado> traslados;
    
    
    //para habilitar la edicion de estos campos.
    private boolean isRit;
    private boolean isRuc;
    private boolean isParte;
    
    //para registrar contenido de la edicion
    private int parte;
    private String ruc;
    private String rit;
  
    
    static final Logger logger = Logger.getLogger(EditarTecnicoETMB.class.getName());
    
    public EditarTecnicoETMB() {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "EditarTecnicoETMB");
        /**/
        this.ediciones = new ArrayList();
        this.traslados = new ArrayList();
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();

        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        if (httpServletRequest1.getSession().getAttribute("nueF") != null) {
            this.nue = (int) httpServletRequest1.getSession().getAttribute("nueF");
            logger.log(Level.FINEST, "Nue recibido {0}", this.nue);
        }

        if (httpServletRequest.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioS = (String) httpServletRequest.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Cuenta Usuario recibido {0}", this.usuarioS);
        }

        this.isRit = true;
        this.isRuc = true;
        this.isParte = true;
       
        logger.exiting(this.getClass().getName(), "EditarTecnicoETMB");
    }

    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "cargarDatosTecnico");
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioS);
        this.traslados = formularioEJB.traslados(formulario);
        this.ediciones = formularioEJB.listaEdiciones(this.nue);
        
        if(formulario.getNumeroParte() == 0){
            this.isParte = false;
        }
        if(formulario.getRuc()==null || formulario.getRuc().equals("")){
            this.isRuc = false;
        }
        if(formulario.getRit()==null || formulario.getRit().equals("")){
            this.isRit = false;
        }       
        
        logger.exiting(this.getClass().getName(), "cargarDatosTecnico");
    }
    
    public String editarFormulario(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "editarFormularioTecnico");    
        
        if(formulario.getNumeroParte() > 0 && this.isParte == false){  
            parte = formulario.getNumeroParte();
            logger.log(Level.INFO, "MB parte -> {0}", parte);
            isParte = true;
        }
        if(formulario.getRuc() != null && !formulario.getRuc().equals("") && this.isRuc == false){
            ruc = formulario.getRuc();
            logger.log(Level.INFO, "MB ruc -> {0}", ruc);
            isRuc = true;
        }
        if(formulario.getRit() != null && !formulario.getRit().equals("") && this.isRit == false){            
            rit = formulario.getRit();
            logger.log(Level.INFO, "MB rit -> {0}", rit);
            isRit = true;
        }  
               
        String response = formularioEJB.edicionFormulario(formulario, observacionEdicion, usuarioSesion, parte, ruc, rit);
        httpServletRequest.getSession().setAttribute("nueF", this.nue);
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
        if(response.equals("Exito")){
            logger.exiting(this.getClass().getName(), "editarFormularioTecnico", "todoTecnico");
            return "todoTecnico.xhtml?faces-redirect=true";
        }
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un problema al guardar los cambios, por favor intente más tarde.", "error al editar"));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, response, "error al editar"));
        logger.exiting(this.getClass().getName(), "editarFormularioTecnico", "");
        return "";
    }
    
    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirTecnico");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirTecnico", "/indexListo");
        return "/indexListo.xhtml?faces-redirect=true";
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

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public String getObservacionEdicion() {
        return observacionEdicion;
    }

    public void setObservacionEdicion(String observacionEdicion) {
        this.observacionEdicion = observacionEdicion;
    }

    public List<EdicionFormulario> getEdiciones() {
        return ediciones;
    }

    public void setEdiciones(List<EdicionFormulario> ediciones) {
        this.ediciones = ediciones;
    }

    public List<Traslado> getTraslados() {
        return traslados;
    }

    public void setTraslados(List<Traslado> traslados) {
        this.traslados = traslados;
    }

    public boolean isIsRit() {
        return isRit;
    }

    public void setIsRit(boolean isRit) {
        this.isRit = isRit;
    }

    public boolean isIsRuc() {
        return isRuc;
    }

    public void setIsRuc(boolean isRuc) {
        this.isRuc = isRuc;
    }

    public boolean isIsParte() {
        return isParte;
    }

    public void setIsParte(boolean isParte) {
        this.isParte = isParte;
    }
    
    
}
