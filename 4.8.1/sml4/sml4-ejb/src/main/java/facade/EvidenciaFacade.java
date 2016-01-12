/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Area;
import entity.Evidencia;
import entity.TipoEvidencia;
import entity.Usuario;
import static facade.AbstractFacade.logger;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
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
public class EvidenciaFacade extends AbstractFacade<Evidencia> implements EvidenciaFacadeLocal {
    @PersistenceContext(unitName = "com.pingeso_sml4-ejb_ejb_3.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EvidenciaFacade() {
        super(Evidencia.class);
    }

    // query creada por programador (ara)
    /*@NamedQuery(name = "Evidencia.findByNombreAndTipoEvidencia", 
    query = "SELECT e FROM Evidencia e WHERE e.nombreEvidencia = :nombreEvidencia AND e.tipoEvidenciaidTipoEvidencia = :tipoEvidenciaidTipoEvidencia")
*/
    @Override
    public Evidencia findByNombreAndTipoEvidencia(String evidencia, TipoEvidencia tipoEvid) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findByNombreAndTipoEvidencia");
        Evidencia retorno = null;
        try {
            Query q = em.createNamedQuery("Evidencia.findByNombreAndTipoEvidencia", Evidencia.class).setParameter("nombreEvidencia", evidencia).setParameter("tipoEvidenciaidTipoEvidencia", tipoEvid);
            retorno = (Evidencia) q.getSingleResult();
        } catch (IllegalArgumentException e) {
            logger.severe("EvidenciaFacade: el nombre o el parametro de la Query no existe -> " + e);
            retorno = null;
        } catch (NoResultException e) {
            logger.severe("EvidenciaFacade: No hay resultados -> " + e);
            retorno = null;
        } catch (NonUniqueResultException e) {
            logger.severe("EvidenciaFacade: hay mas de un resulado -> " + e);
            retorno = null;
        } catch (IllegalStateException e) {
            logger.severe("EvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (QueryTimeoutException e) {
            logger.severe("EvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (TransactionRequiredException e) {
            logger.severe("EvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PessimisticLockException e) {
            logger.severe("EvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (LockTimeoutException e) {
            logger.severe("EvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PersistenceException e) {
            logger.severe("EvidenciaFacade: ocurrio un problema con la consulta -> " + e);
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
    
}