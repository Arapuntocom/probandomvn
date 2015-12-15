/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Peritaje;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Aracelly
 */
@Local
public interface PeritajeFacadeLocal {

    void create(Peritaje peritaje);

    void edit(Peritaje peritaje);

    void remove(Peritaje peritaje);

    Peritaje find(Object id);

    List<Peritaje> findAll();

    List<Peritaje> findRange(int[] range);

    int count();
    
}
