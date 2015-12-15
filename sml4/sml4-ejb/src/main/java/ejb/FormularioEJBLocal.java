/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Formulario;
import entity.Traslado;
import entity.Usuario;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Aracelly
 */
@Local
public interface FormularioEJBLocal {

    public Formulario findFormularioByNue(int nueAbuscar);

    public String crearFormulario(Formulario formulario, Usuario usuarioInicia, Usuario usuarioSesion);
    
    public List<Traslado> traslados(Formulario formulario);

    public String crearTraslado(int nue, Usuario usuarioEntrega, Usuario usuarioRecibe, Date fechaT, Date fechaOcurrido, String obs, String motivo);
    
}
