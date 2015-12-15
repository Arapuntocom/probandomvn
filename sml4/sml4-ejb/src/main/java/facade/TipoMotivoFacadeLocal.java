/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.TipoMotivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Aracelly
 */
@Local
public interface TipoMotivoFacadeLocal {

    void create(TipoMotivo tipoMotivo);

    void edit(TipoMotivo tipoMotivo);

    void remove(TipoMotivo tipoMotivo);

    TipoMotivo find(Object id);

    List<TipoMotivo> findAll();

    List<TipoMotivo> findRange(int[] range);

    int count();

    public TipoMotivo findByTipoMotivo(String motivo);
    
}
