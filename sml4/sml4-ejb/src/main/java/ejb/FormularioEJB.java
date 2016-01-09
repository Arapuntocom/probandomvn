package ejb;

import entity.Area;
import entity.Cargo;
import entity.EdicionFormulario;
import entity.Formulario;
import entity.Semaforo;
import entity.TipoMotivo;
import entity.TipoUsuario;
import entity.Traslado;
import entity.Usuario;
import facade.AreaFacadeLocal;
import facade.CargoFacadeLocal;
import facade.EdicionFormularioFacadeLocal;
import facade.FormularioFacadeLocal;
import facade.SemaforoFacadeLocal;
import facade.TipoMotivoFacadeLocal;
import facade.TipoUsuarioFacadeLocal;
import facade.TrasladoFacadeLocal;
import facade.UsuarioFacadeLocal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Aracelly
 */
@Stateless
public class FormularioEJB implements FormularioEJBLocal {

    @EJB
    private SemaforoFacadeLocal semaforoFacade;
    @EJB
    private CargoFacadeLocal cargoFacade;
    @EJB
    private TipoUsuarioFacadeLocal tipoUsuarioFacade;
    @EJB
    private AreaFacadeLocal areaFacade;
    @EJB
    private TrasladoFacadeLocal trasladoFacade;
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    @EJB
    private TipoMotivoFacadeLocal tipoMotivoFacade;
    @EJB
    private FormularioFacadeLocal formularioFacade;
    @EJB
    private EdicionFormularioFacadeLocal edicionFormularioFacade;
    @EJB
    private ValidacionEJBLocal validacionEJB;
    

    static final Logger logger = Logger.getLogger(FormularioEJB.class.getName());

