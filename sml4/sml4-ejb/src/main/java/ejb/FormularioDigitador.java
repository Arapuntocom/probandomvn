/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import static ejb.FormularioEJB.logger;
import entity.Area;
import entity.Cargo;
import entity.Formulario;
import entity.TipoMotivo;
import entity.TipoUsuario;
import entity.Traslado;
import entity.Usuario;
import facade.AreaFacadeLocal;
import facade.CargoFacadeLocal;
import facade.FormularioFacadeLocal;
import facade.TipoMotivoFacadeLocal;
import facade.TipoUsuarioFacadeLocal;
import facade.TrasladoFacadeLocal;
import facade.UsuarioFacadeLocal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author sebastian
 */
@Stateless
public class FormularioDigitador implements FormularioDigitadorLocal {

    @EJB
    private FormularioFacadeLocal formularioFacade;
    @EJB
    private ValidacionEJBLocal validacionEJB;
    @EJB
    private UsuarioFacadeLocal usuarioFAcade;
    @EJB
    private TipoUsuarioFacadeLocal tipoUsuarioFacade;
    @EJB
    private AreaFacadeLocal areaFacade;
    @EJB
    private CargoFacadeLocal cargoFacade;
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    @EJB
    private TrasladoFacadeLocal trasladoFacade;
    @EJB
    private TipoMotivoFacadeLocal tipoMotivoFacade;

    //ZACK
    //Función que crea el formulario
    // el String de retorno se muentra como mensaje en la vista.
    @Override
    public String crearFormulario(String ruc, String rit, int nue, int nParte, String cargo, String delito, String direccionSS, String lugar, String unidadPolicial, String levantadoPor, String rut, Date fecha, String observacion, String descripcion, Usuario digitador) {

        //Verificando nue
        if (nue > 0) {
            //Verificando si existe un formulario con ese nue
            Formulario verificar = formularioFacade.findByNue(nue);
            if (verificar != null) {
                return "Formulario existente";
            }
        } else {
            return "Nue inválido";
        }

        //Verificando rit y ruc
        //checkeo ruc y rit
        if (!validacionEJB.checkRucOrRit(ruc) || !validacionEJB.checkRucOrRit(rit)) {
            logger.exiting(this.getClass().getName(), "crearFormulario", "Error con RUC o RIT");
            return "Error con RUC o RIT.";
        }

        //Verificando que los campos sean string
        if (!validacionEJB.soloCaracteres(delito) || !validacionEJB.soloCaracteres(levantadoPor) || !validacionEJB.val(rut)) {

            return "Campos erróneos.";
        }

        //ruc - rit- nparte - obs y descripcion no son obligatorios
        Usuario usuarioIngresar = new Usuario();
        //Verificando en la base de datos si existe el usuario con ese rut
        usuarioIngresar = usuarioFacade.findByRUN(rut);

        if (usuarioIngresar == null) {
            //quiero decir que no existe

            //validando el nombre ingresado, no valido el cargo porque lo estoy entregando desde la base de datos
            if (!validacionEJB.soloCaracteres(levantadoPor)) {
                return "Nombre ingresado inválido, sólo caracteres";
            }
            usuarioIngresar = crearExterno1(cargo, levantadoPor, rut);

            if (usuarioIngresar == null) {
                return "No se pudo crear el nuevo usuario";
            }
        } else //Existe, y hay que verificar que los datos ingresador concuerdan con los que hay en la base de datos
        {

            if (!usuarioIngresar.getCargoidCargo().getNombreCargo().equals(cargo) || !usuarioIngresar.getNombreUsuario().equals(levantadoPor)) {
             return "Datos nos coinciden con el rut";
             }
             
        }
        Formulario nuevoFormulario = new Formulario();

        nuevoFormulario.setDireccionFotografia("C:");
        nuevoFormulario.setFechaIngreso(new Date(System.currentTimeMillis()));
        nuevoFormulario.setFechaOcurrido(fecha);
        nuevoFormulario.setUltimaEdicion(nuevoFormulario.getFechaIngreso());
        nuevoFormulario.setUsuarioidUsuario(digitador); // Usuario digitador
        nuevoFormulario.setUsuarioidUsuarioInicia(usuarioIngresar); //Usuario inicia
        nuevoFormulario.setDescripcionEspecieFormulario(descripcion);
        nuevoFormulario.setObservaciones(observacion);
        nuevoFormulario.setDelitoRef(delito);
        nuevoFormulario.setNue(nue);
        nuevoFormulario.setNumeroParte(nParte);
        nuevoFormulario.setDelitoRef(delito);
        nuevoFormulario.setRuc(ruc);
        nuevoFormulario.setRit(rit);
        nuevoFormulario.setUnidadPolicial(unidadPolicial);
        nuevoFormulario.setLugarLevantamiento(lugar);
        nuevoFormulario.setDireccionSS(direccionSS);
        nuevoFormulario.setBloqueado(false);

        logger.finest("se inicia la persistencia del nuevo formulario");
        formularioFacade.create(nuevoFormulario);
        logger.finest("se finaliza la persistencia del nuevo formulario");
        logger.exiting(this.getClass().getName(), "crearFormulario", true);
        return "Exito";

    }

