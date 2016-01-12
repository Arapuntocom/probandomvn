/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Area;
import entity.TipoEvidencia;
import entity.TipoMotivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sebastian
 */
@Local
public interface TipoEvidenciaFacadeLocal {

    void create(TipoEvidencia tipoEvidencia);

    void edit(TipoEvidencia tipoEvidencia);

    void remove(TipoEvidencia tipoEvidencia);

    TipoEvidencia find(Object id);

    List<TipoEvidencia> findAll();

    List<TipoEvidencia> findRange(int[] range);

    int count();
    
    TipoEvidencia findByNombreTipoEvidencia(String nombreTipoEvidencia);
    
    TipoEvidencia findByNombreAndTipoEvidencia(String nombreTipoEvidencia, Area areaEvidencia);
    
    //TipoMotivo findByTipoMotivo(String motivo);
    
}