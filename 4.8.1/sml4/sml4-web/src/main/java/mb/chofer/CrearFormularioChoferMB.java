/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.chofer;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import ejb.ValidacionVistasMensajesEJBLocal;
import entity.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author sebastian
 */
@Named(value = "crearFormularioChoferMB")
@RequestScoped
@ManagedBean
public class CrearFormularioChoferMB {
    @EJB
    private ValidacionVistasMensajesEJBLocal validacionVistasMensajesEJB;
    @EJB
    private UsuarioEJBLocal usuarioEJB;
    @EJB
    private FormularioEJBLocal formularioEJB;

    static final Logger logger = Logger.getLogger(CrearFormularioChoferMB.class.getName());

    //Guardamos el usuario que inicia sesion
    private Usuario usuarioS;

    //Atributos del formulario
    private String ruc;
    private String rit;
    private int nue;
    private String cargo;
    private String delito;
    private String direccionSS;
    private String lugar;
    private String unidadPolicial;
    private String levantadaPor;
    private String rut;
    private Date fecha;
    private String observacion;
    private String descripcion;
    private int parte;

    private String motivo;

    //Guardamos la cuenta del usuario que entrego la vista del login
    private String usuarioSis;

    //Captura al usuario proveniente del inicio de sesión
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;

    //Envio del nue
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    private String evidencias;
    private String codTipoEvidencia;
    private String depa;
    private List<String> listEvidencias = new ArrayList<>();
    private List<String> listEvidencias2 = new ArrayList<>();
    private List<String> listEvidencias3c = new ArrayList<>();
    private List<String> listEvidencias3t = new ArrayList<>();
    private List<String> listEvidencias4 = new ArrayList<>();
    private List<String> listEvidencias5 = new ArrayList<>();
    private List<String> listEvidencias6 = new ArrayList<>();
    private List<String> listEvidenciasx = new ArrayList<>();