    //ZACK
    private Usuario crearExterno1(String cargo, String nombre, String rut) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearExterno");

        Usuario nuevoExterno = new Usuario();
        // unidad = area se esta entregando otro
        Area areaExterno = areaFacade.findByArea("Otro");
        TipoUsuario tue = tipoUsuarioFacade.findByTipo("Externo");
        //buscando cargo, en el caso que no exista se crea
        Cargo cargoExterno = cargoFacade.findByCargo(cargo);

        if (cargoExterno == null) {
            return null;
        }
        //no valido que exista ya que lo estoy sacando desde sistema, y en el caso que no existe debe ser otro

        //APELLIDO ? -> se lo comio el perro        
        nuevoExterno.setNombreUsuario(nombre);
        nuevoExterno.setRutUsuario(rut);
        nuevoExterno.setAreaidArea(areaExterno);
        nuevoExterno.setCargoidCargo(cargoExterno);
        nuevoExterno.setTipoUsuarioidTipoUsuario(tue);
        nuevoExterno.setEstadoUsuario(Boolean.TRUE);
        nuevoExterno.setMailUsuario("na");
        nuevoExterno.setPassUsuario("na");
        logger.finest("se inicia la persistencia del nuevo usuario externo");
        usuarioFacade.create(nuevoExterno);
        logger.finest("se finaliza la persistencia del nuevo usuario externo");

