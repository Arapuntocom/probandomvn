/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Traslado;
import entity.Formulario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sebastian
 */
@Local
public interface TrasladoFacadeLocal {

    void create(Traslado traslado);

    void edit(Traslado traslado);

    void remove(Traslado traslado);

    Traslado find(Object id);

    List<Traslado> findAll();

    List<Traslado> findRange(int[] range);

    int count();

    List<Traslado> findByNue(Formulario formulario);

}