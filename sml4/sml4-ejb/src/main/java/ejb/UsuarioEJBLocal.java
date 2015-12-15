/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Usuario;
import javax.ejb.Local;

/**
 *
 * @author Aracelly
 */
@Local
public interface UsuarioEJBLocal {

    public boolean verificarUsuario(String user, String pass);

    public Usuario findUsuarioSesionByCuenta(String cuentaUsuario);
    
}