    @Override
    public Usuario obtenerPoseedorFormulario(Formulario formulario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "obtenerPoseedorFormulario", formulario.getNue());
        //Busco todo slos traslados del formulario
        List<Traslado> trasladoList = traslados(formulario);
        Usuario usuarioPoseedor = formulario.getUsuarioidUsuarioInicia(); //usuario que inicia el formulario
        //Comparando fechas entre traslados
        if (!trasladoList.isEmpty()) {
            usuarioPoseedor = trasladoList.get(trasladoList.size() - 1).getUsuarioidUsuarioRecibe();  //último usuario que recibió            
        }
        logger.exiting(this.getClass().getName(), "obtenerPoseedorFormulario", usuarioPoseedor.toString());
        return usuarioPoseedor;
    }

    //** trabajar en la consulta sql
    // por qué es necesario tener las ediciones de un solo usuario ?
    //@Override
    public List<EdicionFormulario> listaEdiciones(int nue, int idUser) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "listaEdiciones", nue + " " + idUser);
        List<EdicionFormulario> lista = new ArrayList();
        List<EdicionFormulario> response = new ArrayList();
        lista = edicionFormularioFacade.findAll();

        for (int i = 0; i < lista.size(); i++) {

            if (lista.get(i).getUsuarioidUsuario().getIdUsuario() == idUser && lista.get(i).getFormularioNUE().getNue() == nue) {
                response.add(lista.get(i));
            }
        }

        if (response.isEmpty()) {
            //response = null;
            logger.exiting(this.getClass().getName(), "listaEdiciones", "sin elementos");
            return response;
        }
        logger.exiting(this.getClass().getName(), "listaEdiciones", response.size());
        return response;
    }

    //observacion: se pueden reducir la cant de consultas usando como parametro de entrada un Formulario.
    @Override
    public List<EdicionFormulario> listaEdiciones(int nue) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "listaEdiciones");
        List<EdicionFormulario> retorno = new ArrayList<>();
        Formulario f = formularioFacade.findByNue(nue);
        if (f != null) {
            retorno = edicionFormularioFacade.listaEdiciones(f);
            if (retorno == null) {
                logger.severe("lista de ediciones es null");
                retorno = new ArrayList<>();
            }
        } else {
            logger.severe("formulario no encontrado");
        }
        logger.exiting(this.getClass().getName(), "listaEdiciones", retorno.size());
        return retorno;
    }

    @Override
    public Formulario findFormularioByNue(int nueAbuscar) {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findFormularioByNue", nueAbuscar);

        Formulario formulario = formularioFacade.findByNue(nueAbuscar);
        if (formulario != null) {
            logger.exiting(this.getClass().getName(), "findFormularioByNue", formulario.toString());
            return formulario;
        }
        logger.exiting(this.getClass().getName(), "findFormularioByNue", "Error con formulario");
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

        //Actualizando traslado
        
        Traslado ultimoTraslado = ultimoTraslado(formulario.getNue());
        ultimoTraslado.setFechaEntrega(fechaT);
        ultimoTraslado.setObservaciones(observaciones);
        ultimoTraslado.setUsuarioidUsuarioRecibe(usuarioRecibeP);

        logger.info("se inicia actualizacion del nuevo traslado");
        trasladoFacade.edit(ultimoTraslado);
        logger.info("se finaliza actualizacion del nuevo traslado");

        //verificamos si se se trata de un peritaje, lo cual finaliza la cc.
        if (ultimoTraslado.getTipoMotivoidMotivo().getTipoMotivo().equals("Peritaje")) {
            if (uSesion.getCargoidCargo().getNombreCargo().equals("Técnico") || uSesion.getCargoidCargo().getNombreCargo().equals("Perito")) {
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

    //Esta unidad hace referencia al area del SML 
    private Usuario crearExterno(Usuario usuario, String cargo) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearExterno");

        if (usuario != null) {
            Usuario nuevoExterno = usuario;
            Area areaExterno = areaFacade.findByArea("Otro");
            TipoUsuario tue = tipoUsuarioFacade.findByTipo("Externo");
            Cargo cargoExterno = cargoFacade.findByCargo(cargo);
            if (cargoExterno == null) {
                Cargo nuevo = new Cargo();
                nuevo.setNombreCargo(cargo);
                cargoFacade.create(nuevo);
                cargoExterno = cargoFacade.findByCargo(cargo);
            }

            nuevoExterno.setAreaidArea(areaExterno);
            nuevoExterno.setCargoidCargo(cargoExterno);
            nuevoExterno.setTipoUsuarioidTipoUsuario(tue);
            nuevoExterno.setEstadoUsuario(Boolean.TRUE);
            nuevoExterno.setMailUsuario("na");
            nuevoExterno.setPassUsuario("na");
            logger.finest("se inicia la persistencia del nuevo usuario externo");
            usuarioFacade.create(nuevoExterno);
            logger.finest("se finaliza la persistencia del nuevo usuario externo");

            nuevoExterno = usuarioFacade.findByRUN(usuario.getRutUsuario());
            if (nuevoExterno != null) {
                logger.exiting(this.getClass().getName(), "crearExterno", nuevoExterno.toString());
                return nuevoExterno;
            }
        }
        logger.exiting(this.getClass().getName(), "crearExterno", null);
        return null;
    }

    //ZACK
    private Usuario crearExterno1(String cargo, String nombre, String rut) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearExterno");

        Usuario nuevoExterno = new Usuario();
        Area areaExterno = areaFacade.findByArea("Otro");
        TipoUsuario tue = tipoUsuarioFacade.findByTipo("Externo");
        //buscando cargo, en el caso que no exista se crea
        Cargo cargoExterno = cargoFacade.findByCargo(cargo);
        if (cargoExterno == null) {
            Cargo nuevo = new Cargo();
            nuevo.setNombreCargo(cargo);
            cargoFacade.create(nuevo);
            cargoExterno = cargoFacade.findByCargo(cargo);
        }

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

    //** modificada para retornar una lista vacía si no encuentra resultados.
    @Override
    public List<Traslado> traslados(Formulario formulario) {
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

    //ZACK
    //Función que crea el formulario
    // el String de retorno se muentra como mensaje en la vista.
    @Override
    public String crearFormulario(String motivo, String ruc, String rit, int nue, int nParte, String cargo, String delito, String direccionSS, String lugar, String unidad, String levantadoPor, String rut, Date fecha, String observacion, String descripcion, Usuario digitador) {

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
        if (!validacionEJB.soloCaracteres(cargo) || !validacionEJB.soloCaracteres(delito) || !validacionEJB.soloCaracteres(levantadoPor) || !validacionEJB.val(rut)) {

            return "Campos erróneos.";
        }
        
        //match con motivo
        TipoMotivo motivoP = tipoMotivoFacade.findByTipoMotivo(motivo);
        if(motivoP == null){
            logger.exiting(this.getClass().getName(), "crearFormulario", "Error con motivo de entrega.");
            return "Error con motivo de entrega.";
        }

        //ruc - rit- nparte - obs y descripcion no son obligatorios
        Usuario usuarioIngresar = new Usuario();
        //Verificando en la base de datos si existe el usuario con ese rut
        usuarioIngresar = usuarioFacade.findByRUN(rut);

        if (usuarioIngresar == null) {
            //quiero decir que no existe
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
        Semaforo estadoInicial;
        switch(motivo){
            case "Peritaje":
                estadoInicial = semaforoFacade.findByColor("Amarillo");
                break;
            default:
                estadoInicial = semaforoFacade.findByColor("Rojo");
        }        

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
        nuevoFormulario.setUnidadPolicial(unidad);
        nuevoFormulario.setLugarLevantamiento(lugar);
        nuevoFormulario.setDireccionSS(direccionSS);
        nuevoFormulario.setBloqueado(false);
        nuevoFormulario.setSemaforoidSemaforo(estadoInicial);

        logger.finest("se inicia la persistencia del nuevo formulario");
        formularioFacade.create(nuevoFormulario);
        logger.finest("se finaliza la persistencia del nuevo formulario");
        
        
        //creando primer traslado
        Traslado primerTraslado = new Traslado();
        primerTraslado.setFormularioNUE(formularioFacade.findByNue(nue));
        primerTraslado.setTipoMotivoidMotivo(motivoP);
        primerTraslado.setUsuarioidUsuarioEntrega(usuarioIngresar);
        
        logger.finest("se inicia la persistencia del primer traslado");
        trasladoFacade.create(primerTraslado);
        logger.finest("se finaliza la persistencia del primer traslado");
        
        logger.exiting(this.getClass().getName(), "crearFormulario", true);
        return "Exito";

    }

    //se crea una nueva edicion para el formulario indicado.
    @Override
    public String edicionFormulario(Formulario formulario, String obsEdicion, Usuario usuarioSesion) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "edicionFormulario");
        if (obsEdicion == null) {
            logger.exiting(this.getClass().getName(), "edicionFormulario", "falta observación.");
            return "Se requiere la observación.";
        }

        //verificando que el usuario que edita si haya participado en la cc.
        if (!esParticipanteCC(formulario, usuarioSesion)) {
            logger.exiting(this.getClass().getName(), "edicionFormulario", "usuario no ha participado en cc");
            return "Ud no ha participado en esta cadena de custodia.";
        }

        //Creando el objeto edicion
        EdicionFormulario edF = new EdicionFormulario();

        edF.setFormularioNUE(formulario);
        edF.setUsuarioidUsuario(usuarioSesion);
        edF.setObservaciones(obsEdicion);
        edF.setFechaEdicion(new Date(System.currentTimeMillis()));

        //Actualizando ultima edicion formulario
        formulario.setUltimaEdicion(edF.getFechaEdicion());

        edicionFormularioFacade.edit(edF);
        formularioFacade.edit(formulario);

        logger.exiting(this.getClass().getName(), "edicionFormulario", "Exito");
        return "Exito";
    }

    //se crea una nueva edicion para el formulario indicado.
    //modificado Ara
    @Override
    public String edicionFormulario(Formulario formulario, String obsEdicion, Usuario usuarioSesion, int parte, String ruc, String rit) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "edicionFormulario");
        System.out.println("EJB ruc " + ruc);
        System.out.println("EJB rit " + rit);
        System.out.println("EJB parte " + parte);
        System.out.println("EJB obs " + obsEdicion);
        if (parte > 0 || rit != null || rit != null || obsEdicion != null) {

            //verificando que el usuario que edita si haya participado en la cc.
            if (!esParticipanteCC(formulario, usuarioSesion)) {
                logger.exiting(this.getClass().getName(), "edicionFormulario", "usuario no ha participado en cc");
                return "Ud no ha participado en esta cadena de custodia.";
            }

            //Creando el objeto edicion
            EdicionFormulario edF = new EdicionFormulario();

            edF.setFormularioNUE(formulario);
            edF.setUsuarioidUsuario(usuarioSesion);
            edF.setObservaciones(obsEdicion);
            edF.setFechaEdicion(new Date(System.currentTimeMillis()));

            //Actualizando ultima edicion formulario
            formulario.setUltimaEdicion(edF.getFechaEdicion());

            if (obsEdicion != null && parte <= 0 && ruc == null && rit == null) {
                logger.exiting(this.getClass().getName(), "edicionFormulario", "falta observación.");
                return "Se requiere la observación.";
            }

            if (obsEdicion != null) {
                edicionFormularioFacade.edit(edF);
                formularioFacade.edit(formulario);
                logger.log(Level.INFO, "se ha insertado observacion {0}", formulario.getObservaciones());
            }

            if (parte > 0) {
                edF.setObservaciones("Se ingresa número de parte: " + parte);
                edicionFormularioFacade.edit(edF);
                formulario.setNumeroParte(parte);
                formularioFacade.edit(formulario);
                logger.log(Level.INFO, "se ha insertado n Parte {0}", formulario.getNumeroParte());
            }

            if (rit != null && !rit.equals("") && validacionEJB.checkRucOrRit(rit)) {
                edF.setObservaciones("Se ingresa R.I.T: " + rit);
                edicionFormularioFacade.edit(edF);
                formulario.setRit(rit);
                formularioFacade.edit(formulario);
                logger.log(Level.INFO, "se ha insertado rit {0}", formulario.getRit());
            }

            if (ruc != null && !ruc.equals("") && validacionEJB.checkRucOrRit(ruc)) {
                edF.setObservaciones("Se ingresa R.U.C.: " + ruc);
                edicionFormularioFacade.edit(edF);
                formulario.setRuc(ruc);
                formularioFacade.edit(formulario);
                logger.log(Level.INFO, "se ha insertado ruc {0}", formulario.getRuc());
            }

            logger.exiting(this.getClass().getName(), "edicionFormulario", "Exito");
            return "Exito";
        } else {
            logger.exiting(this.getClass().getName(), "edicionFormulario", "falta observación.");
            return "Se requiere la observación.";
        }

    }

    //retorna true cuando el usuario si ha particiado en la cc.
    @Override
    public boolean esParticipanteCC(Formulario formulario, Usuario usuario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "obtenerParticipantesCC");
        if (usuario.equals(formulario.getUsuarioidUsuarioInicia())) {
            logger.exiting(this.getClass().getName(), "obtenerParticipantesCC", true);
            return true;
        }
        List<Traslado> traslados = trasladoFacade.findByNue(formulario);
        if (traslados == null || traslados.isEmpty()) {
            logger.log(Level.INFO, "formulario ''{0}'' no registra traslados", formulario.getNue());
            logger.exiting(this.getClass().getName(), "obtenerParticipantesCC", false);
            return false;
        }
        for (int i = 0; i < traslados.size(); i++) {
            if (traslados.get(i).getUsuarioidUsuarioRecibe().equals(usuario)) {
                logger.exiting(this.getClass().getName(), "obtenerParticipantesCC", true);
                return true;
            }
        }
        for (int i = 0; i < traslados.size(); i++) { //util solo para caso del digitador, ya que no se asegura que quien inicia o recibe sea el mismo que entrega.
            if (traslados.get(i).getUsuarioidUsuarioEntrega().equals(usuario)) {
                logger.exiting(this.getClass().getName(), "obtenerParticipantesCC", true);
                return true;
            }
        }

        logger.exiting(this.getClass().getName(), "obtenerParticipantesCC", false);
        return false;
    }
    
    @Override
    public List<Formulario> findByNParteRR(String input, String aBuscar) {

        List<Formulario> formulariosList = new ArrayList<>();
        
        if (input.equals("") || aBuscar.equals("")) {
            return formulariosList;
        }
        
        switch (aBuscar) {
            case "NumeroParte":
                if (validacionEJB.esNumero(input)) { //si no son solo numeros, retorna lista vacía.
                    formulariosList = formularioFacade.findByNParte(Integer.parseInt(input));
                }   break;
            case "Ruc":
                if (validacionEJB.checkRucOrRit(input)) { //si no es un ruc valido, retorna lista vacia
                    formulariosList = formularioFacade.findByRuc(input);
                }   break;
            default:
                //es rit
                if (validacionEJB.checkRucOrRit(input)) { //si no es un rit valido, retorna lista vacia
                    formulariosList = formularioFacade.findByRit(input);
                }   break;
        }
        return formulariosList;
    }

    private Traslado ultimoTraslado(int nue) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "ultimoTraslado", nue);
        Traslado traslado = null;
        Formulario f = formularioFacade.find(nue);
        if(f != null){
            List<Traslado> trasladosList = f.getTrasladoList();
            traslado = trasladosList.get(trasladosList.size() -1);
        }
        logger.exiting(this.getClass().getName(), "ultimoTraslado", traslado.toString());
        return traslado;
    }

}
