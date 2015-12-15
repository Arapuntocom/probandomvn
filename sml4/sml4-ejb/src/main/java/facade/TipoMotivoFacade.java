/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.TipoMotivo;
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
 * @author Aracelly
 */
@Stateless
public class TipoMotivoFacade extends AbstractFacade<TipoMotivo> implements TipoMotivoFacadeLocal {
    @PersistenceContext(unitName = "com.pingeso_sml4-ejb_ejb_3.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoMotivoFacade() {
        super(TipoMotivo.class);
    }

    @Override
    public TipoMotivo findByTipoMotivo(String motivo) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findByTipoMotivo", motivo);
        TipoMotivo retorno = null;
        try {
            Query q = em.createNamedQuery("TipoMotivo.findByTipoMotivo", TipoMotivo.class).setParameter("tipoMotivo", motivo);
            retorno = (TipoMotivo) q.getSingleResult();
        } catch (IllegalArgumentException e) {
            logger.severe("TipoMotivoFacade: el nombre o el parametro de la Query no existe -> " + e);
            retorno = null;
        } catch (NoResultException e) {
            logger.severe("TipoMotivoFacade: No hay resultados -> " + e);
            retorno = null;
        } catch (NonUniqueResultException e) {
            logger.severe("TipoMotivoFacade: hay mas de un resulado -> " + e);
            retorno = null;
        } catch (IllegalStateException e) {
            logger.severe("TipoMotivoFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (QueryTimeoutException e) {
            logger.severe("TipoMotivoFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (TransactionRequiredException e) {
            logger.severe("TipoMotivoFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PessimisticLockException e) {
            logger.severe("TipoMotivoFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (LockTimeoutException e) {
            logger.severe("TipoMotivoFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PersistenceException e) {
            logger.severe("TipoMotivoFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        }
        if (retorno == null) {
            logger.exiting(this.getClass().getName(), "findByTipoMotivo", null);
            return null;
        } else {
            logger.exiting(this.getClass().getName(), "findByTipoMotivo", retorno.toString());
            return retorno;
        }
    
    }
    
}
