/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import facade.SemaforoFacadeLocal;
import entity.Semaforo;
import entity.Usuario;
import facade.AbstractFacade;
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
public class SemaforoFacade extends AbstractFacade<Semaforo> implements SemaforoFacadeLocal {
    @PersistenceContext(unitName = "com.pingeso_sml4-ejb_ejb_3.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SemaforoFacade() {
        super(Semaforo.class);
    }

    @Override
    public Semaforo findByColor(String color) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findByColor", color);
        Semaforo retorno = null;
        try {
            Query q = em.createNamedQuery("Semaforo.findBySemaforo", Semaforo.class).setParameter("semaforo", color);
            
            retorno = (Semaforo) q.getSingleResult();
        } catch (IllegalArgumentException e) {
            logger.severe("SemaforoFacade: el nombre o el parametro de la Query no existe -> " + e);
            retorno = null;
        } catch (NoResultException e) {
            logger.severe("SemaforoFacade: No hay resultados -> " + e);
            retorno = null;
        } catch (NonUniqueResultException e) {
            logger.severe("SemaforoFacade: hay mas de un resulado -> " + e);
            retorno = null;
        } catch (IllegalStateException e) {
            logger.severe("SemaforoFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (QueryTimeoutException e) {
            logger.severe("SemaforoFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (TransactionRequiredException e) {
            logger.severe("SemaforoFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PessimisticLockException e) {
            logger.severe("SemaforoFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (LockTimeoutException e) {
            logger.severe("SemaforoFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PersistenceException e) {
            logger.severe("SemaforoFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        }
        if (retorno == null) {
            logger.exiting(this.getClass().getName(), "findByCuentaUsuario", null);
            return null;
        } else {
            logger.exiting(this.getClass().getName(), "findByCuentaUsuario", retorno.toString());
            return retorno;
        }
    }
    
}
