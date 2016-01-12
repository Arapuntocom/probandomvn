/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Area;
import entity.TipoEvidencia;
import entity.TipoMotivo;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;

/**
 *
 * @author sebastian
 */
@Stateless
public class TipoEvidenciaFacade extends AbstractFacade<TipoEvidencia> implements TipoEvidenciaFacadeLocal {
    @PersistenceContext(unitName = "com.pingeso_sml4-ejb_ejb_3.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoEvidenciaFacade() {
        super(TipoEvidencia.class);
    }
    
    //creada por programador (ara)
    //@NamedQuery(name = "TipoEvidencia.findByNombreAndTipoEvidencia", query = "SELECT t FROM TipoEvidencia t WHERE t.nombreTipoEvidencia = :nombreTipoEvidencia AND t.areaidArea = :areaidArea")
@Override
    public TipoEvidencia findByNombreAndTipoEvidencia(String nombreTipoEvidencia, Area areaEvidencia) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findByNombreAndTipoEvidencia", nombreTipoEvidencia);
        TipoEvidencia retorno = null;
        try {
            Query q = em.createNamedQuery("TipoEvidencia.findByNombreAndTipoEvidencia", TipoEvidencia.class).setParameter("nombreTipoEvidencia", nombreTipoEvidencia).setParameter("areaidArea", areaEvidencia);
            retorno = (TipoEvidencia) q.getSingleResult();
        } catch (IllegalArgumentException e) {
            logger.severe("TipoEvidenciaFacade: el nombre o el parametro de la Query no existe -> " + e);
            retorno = null;
        } catch (NoResultException e) {
            logger.severe("TipoEvidenciaFacade: No hay resultados -> " + e);
            retorno = null;
        } catch (NonUniqueResultException e) {
            logger.severe("TipoEvidenciaFacade: hay mas de un resulado -> " + e);
            retorno = null;
        } catch (IllegalStateException e) {
            logger.severe("TipoEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (QueryTimeoutException e) {
            logger.severe("TipoEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (TransactionRequiredException e) {
            logger.severe("TipoEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PessimisticLockException e) {
            logger.severe("TipoEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (LockTimeoutException e) {
            logger.severe("TipoEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PersistenceException e) {
            logger.severe("TipoEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        }
        if (retorno == null) {
            logger.exiting(this.getClass().getName(), "findByNombreAndTipoEvidencia", null);
            return null;
        } else {
            logger.exiting(this.getClass().getName(), "findByNombreAndTipoEvidencia", retorno.toString());
            return retorno;
        }
   
    }
    
    
    //@NamedQuery(name = "TipoEvidencia.findByNombreTipoEvidencia", query = "SELECT t FROM TipoEvidencia t WHERE t.nombreTipoEvidencia = :nombreTipoEvidencia")
     @Override
    public TipoEvidencia findByNombreTipoEvidencia(String nombreTipoEvidencia) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findByNombreTipoEvidencia", nombreTipoEvidencia);
        TipoEvidencia retorno = null;
        try {
            Query q = em.createNamedQuery("TipoEvidencia.findByNombreTipoEvidencia", TipoEvidencia.class).setParameter("nombreTipoEvidencia", nombreTipoEvidencia);
            retorno = (TipoEvidencia) q.getSingleResult();
        } catch (IllegalArgumentException e) {
            logger.severe("TipoEvidenciaFacade: el nombre o el parametro de la Query no existe -> " + e);
            retorno = null;
        } catch (NoResultException e) {
            logger.severe("TipoEvidenciaFacade: No hay resultados -> " + e);
            retorno = null;
        } catch (NonUniqueResultException e) {
            logger.severe("TipoEvidenciaFacade: hay mas de un resulado -> " + e);
            retorno = null;
        } catch (IllegalStateException e) {
            logger.severe("TipoEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (QueryTimeoutException e) {
            logger.severe("TipoEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (TransactionRequiredException e) {
            logger.severe("TipoEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PessimisticLockException e) {
            logger.severe("TipoEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (LockTimeoutException e) {
            logger.severe("TipoEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PersistenceException e) {
            logger.severe("TipoEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        }
        if (retorno == null) {
            logger.exiting(this.getClass().getName(), "findByNombreTipoEvidencia", null);
            return null;
        } else {
            logger.exiting(this.getClass().getName(), "findByNombreTipoEvidencia", retorno.toString());
            return retorno;
        }
   
    }
    
//     //@NamedQuery(name = "TipoMotivo.findByTipoMotivo", query = "SELECT t FROM TipoMotivo t WHERE t.tipoMotivo = :tipoMotivo")
//    @Override
//    public TipoMotivo findByTipoMotivo(String motivo) {
//        logger.setLevel(Level.ALL);
//        logger.entering(this.getClass().getName(), "findByTipoMotivo", motivo);
//        TipoMotivo retorno = null;
//        try {
//            Query q = em.createNamedQuery("TipoMotivo.findByTipoMotivo", TipoMotivo.class).setParameter("tipoMotivo", motivo);
//            retorno = (TipoMotivo) q.getSingleResult();
//        } catch (IllegalArgumentException e) {
//            logger.severe("TipoMotivoFacade: el nombre o el parametro de la Query no existe -> " + e);
//            retorno = null;
//        } catch (NoResultException e) {
//            logger.severe("TipoMotivoFacade: No hay resultados -> " + e);
//            retorno = null;
//        } catch (NonUniqueResultException e) {
//            logger.severe("TipoMotivoFacade: hay mas de un resulado -> " + e);
//            retorno = null;
//        } catch (IllegalStateException e) {
//            logger.severe("TipoMotivoFacade: ocurrio un problema con la consulta -> " + e);
//            retorno = null;
//        } catch (QueryTimeoutException e) {
//            logger.severe("TipoMotivoFacade: ocurrio un problema con la consulta -> " + e);
//            retorno = null;
//        } catch (TransactionRequiredException e) {
//            logger.severe("TipoMotivoFacade: ocurrio un problema con la consulta -> " + e);
//            retorno = null;
//        } catch (PessimisticLockException e) {
//            logger.severe("TipoMotivoFacade: ocurrio un problema con la consulta -> " + e);
//            retorno = null;
//        } catch (LockTimeoutException e) {
//            logger.severe("TipoMotivoFacade: ocurrio un problema con la consulta -> " + e);
//            retorno = null;
//        } catch (PersistenceException e) {
//            logger.severe("TipoMotivoFacade: ocurrio un problema con la consulta -> " + e);
//            retorno = null;
//        }
//        if (retorno == null) {
//            logger.exiting(this.getClass().getName(), "findByTipoMotivo", null);
//            return null;
//        } else {
//            logger.exiting(this.getClass().getName(), "findByTipoMotivo", retorno.toString());
//            return retorno;
//        }
//   
//    } 
   
}
