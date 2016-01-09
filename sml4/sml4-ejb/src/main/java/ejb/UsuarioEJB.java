/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Area;
import entity.Cargo;
import entity.Usuario;
import facade.TipoUsuarioFacadeLocal;
import facade.UsuarioFacadeLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Aracelly
 */
@Stateless
public class UsuarioEJB implements UsuarioEJBLocal {

    @EJB
    private TipoUsuarioFacadeLocal tipoUsuarioFacade;


    @EJB
    private UsuarioFacadeLocal usuarioFacade;
  
    
    @EJB
    private ValidacionEJBLocal valdicionEJB;

    static final Logger logger = Logger.getLogger(UsuarioEJB.class.getName());


    @Override
    public Usuario findUsuarioSesionByCuenta(String cuentaUsuario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findUsuarioSesionByCuenta", cuentaUsuario);
        Usuario foundUser = usuarioFacade.findByCuentaUsuario(cuentaUsuario);
        if (foundUser != null) {
            logger.exiting(this.getClass().getName(), "findUsuarioSesionByCuenta", foundUser.toString());
            return foundUser;
        } else {
            logger.exiting(this.getClass().getName(), "findUsuarioSesionByCuenta", "Error con usuario");
            return null;
        }
    }
    

    /*
     OJO CON LA UNIDAD HAY QUE ACTUALIZAR LA UNIDAD
     A ESTE EJB HAY QUE AGREGAR LAS FUNCIONES VALIDAR CUENTA,PASS, EL EMAIL, RUT Y SOLO CARACTERES
     ADEMAS CREE UNA QUERY EN EL FACADE DE USUARIO BUSCANDO POR EL EMAIL
     DISCUTIR EL TEMA DE LA VERIFICACION DE LA PASS Y CUENTA DE USUARIO
     */
    @Override
    public String crearUsuario(String nombreUsuario, String apellidoUsuario, String rut, String pass, String mail, String cuentaUsuario, String estado, Cargo cargo, Area area) {

        //Verifico el rut
        if (!valdicionEJB.val(rut)) {
            return "Rut inválido";
        }

        //Verifico si existe ese rut en la BD
        if (usuarioFacade.findByRUN(rut) != null) {
            return "Rut existente";
        }

        //Verifico el nombreUsuario
        if (!valdicionEJB.soloCaracteres(nombreUsuario)) {
            return "Nombre ingresado incorrecto";
        }
        //Verifico el apellido
        if (!valdicionEJB.soloCaracteres(apellidoUsuario)) {
            return "Apellido ingresado incorrecto";
        }

        //Tengo que verificar si el email existe
        //Verifico el email
        if (!valdicionEJB.validarEmail(mail) || usuarioFacade.findByEmail(mail) != null) {
            return "Email inválido";
        }
        
        
        //Tengo que verificar si la cuenta de usuario existe
        //Validar cuenta de usuario
        if(!valdicionEJB.validarCuentaUsuario(cuentaUsuario) || usuarioFacade.findByCuentaUsuario(cuentaUsuario) != null){
            return "Cuenta de usuario inválida";
        }
        
        if(!valdicionEJB.validarPassUsuario(pass)){
            return "Contraseña inválida";
        }
        
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(nombreUsuario);
        nuevoUsuario.setApellidoUsuario(apellidoUsuario);
        nuevoUsuario.setRutUsuario(rut);
        nuevoUsuario.setPassUsuario(pass);
        nuevoUsuario.setMailUsuario(mail);
        nuevoUsuario.setCuentaUsuario(cuentaUsuario);
        if(estado.equals("Activo")){
             nuevoUsuario.setEstadoUsuario(Boolean.TRUE);
        }else{
            nuevoUsuario.setEstadoUsuario(Boolean.FALSE);
        }
        nuevoUsuario.setCargoidCargo(cargo);
        nuevoUsuario.setAreaidArea(area);
        
        /*}TipoUsuario tu = new TipoUsuario();
        /tu = tipoUsuarioFacade.findByTipo("SML");
        if(tu == null){
            return "Tipo de usuario no existe";
        }
        nuevoUsuario.setTipoUsuarioidTipoUsuario(tu);
       */

        return "Exito";
    }


    //OJO ARREGLAR EL INICIO DE SESION SI EL USUARIO ESTA INACTIVO NO PUEDE INGRESAR EN SU CUENTA OJOJOJOJOJOJOJOJO !!!!!!!!!!!!
    @Override
    public boolean edicionEstadoUsuario(String rut, String estado){
    
        Usuario usuario = new Usuario();
        usuario = usuarioFacade.findByRUN(rut);
        if(usuario == null){
            return false;
        }
        if(estado.equals("Activo")){
            usuario.setEstadoUsuario(Boolean.TRUE);
        }
        else{
            usuario.setEstadoUsuario(Boolean.FALSE);
        }
        
        usuarioFacade.edit(usuario);
        return true;
    }
    

}
