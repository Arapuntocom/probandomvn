/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Semaforo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sebastian
 */
@Local
public interface SemaforoFacadeLocal {

    void create(Semaforo semaforo);

    void edit(Semaforo semaforo);

    void remove(Semaforo semaforo);

    Semaforo find(Object id);

    List<Semaforo> findAll();

    List<Semaforo> findRange(int[] range);

    int count();

    public Semaforo findByColor(String amarillo);
    
}