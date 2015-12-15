/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Formulario;
import entity.Traslado;
import static facade.AbstractFacade.logger;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
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
public class TrasladoFacade extends AbstractFacade<Traslado> implements TrasladoFacadeLocal {

    @PersistenceContext(unitName = "com.pingeso_sml4-ejb_ejb_3.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrasladoFacade() {
        super(Traslado.class);
    }

    @Override
    public List<Traslado> findByNue(Formulario formulario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findByNue", formulario);
        List<Traslado> retorno = null;
        try {
            Query q = em.createNamedQuery("Traslado.findByNue", Traslado.class).setParameter("nue", formulario);
            retorno = q.getResultList();
            logger.log(Level.INFO, "buscar formulario by nue -> {0}", formulario.toString());
        } catch (IllegalArgumentException | IllegalStateException iae) {
            logger.log(Level.SEVERE, "TrasladoFacade: {0}", iae);
        } catch (QueryTimeoutException qte) {
            logger.log(Level.SEVERE, "TrasladoFacade: {0}", qte);
        } catch (TransactionRequiredException tre) {
            logger.log(Level.SEVERE, "TrasladoFacade: {0}", tre);
        } catch (PessimisticLockException ple) {
            logger.log(Level.SEVERE, "TrasladoFacade: {0}", ple);
        } catch (LockTimeoutException lte) {
            logger.log(Level.SEVERE, "TrasladoFacade: {0}", lte);
        } catch (PersistenceException pe) {
            logger.log(Level.SEVERE, "TrasladoFacade: {0}", pe);
        }
        if (retorno == null) {
            logger.exiting(this.getClass().getName(), "findByNue", null);
            return null;
        } else {
            logger.exiting(this.getClass().getName(), "findByNue", retorno.toString());
            return retorno;
        }
    }

}
