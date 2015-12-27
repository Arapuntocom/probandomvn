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

    //Función para verificar la existencia del usuario en el sistema
    @Override
    public String verificarUsuario(String user, String pass) {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "Función verificarUsuario", user);
        //Buscamos al usuario segun su cuenta usuario
        Usuario foundUser = usuarioFacade.findByCuentaUsuario(user);
        String direccion = "";
        //Si lo encuentro verifico si la contraseña es igual a la que se ingreso
        if (foundUser != null) {
            if (foundUser.getPassUsuario().equals(pass)) {
                //Redirecciono según el cargo a su respectiva vista
                if (foundUser.getCargoidCargo().getNombreCargo().equals("Perito")) {
                    direccion = "/perito/peritoFormulario.xhtml?faces-redirect=true";
                } else if (foundUser.getCargoidCargo().getNombreCargo().equals("Chofer")) {
                    direccion = "/chofer/choferFormulario.xhtml?faces-redirect=true";
                } else if (foundUser.getCargoidCargo().getNombreCargo().equals("Digitador")) {
                    direccion = "/digitador/digitadorFormularioHU11.xhtml?faces-redirect=true";
                } else if (foundUser.getCargoidCargo().getNombreCargo().equals("Tecnico")) {
                    direccion = "/tecnico/buscadorTecnico.xhtml?faces-redirect=true";
                } else if (foundUser.getCargoidCargo().getNombreCargo().equals("Jefe de area")){
                    direccion = "/jefeArea/buscadorJefeArea.xhtml?faces-redirect=true";
                }
            }
        }
        logger.exiting(this.getClass().getName(), "Función verificarUsuario", direccion);
        return direccion;
    }

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

}
