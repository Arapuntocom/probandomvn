/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.EdicionFormulario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Aracelly
 */
@Local
public interface EdicionFormularioFacadeLocal {

    void create(EdicionFormulario edicionFormulario);

    void edit(EdicionFormulario edicionFormulario);

    void remove(EdicionFormulario edicionFormulario);

    EdicionFormulario find(Object id);

    List<EdicionFormulario> findAll();

    List<EdicionFormulario> findRange(int[] range);

    int count();
    
}