    public CrearFormularioChoferMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CrearFormularioChoferMB");
        this.usuarioS = new Usuario();

        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }

        iniciarListas();
        logger.exiting(this.getClass().getName(), "CrearFormularioChoferMB");
    }

    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "cargarDatosChofer");
        this.usuarioS = (Usuario) usuarioEJB.findUsuarioSesionByCuenta(usuarioSis);

        this.cargo = usuarioS.getCargoidCargo().getNombreCargo();
        this.levantadaPor = usuarioS.getNombreUsuario();
        this.rut = usuarioS.getRutUsuario();

        GregorianCalendar c = new GregorianCalendar();
        this.fecha = c.getTime();

        logger.exiting(this.getClass().getName(), "cargarDatosChofer");
    }

    public String iniciarFormulario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "iniciarFormularioChofer");

        boolean datosIncorrectos = false;
        if (parte != 0) {                  
            String mensaje = validacionVistasMensajesEJB.checkParte(parte);
            if(!mensaje.equals("Exito")){                              
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje," "));
                datosIncorrectos = true;
            }            
        }
        if (ruc != null) {            
            String mensaje = validacionVistasMensajesEJB.checkRuc(ruc);
            if(!mensaje.equals("Exito")){                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje," "));
                datosIncorrectos = true;
            }
        }
        if (rit != null) {            
            String mensaje = validacionVistasMensajesEJB.checkRit(rit);
            if(!mensaje.equals("Exito")){                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje," "));
                datosIncorrectos = true;
            }
        }
        if(nue != 0){
            if(nue <0){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar un N.U.E válido"," "));
                datosIncorrectos = true;
            }
        }
       
        if("0".equals(evidencias) || "0".equals(codTipoEvidencia)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar Evidencia y Tipo de Evidencia"," "));
            datosIncorrectos = true;        
        }        
        
        if(datosIncorrectos){
            httpServletRequest.getSession().setAttribute("nueF", this.nue);
            httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
            logger.exiting(this.getClass().getName(), "editarFormularioPerito", "");
            return "";
        }    
        
        
        String resultado = formularioEJB.crearFormulario(codTipoEvidencia, evidencias, motivo, ruc, rit, nue, parte, cargo, delito, direccionSS, lugar, unidadPolicial, levantadaPor, rut, fecha, observacion, descripcion, usuarioS);

        if (resultado.equals("Exito")) {
            //Enviando nue
            httpServletRequest.getSession().setAttribute("nueF", this.nue);
            //Enviando usuario
            httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, resultado, "Datos exitosos"));
            logger.exiting(this.getClass().getName(), "iniciarFormularioChofer", "formularioCreadoChofer");
            return "formularioCreadoChofer.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado, "Datos no válidos"));
        logger.exiting(this.getClass().getName(), "iniciarFormularioChofer", "");
        return "";
    }

    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirChofer");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioS.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirChofer", "/indexListo");
        return "/indexListo?faces-redirect=true";
    }

    //redirecciona a la pagina para realizar una busqueda
    public String buscar() {
        logger.entering(this.getClass().getName(), "buscar");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
        logger.exiting(this.getClass().getName(), "buscar", "buscadorChofer");
        return "buscadorChofer?faces-redirect=true";
    }

    //redirecciona a la pagina para iniciar cadena de custodia
    public String iniciarCadena() {
        logger.entering(this.getClass().getName(), "iniciarCadena");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
        logger.exiting(this.getClass().getName(), "iniciarCadena", "choferFormulario");
        return "choferFormulario?faces-redirect=true";
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Usuario getUsuarioS() {
        return usuarioS;
    }

    public void setUsuarioS(Usuario usuarioS) {
        this.usuarioS = usuarioS;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRit() {
        return rit;
    }

    public void setRit(String rit) {
        this.rit = rit;
    }

    public int getNue() {
        return nue;
    }

    public void setNue(int nue) {
        this.nue = nue;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDelito() {
        return delito;
    }

    public void setDelito(String delito) {
        this.delito = delito;
    }

    public String getDireccionSS() {
        return direccionSS;
    }

    public void setDireccionSS(String direccionSS) {
        this.direccionSS = direccionSS;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getUnidadPolicial() {
        return unidadPolicial;
    }

    public void setUnidadPolicial(String unidadPolicial) {
        this.unidadPolicial = unidadPolicial;
    }

    public String getLevantadaPor() {
        return levantadaPor;
    }

    public void setLevantadaPor(String levantadaPor) {
        this.levantadaPor = levantadaPor;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuarioSis() {
        return usuarioSis;
    }

    public void setUsuarioSis(String usuarioSis) {
        this.usuarioSis = usuarioSis;
    }

    public int getParte() {
        return parte;
    }

    public void setParte(int parte) {
        this.parte = parte;
    }

    public String getEvidencias() {
        return evidencias;
    }

    public void setEvidencias(String evidencias) {
        this.evidencias = evidencias;
    }

    public String getCodTipoEvidencia() {
        return codTipoEvidencia;
    }

    public void setCodTipoEvidencia(String codTipoEvidencia) {
        this.codTipoEvidencia = codTipoEvidencia;
    }

    public String getDepa() {
        return depa;
    }

    public void setDepa(String depa) {
        this.depa = depa;
    }

    public List<String> getListEvidenciasx() {
        return listEvidenciasx;
    }

    public void setListEvidenciasx(List<String> listEvidenciasx) {
        this.listEvidenciasx = listEvidenciasx;
    }

    public void iniciarListas() {
        //biologica tanato ok
        listEvidencias.add("Contenido bucal");
        listEvidencias.add("Contenido vaginal");
        listEvidencias.add("Contenido rectal");
        listEvidencias.add("Lecho ungeal");
        listEvidencias.add("Secreciones");
        listEvidencias.add("Sangre");
        listEvidencias.add("Orina");
        listEvidencias.add("Tejido cerebro");
        listEvidencias.add("Tejido corazón");
        listEvidencias.add("Tejido pulmón");
        listEvidencias.add("Tejido hígado");
        listEvidencias.add("Tejido baso");
        listEvidencias.add("Tejido diafragma");
        listEvidencias.add("Tejido intestino");
        listEvidencias.add("Tejido piel");
        listEvidencias.add("Tejido otros");
        listEvidencias.add("Otros");

        //biologica clinica ok
        listEvidencias6.add("Contenido bucal");
        listEvidencias6.add("Contenido vaginal");
        listEvidencias6.add("Contenido rectal");
        listEvidencias6.add("Lecho ungeal");
        listEvidencias6.add("Secreciones");
        listEvidencias6.add("Sangre");
        listEvidencias6.add("Orina");
        listEvidencias6.add("Otros");

        //vestuario clinica y tanato ok
        listEvidencias2.add("Vestido");
        listEvidencias2.add("Blusa");
        listEvidencias2.add("Camisa");
        listEvidencias2.add("Pantalón");
        listEvidencias2.add("Polera");
        listEvidencias2.add("Chaqueta");
        listEvidencias2.add("Chaleco");
        listEvidencias2.add("Calzado");
        listEvidencias2.add("Otros");

        //artefactos clinica
        listEvidencias3c.add("Protector");
        listEvidencias3c.add("Toalla higiénica");
        listEvidencias3c.add("Otros");

        //artefactos tanato
        listEvidencias3t.add("Protector");
        listEvidencias3t.add("Toalla higiénica");
        listEvidencias3t.add("Arma blanca");
        listEvidencias3t.add("Cuchillo");
        listEvidencias3t.add("Sable");
        listEvidencias3t.add("Otros");

        // balistica tanato
        listEvidencias4.add("Bala");
        listEvidencias4.add("Otros");

        listEvidencias5.add("Otros");

        listEvidenciasx.add("Seleccione");
        listEvidenciasx.add("Contenido bucal");
        listEvidenciasx.add("Contenido vaginal");
        listEvidenciasx.add("Contenido rectal");
        listEvidenciasx.add("Lecho ungeal");
        listEvidenciasx.add("Secreciones");
        listEvidenciasx.add("Sangre");
        listEvidenciasx.add("Orina");
        listEvidenciasx.add("Tejido cerebro");
        listEvidenciasx.add("Tejido corazón");
        listEvidenciasx.add("Tejido pulmón");
        listEvidenciasx.add("Tejido hígado");
        listEvidenciasx.add("Tejido baso");
        listEvidenciasx.add("Tejido diafragma");
        listEvidenciasx.add("Tejido intestino");
        listEvidenciasx.add("Tejido piel");
        listEvidenciasx.add("Tejido otros");
        listEvidenciasx.add("Vestido");
        listEvidenciasx.add("Blusa");
        listEvidenciasx.add("Camisa");
        listEvidenciasx.add("Pantalón");
        listEvidenciasx.add("Polera");
        listEvidenciasx.add("Chaqueta");
        listEvidenciasx.add("Chaleco");
        listEvidenciasx.add("Calzado");
        listEvidenciasx.add("Protector");
        listEvidenciasx.add("Toalla higiénica");
        listEvidenciasx.add("Arma blanca");
        listEvidenciasx.add("Cuchillo");
        listEvidenciasx.add("Sable");
        listEvidenciasx.add("Bala");
        listEvidenciasx.add("Otros");

    }

    public List<String> cargarEvidencias(final AjaxBehaviorEvent event) {
        logger.info("selecciono: " + codTipoEvidencia);
        switch (codTipoEvidencia) {
            case "1":
                //biologica clinica
                listEvidenciasx = listEvidencias6;
                return listEvidenciasx;
            case "6":
                //biologica tanatologia
                listEvidenciasx = listEvidencias;
                return listEvidenciasx;
            case "2":
                // vestuario clinica
                listEvidenciasx = listEvidencias2;
                return listEvidenciasx;
            case "7":
                // vestuario tanatologia
                listEvidenciasx = listEvidencias2;
                return listEvidenciasx;
            case "3":
                //artefactos clinica
                listEvidenciasx = listEvidencias3c;
                return listEvidenciasx;
            case "8":
                //artefactos tanatologia
                listEvidenciasx = listEvidencias3t;
                return listEvidenciasx;
            case "5":
                // balistica tanatologia
                listEvidenciasx = listEvidencias4;
                return listEvidenciasx;
            case "4":
                //otros clinica
                listEvidenciasx = listEvidencias5;
                return listEvidenciasx;
            case "9":
                //otros tanatologia
                listEvidenciasx = listEvidencias5;
                return listEvidenciasx;
        }
        return listEvidenciasx;
    }

}