        nuevoExterno = usuarioFacade.findByRUN(rut);
        if (nuevoExterno != null) {
            logger.exiting(this.getClass().getName(), "crearExterno", nuevoExterno.toString());
            return nuevoExterno;
        }
        logger.exiting(this.getClass().getName(), "crearExterno", null);
        return null;
    }

    @Override
    public String crearTraslado(Formulario formulario, String usuarioEntrega, String usuarioEntregaCargo, String usuarioEntregaRut, String usuarioRecibe, String usuarioRecibeCargo, String usuarioRecibeRut, Date fechaT, String observaciones, String motivo, Usuario uSesion) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearTraslado");

        if (formulario == null) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Formulario nulo");
            return "Imposible agregar traslado, ocurrió un problema al cargar el formulario, por favor intente más tarde.";
        }

        //verificamos que el formulario no se encuentre bloqueado.
        if (formulario.getBloqueado()) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Formulario bloqueado");
            return "Imposible agregar traslado, esta cadena de custodia se encuentra cerrada.";
        }

        if (usuarioEntrega == null || usuarioEntregaCargo == null || usuarioEntregaRut == null || usuarioRecibe == null || usuarioRecibeCargo == null || usuarioRecibeRut == null || motivo == null) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Campos null");
            return "Faltan campos";
        }

        //Validando usuario que entrega
        if (!validacionEJB.soloCaracteres(usuarioEntregaRut) || !validacionEJB.soloCaracteres(usuarioEntrega) || !validacionEJB.soloCaracteres(usuarioEntregaCargo)) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Error verificacion datos usuario entrega");
            return "Error datos usuario entrega";
        }

        //Validando usuario que recibe
        if (!validacionEJB.val(usuarioRecibeRut) || !validacionEJB.val(usuarioRecibe) || !validacionEJB.val(usuarioRecibeCargo)) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Error verificacion datos usuario recibe");
            return "Error datos usuario recibe";
        }

        //Busco todos los traslados del formulario
        List<Traslado> trasladoList = traslados(formulario);

        //Comparando fechas entre traslados
        if (!trasladoList.isEmpty() && !validacionEJB.compareFechas(fechaT, trasladoList.get(trasladoList.size() - 1).getFechaEntrega())) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Fecha");
            return "Error, la fecha del nuevo traslado debe ser igual o posterior a la ultima fecha de traslado.";
        }

        //Comparando fecha entre traslado y formulario
        if (!validacionEJB.compareFechas(fechaT, formulario.getFechaOcurrido())) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Fecha");
            return "Error, la fecha de traslado debe ser igual o posterior a la fecha del formulario.";
        }

        //traer usuarios, motivo
        TipoMotivo motivoP = tipoMotivoFacade.findByTipoMotivo(motivo);
        if (motivoP == null) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Motivo de Traslado");
            return "Error, se requiere especificar Motivo del traslado.";
        }

        Usuario usuarioEntregaP = null;
        Usuario usuarioRecibeP = null;

        //Verificando usuario Entrega
        usuarioEntregaP = usuarioFacade.findByRUN(usuarioEntregaRut);

        if (usuarioEntregaP == null) {
            usuarioEntregaP = crearExterno1(usuarioEntregaCargo, usuarioEntrega, usuarioEntregaRut);
            if (usuarioEntregaP == null) {
                logger.exiting(this.getClass().getName(), "crearTraslado", "Error con creacion Usuario Entrega");
                return "Error con datos de la persona que entrega.";
            }
        } else if (!usuarioEntregaP.getNombreUsuario().equals(usuarioEntrega) || !usuarioEntregaP.getCargoidCargo().getNombreCargo().equals(usuarioEntregaCargo)) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Error con verificacion Usuario Entrega");
            return "Datos no coinciden con el rut ingresado";
        }
        //Verificando usuario Recibe
        usuarioRecibeP = usuarioFacade.findByRUN(usuarioRecibeRut);
        if (usuarioRecibeP == null) {
            usuarioRecibeP = crearExterno1(usuarioRecibeCargo, usuarioRecibe, usuarioRecibeRut);
            if (usuarioRecibeP == null) {
                logger.exiting(this.getClass().getName(), "crearTraslado", "Error con creacion usuario Recibe");
                return "Error con datos de la persona que recibe.";
            }
        } else if (!usuarioRecibeP.getNombreUsuario().equals(usuarioRecibe) || !usuarioRecibeP.getCargoidCargo().getNombreCargo().equals(usuarioRecibeCargo)) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Error con verificacion usuario Recibe");
            return "Datos no corresponden al rut";
        }

        //verificando que usuario recibe sea distinto del usuario que entrega
        if (usuarioEntregaP.equals(usuarioRecibeP)) { //si se trata del mismo usuario
            logger.exiting(this.getClass().getName(), "crearTraslado", "Usuario Entrega y Recibe son el mismo");
            return "El usuario que recibe la cadena de custodia debe ser distinto al usuario que la entrega.";
        }

        //Creando traslado
        Traslado nuevoTraslado = new Traslado();
        nuevoTraslado.setFechaEntrega(fechaT);
        nuevoTraslado.setFormularioNUE(formulario);
        nuevoTraslado.setObservaciones(observaciones);
        nuevoTraslado.setTipoMotivoidMotivo(motivoP);
        nuevoTraslado.setUsuarioidUsuarioRecibe(usuarioRecibeP);
        nuevoTraslado.setUsuarioidUsuarioEntrega(usuarioEntregaP);

        logger.info("se inicia insercion del nuevo traslado");
        trasladoFacade.create(nuevoTraslado);
        logger.info("se finaliza insercion del nuevo traslado");

        //verificamos si se se trata de un peritaje, lo cual finaliza la cc.
        if (nuevoTraslado.getTipoMotivoidMotivo().getTipoMotivo().equals("Peritaje")) {
            if (uSesion.getCargoidCargo().getNombreCargo().equals("Tecnico") || uSesion.getCargoidCargo().getNombreCargo().equals("Perito")) {
                logger.info("se realiza peritaje, por tanto se finaliza la cc.");

                formulario.setBloqueado(true);
                logger.info("se inicia la edición del formulario para bloquearlo");
                formularioFacade.edit(formulario);
                logger.info("se finaliza la edición del formulario para bloquearlo");

            }
        }

        logger.exiting(this.getClass().getName(), "crearTraslado", "Exito");
        return "Exito";

    }

    //** modificada para retornar una lista vacía si no encuentra resultados.
    private List<Traslado> traslados(Formulario formulario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "traslados", formulario.toString());
        List<Traslado> retorno = trasladoFacade.findByNue(formulario);
        if (retorno == null) {
            retorno = new ArrayList<>();
            logger.exiting(this.getClass().getName(), "traslados", retorno.size());
            return retorno;
        } else {
            logger.exiting(this.getClass().getName(), "traslados", retorno.size());
            return retorno;
        }
    }

}
