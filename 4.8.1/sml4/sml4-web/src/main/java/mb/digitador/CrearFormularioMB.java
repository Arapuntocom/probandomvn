/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.digitador;

import ejb.FormularioDigitadorLocal;
import ejb.UsuarioEJBLocal;
import ejb.ValidacionVistasMensajesEJBLocal;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aracelly
 */
@Named(value = "crearFormularioMB")
@RequestScoped
@ManagedBean
public class CrearFormularioMB {
    @EJB
    private ValidacionVistasMensajesEJBLocal validacionVistasMensajesEJB;
    @EJB
    private FormularioDigitadorLocal formularioDigitador;

    @EJB
    private UsuarioEJBLocal usuarioEJB;

    static final Logger logger = Logger.getLogger(CrearFormularioMB.class.getName());

    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;

    private HttpServletRequest httpServletRequest2;
    private FacesContext facesContext2;

    private String usuarioSis;
    //Guardamos el usuario que inicia sesion
    private Usuario usuarioSesion;

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

    //Usuario levantador
    private Usuario iniciaCadena;

    public CrearFormularioMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CrearFormularioMB");

        this.usuarioSesion = new Usuario();
        this.iniciaCadena = new Usuario();

        this.facesContext2 = FacesContext.getCurrentInstance();
        this.httpServletRequest2 = (HttpServletRequest) facesContext2.getExternalContext().getRequest();

        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }
        
        logger.exiting(this.getClass().getName(), "CrearFormularioMB");
    }

    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadUsuario");
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioSis);
        iniciarListas();
        logger.exiting(this.getClass().getName(), "loadUsuario");
    }

    public String iniciarFormulario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "iniciarFormulario");
        
        boolean datosIncorrectos = false;
        if(rut == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar un R.U.T. válido"," "));
            datosIncorrectos = true;        
        }else{
            String mensaje = validacionVistasMensajesEJB.checkRut(rut);
            if(!mensaje.equals("Exito")){                              
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje," "));
                datosIncorrectos = true;
            }
        }  
          
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

        String resultado = formularioDigitador.crearFormulario(codTipoEvidencia, evidencias, ruc, rit, nue, parte, cargo, delito, direccionSS, lugar, unidadPolicial, levantadaPor, rut, fecha, observacion, descripcion, usuarioSesion);

        if (resultado.equals("Exito")) {
            httpServletRequest.getSession().setAttribute("nueF", this.nue);
            httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
            httpServletRequest1.getSession().setAttribute("rutInicia", this.rut);

            logger.exiting(this.getClass().getName(), "iniciarFormulario", "forAddTHU11");
            return "forAddTHU11?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado, "Datos no válidos"));
        logger.exiting(this.getClass().getName(), "iniciarFormulario", "");
        return "";
    }

    //redirecciona a la pagina para iniciar cadena de custodia
    public String iniciarCadena() {
        logger.entering(this.getClass().getName(), "iniciarCadena");
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
        logger.exiting(this.getClass().getName(), "iniciarCadena", "digitadorFormulario");
        return "digitadorFormularioHU11?faces-redirect=true";
    }

    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirDigitador");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirDigitador", "/indexListo");
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

    public List<String> getListEvidencias() {
        return listEvidencias;
    }

    public void setListEvidencias(List<String> listEvidencias) {
        this.listEvidencias = listEvidencias;
    }

    public List<String> getListEvidencias2() {
        return listEvidencias2;
    }

    public void setListEvidencias2(List<String> listEvidencias2) {
        this.listEvidencias2 = listEvidencias2;
    }

    public List<String> getListEvidencias3c() {
        return listEvidencias3c;
    }

    public void setListEvidencias3c(List<String> listEvidencias3c) {
        this.listEvidencias3c = listEvidencias3c;
    }

    public List<String> getListEvidencias3t() {
        return listEvidencias3t;
    }

    public void setListEvidencias3t(List<String> listEvidencias3t) {
        this.listEvidencias3t = listEvidencias3t;
    }

    public List<String> getListEvidencias4() {
        return listEvidencias4;
    }

    public void setListEvidencias4(List<String> listEvidencias4) {
        this.listEvidencias4 = listEvidencias4;
    }

    public List<String> getListEvidencias5() {
        return listEvidencias5;
    }

    public void setListEvidencias5(List<String> listEvidencias5) {
        this.listEvidencias5 = listEvidencias5;
    }

    public List<String> getListEvidencias6() {
        return listEvidencias6;
    }

    public void setListEvidencias6(List<String> listEvidencias6) {
        this.listEvidencias6 = listEvidencias6;
    }

    public List<String> getListEvidenciasx() {
        return listEvidenciasx;
    }

    public void setListEvidenciasx(List<String> listEvidenciasx) {
        this.listEvidenciasx = listEvidenciasx;
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

    public int getParte() {
        return parte;
    }

    public void setParte(int parte) {
        this.parte = parte;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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