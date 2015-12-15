/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

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

    static final Logger logger = Logger.getLogger(FormularioEJB.class.getName());

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
    public String crearTraslado(int nue, Usuario usuarioEntrega, Usuario usuarioRecibe, Date fechaT, Date fechaFormulario, String obs, String motivo) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearTraslado");
        if (nue > 0 && usuarioEntrega != null && usuarioRecibe != null && motivo != null) {
            Formulario formulario = formularioFacade.find(nue);
            if (formulario == null) {
                logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Formulario");
                return "Error. Formulario no existe.";
            }            
            List<Traslado> trasladoList = traslados(formulario);
            if( !trasladoList.isEmpty() && !compareFechas(fechaT, trasladoList.get(trasladoList.size() -1).getFechaEntrega()) ){
                logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Fecha");
                return "Error, la fecha del nuevo traslado debe ser igual o posterior a la ultima fecha de traslado.";
            }
            
            if(!compareFechas(fechaT, fechaFormulario)){
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
            //Validando rut de los usuarios ingresados
            if (val(usuarioEntrega.getRutUsuario()) && val(usuarioRecibe.getRutUsuario())) {
                usuarioEntregaP = usuarioFacade.findByRUN(usuarioEntrega.getRutUsuario());
                usuarioRecibeP = usuarioFacade.findByRUN(usuarioRecibe.getRutUsuario());
            } else {
                logger.exiting(this.getClass().getName(), "crearTraslado", "Error con ruts de usuarios");
                return "Error. Verificar run.";
            }

            if (usuarioEntregaP == null) { //el usuario no esta en la bd
                usuarioEntregaP = crearExterno(usuarioEntrega);
                if (usuarioEntregaP == null) {
                    logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Usuario Entrega");
                    return "Error con datos de la persona que entrega.";
                }
            }
            if (usuarioRecibeP == null) {
                usuarioRecibeP = crearExterno(usuarioRecibe);
                if (usuarioRecibeP == null) {
                    logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Usuario Recibe");
                    return "Error con datos de la persona que recibe.";
                }
            }
            
            Traslado nuevoTraslado = new Traslado();
            nuevoTraslado.setFechaEntrega(fechaT);
            nuevoTraslado.setFormularioNUE(formulario);
            nuevoTraslado.setObservaciones(obs);
            nuevoTraslado.setTipoMotivoidMotivo(motivoP);
            nuevoTraslado.setUsuarioidUsuario(usuarioRecibeP);
            nuevoTraslado.setUsuarioidUsuario1(usuarioEntregaP);
            logger.info("se inicia insercion del nuevo traslado");
            trasladoFacade.create(nuevoTraslado);
            logger.info("se finaliza insercion del nuevo traslado");
            logger.exiting(this.getClass().getName(), "crearTraslado", "Exito");
            return "Exito";
        }
        logger.exiting(this.getClass().getName(), "crearTraslado", "Erro. Faltan datos");
        return "Error. Faltan datos.";
    }

    private boolean compareFechas(Date fechaT, Date fechaFormulario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "compareFechas");
        if (fechaT != null && fechaFormulario != null) {
            Date dateTraslado = fechaT;
            Date dateFormulario = fechaFormulario;
            if (dateTraslado.equals(dateFormulario) || dateTraslado.after(dateFormulario)) {
                logger.exiting(this.getClass().getName(), "compareFechas", true);
                return true;
            }
        } else {
            logger.severe("Error con fechas");
        }
        logger.exiting(this.getClass().getName(), "compareFechas", false);
        return false;
    }

    //Función que verifica el rut, entrega true solo con el siguiente formato (18486956k) sin puntos ni guión.
    private boolean val(String rut) {

        int contadorPuntos = 0;
        int contadorGuion = 0;

        int largoR = rut.length();

        //Verifico que no tenga puntos y que tenga 1 solo guion
        for (int i = 0; i < largoR; i++) {
            if (rut.charAt(i) == 46) {
                contadorPuntos++;
            }
            if (rut.charAt(i) == 45) {
                contadorGuion++;
            }

        }

        if (contadorPuntos > 0 || contadorGuion > 0) {
            return false;
        }

        try {
            rut = rut.toUpperCase();
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                return true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return false;
    }

    private Usuario crearExterno(Usuario usuario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearExterno");
        if (usuario != null) {
            Usuario nuevoExterno = usuario;
            Area areaExterno = areaFacade.findByArea("Otro");
            TipoUsuario tue = tipoUsuarioFacade.findByTipo("Externo");
            Cargo cargoExterno = cargoFacade.findByCargo("Externo");
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
    
    //Función que verifica el ruc y el rit, solamente entrega true en los siguientes casos (513-21321) y ().
    private boolean checkRucOrRit(String rucOrRit) {
 
        if (rucOrRit.equals("")) {
            return true;
        }
        int largoTotal = rucOrRit.length();
        String lastGuion = "" + rucOrRit.charAt(largoTotal - 1);
        String[] numeros = rucOrRit.split("-");
        int largoN = numeros.length;
        int largoInterno = 0;
        System.out.println("Largo: " + largoN);
 
        if (lastGuion.equals("-")) {
            return false;
        }
        if (largoN == 2) {
            for (int i = 0; i < largoN; i++) {
                largoInterno = numeros[i].length();
                if (largoInterno == 0) {
                    return false;
                }
                for (int j = 0; j < largoInterno; j++) {
                    if (numeros[i].charAt(j) < 48 || numeros[i].charAt(j) > 57) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
 
    }

    
     @Override
    public List<Traslado> traslados(Formulario formulario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "traslados", formulario.toString());
        List<Traslado> retorno = trasladoFacade.findByNue(formulario);
        if (retorno == null) {
            logger.exiting(this.getClass().getName(), "traslados", null);
            return null;
        } else {
            logger.exiting(this.getClass().getName(), "traslados", retorno.size());
            return retorno;
        }
    }

    @Override
    public String crearFormulario(Formulario formulario, Usuario usuarioInicia, Usuario usuarioSesion) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearFormulario");
        if (formulario != null && usuarioInicia != null && formulario.getNue() > 0) {
            Formulario formularioP = formularioFacade.find(formulario.getNue()); //verificamos que no haya otro formulario con el mismo nue
            if (formularioP == null) { //significa que no hay otro formulario con el mismo nue
                
                if(!(checkRucOrRit(formulario.getRuc()) && checkRucOrRit(formulario.getRit()))){
                    logger.exiting(this.getClass().getName(), "crearFormulario", "Error con RUC o RIT");
                    return "Error con RUC o RIT.";
                } 
                
                Usuario usuarioIniciaP = null;
               
                if (val(usuarioInicia.getRutUsuario())) { //Verificando el rut
                    usuarioIniciaP = usuarioFacade.findByRUN(usuarioInicia.getRutUsuario()); //buscamos al usuario que ingresa el formulario
                }
                else{
                    logger.exiting(this.getClass().getName(), "crearFormulario", "Error con Usuario que inicia");
                    return "Error con datos de la persona que levanta el formulario.";
                }
 
                if (usuarioIniciaP == null) { //significa que el usuario no existe en la bd.
                    usuarioIniciaP = crearExterno(usuarioInicia);
                    if(usuarioIniciaP == null){
                        logger.exiting(this.getClass().getName(), "crearFormulario", "Error con usuario iniciante externo");
                        return "Error con datos de la persona que levanta el formulario.";
                    }
                }
                
                               
                
                Formulario nuevoFormulario = formulario;
               
                nuevoFormulario.setDireccionFotografia("C:");
                nuevoFormulario.setFechaIngreso(new Date(System.currentTimeMillis()));
                nuevoFormulario.setUltimaEdicion(nuevoFormulario.getFechaIngreso());
                nuevoFormulario.setUsuarioidUsuario(usuarioIniciaP);
                nuevoFormulario.setUsuarioidUsuario1(usuarioIniciaP);
 
                logger.finest("se inicia la persistencia del nuevo formulario");
                formularioFacade.create(nuevoFormulario);
                logger.finest("se finaliza la persistencia del nuevo formulario");
                logger.exiting(this.getClass().getName(), "crearFormulario", true);
                return "Exito";
            } else { //ya existe un forulario con el mismo nue.
                logger.exiting(this.getClass().getName(), "crearFormulario", "Error con Formulario");
                return "Error. Formulario ya existe.";
            }
        } else {
            logger.exiting(this.getClass().getName(), "crearFormulario", false);
        }
        return "Error. Faltan datos.";
    }

}
