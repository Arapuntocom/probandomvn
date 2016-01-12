/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Area;
import entity.Cargo;
import entity.Usuario;
import javax.ejb.Local;

/**
 *
 * @author Aracelly
 */
@Local
public interface UsuarioEJBLocal {

    public Usuario findUsuarioSesionByCuenta(String cuentaUsuario);

    public String crearUsuario(String nombreUsuario, String apellidoUsuario, String rut, String pass, String mail, String cuentaUsuario, String estado, Cargo cargo, Area area);

    public boolean edicionEstadoUsuario(String rut, String estado);

    public Usuario findUserByRut(String rut);
    
    public String crearUsuario(String nombreUsuario, String apellidoUsuario, String rut, String pass, String mail, String cuentaUsuario, String cargo, String area);
        
}
