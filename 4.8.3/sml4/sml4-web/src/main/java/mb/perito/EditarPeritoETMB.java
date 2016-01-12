/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.perito;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import ejb.ValidacionVistasMensajesEJBLocal;
import entity.EdicionFormulario;
import entity.Evidencia;
import entity.Formulario;
import entity.FormularioEvidencia;
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
@Named(value = "editarPeritoETMB")
@RequestScoped
@ManagedBean
public class EditarPeritoETMB {

    @EJB
    private ValidacionVistasMensajesEJBLocal validacionVistasMensajesEJB;

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

    //Listado de  edicionesList realizadas
    private List<EdicionFormulario> edicionesList;
    private List<Traslado> trasladosList;
    
    private List<FormularioEvidencia> evidenciasList;
     private String evidencia;
    //para deshabilitar la edicion de estos campos.
    private boolean isRit;
    private boolean isRuc;
    private boolean isParte;

    //para registrar contenido de la edicion
    private int parte;
    private String ruc;
    private String rit;
    
    private int contador=1;

    private String cambia;
    
    private List<Traslado> intercalado;

    static final Logger logger = Logger.getLogger(EditarPeritoETMB.class.getName());

    public EditarPeritoETMB() {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "EditarPeritoETMB");
        /**/
        this.edicionesList = new ArrayList();
        this.trasladosList = new ArrayList();
        this.evidenciasList = new ArrayList<>();
        this.intercalado = new ArrayList<>();
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

        logger.exiting(this.getClass().getName(), "EditarPeritoETMB");
    }

    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "cargarDatosPerito");
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioS);
        this.trasladosList = formularioEJB.traslados(formulario);
        this.edicionesList = formularioEJB.listaEdiciones(this.nue);
        this.evidenciasList = formularioEJB.findEvidenciaFormularioByFormulario(formulario);
        
        if (formulario.getNumeroParte() == 0) {
            this.isParte = false;
        }
        if (formulario.getRuc() == null || formulario.getRuc().equals("")) {
            this.isRuc = false;
        }
        if (formulario.getRit() == null || formulario.getRit().equals("")) {
            this.isRit = false;
        }
        
        this.evidenciasList = formularioEJB.findEvidenciaFormularioByFormulario(formulario);
        if(!evidenciasList.isEmpty())
            this.evidencia = evidenciasList.get(0).getEvidenciaidEvidencia().getNombreEvidencia();
        System.out.println("EVIDENCIA! "+evidencia);
        
         intercalado(trasladosList);
        
        logger.exiting(this.getClass().getName(), "cargarDatosPerito");
    }

    public String editarFormulario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "editarFormularioPerito");
        boolean datosIncorrectos = false;
        if (this.isParte == false && formulario.getNumeroParte() != 0) {
            parte = formulario.getNumeroParte();
            logger.log(Level.INFO, "MB parte -> {0}", parte);
            String mensaje = validacionVistasMensajesEJB.checkParte(formulario.getNumeroParte());
            if(mensaje.equals("Exito"))
                isParte = true;
            else{                
                FacesContext.getCurrentInstance().addMessage("ruc", new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje," "));
                datosIncorrectos = true;
            }            
        }
        if (this.isRuc == false && formulario.getRuc() != null && !formulario.getRuc().equals("")) {
            ruc = formulario.getRuc();
            logger.log(Level.INFO, "MB ruc -> {0}", ruc);   
            String mensaje = validacionVistasMensajesEJB.checkRucE(formulario.getRuc());
            if(mensaje.equals("Exito"))
                isRuc = true;
            else{                
                FacesContext.getCurrentInstance().addMessage("ruc", new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje," "));
                datosIncorrectos = true;
            }
        }
        if (this.isRit == false && formulario.getRit() != null && !formulario.getRit().equals("")) {
            rit = formulario.getRit();
            logger.log(Level.INFO, "MB rit -> {0}", rit);
            String mensaje = validacionVistasMensajesEJB.checkRitE(formulario.getRit());
            if(mensaje.equals("Exito"))
                isRit = true;
            else{                
                FacesContext.getCurrentInstance().addMessage("ruc", new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje," "));
                datosIncorrectos = true;
            }
        }
        
        if(datosIncorrectos){
            httpServletRequest.getSession().setAttribute("nueF", this.nue);
            httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
            logger.exiting(this.getClass().getName(), "editarFormularioPerito", "");
            return "";
        }        

        String response = formularioEJB.edicionFormulario(formulario, observacionEdicion, usuarioSesion, parte, ruc, rit);
        httpServletRequest.getSession().setAttribute("nueF", this.nue);
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
        if (response.equals("Exito")) {
            logger.exiting(this.getClass().getName(), "editarFormularioPerito", "todoPerito");
            return "todoPerito.xhtml?faces-redirect=true";
        }
        
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un problema al guardar los cambios, por favor intente más tarde.", "error al editar"));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, response, "error al editar"));
        logger.exiting(this.getClass().getName(), "editarFormularioPerito", "");
        return "";
    }

    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirPerito");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirPerito", "/indexListo");
        return "/indexListo.xhtml?faces-redirect=true";
    }
    
    public void validarRuc(){
        System.out.println("entra a validador del ruc en la vista, valida -> "+formulario.getRuc());
        String mensaje = validacionVistasMensajesEJB.checkRuc(formulario.getRuc());
        if(!mensaje.equals("exito")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje," "));
        }
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

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public List<FormularioEvidencia> getEvidenciasList() {
        return evidenciasList;
    }

    public void setEvidenciasList(List<FormularioEvidencia> evidenciasList) {
        this.evidenciasList = evidenciasList;
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

    public List<EdicionFormulario> getEdicionesList() {
        return edicionesList;
    }

    public void setEdicionesList(List<EdicionFormulario> edicionesList) {
        this.edicionesList = edicionesList;
    }

    public List<Traslado> getTrasladosList() {
        return trasladosList;
    }

    public void setTrasladosList(List<Traslado> trasladosList) {
        this.trasladosList = trasladosList;
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
