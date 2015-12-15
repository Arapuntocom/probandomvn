/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Usuario;
import facade.UsuarioFacadeLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Aracelly
 */
@Stateless
public class UsuarioEJB implements UsuarioEJBLocal {
    
    @EJB
    private UsuarioFacadeLocal usuarioFacade;    
    
    static final Logger logger = Logger.getLogger(UsuarioEJB.class.getName());
    
    //Función para verificar la existencia de un usuario en el sistema
    @Override
    public boolean verificarUsuario(String user, String pass) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(),"verificarUsuario", user);
        boolean correcto = false;
        //Buscamos al usuario segun su cuenta usuario
        Usuario foundUser = usuarioFacade.findByCuentaUsuario(user);
        
        //Si lo encuentro verifico si la contraseña es igual a la que se ingreso
        if (foundUser != null) {
            if (foundUser.getPassUsuario().equals(pass)) {
                correcto = true;                    
            }
        }
        logger.exiting(this.getClass().getName(), "verificarUsuario", correcto);
        return correcto;
    }  

    @Override
    public Usuario findUsuarioSesionByCuenta(String cuentaUsuario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(),"findUsuarioSesionByCuenta", cuentaUsuario);
        Usuario foundUser = usuarioFacade.findByCuentaUsuario(cuentaUsuario);
        if(foundUser != null){
            logger.exiting(this.getClass().getName(),"findUsuarioSesionByCuenta", foundUser.toString());
            return foundUser;
        }else{
            logger.exiting(this.getClass().getName(),"findUsuarioSesionByCuenta", "Error con usuario");
            return null;
        }
    }
    
}
