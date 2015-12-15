/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.TipoUsuario;
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
public class TipoUsuarioFacade extends AbstractFacade<TipoUsuario> implements TipoUsuarioFacadeLocal {
    @PersistenceContext(unitName = "com.pingeso_sml4-ejb_ejb_3.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoUsuarioFacade() {
        super(TipoUsuario.class);
    }

    @Override
    public TipoUsuario findByTipo(String nombreTipo) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findByTipo", nombreTipo);
        TipoUsuario retorno = null;
        try {
            Query q = em.createNamedQuery("TipoUsuario.findByNombreTipo", TipoUsuario.class).setParameter("nombreTipo", nombreTipo);
            //retorno = new Usuario();
            retorno = (TipoUsuario) q.getSingleResult();
        } catch (IllegalArgumentException e) {
            logger.severe("TipoUsuarioFacade: el nombre o el parametro de la Query no existe -> " + e);
            retorno = null;
        } catch (NoResultException e) {
            logger.severe("TipoUsuarioFacade: No hay resultados -> " + e);
            retorno = null;
        } catch (NonUniqueResultException e) {
            logger.severe("TipoUsuarioFacade: hay mas de un resulado -> " + e);
            retorno = null;
        } catch (IllegalStateException e) {
            logger.severe("TipoUsuarioFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (QueryTimeoutException e) {
            logger.severe("TipoUsuarioFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (TransactionRequiredException e) {
            logger.severe("TipoUsuarioFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PessimisticLockException e) {
            logger.severe("TipoUsuarioFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (LockTimeoutException e) {
            logger.severe("TipoUsuarioFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PersistenceException e) {
            logger.severe("TipoUsuarioFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        }
        if (retorno == null) {
            logger.exiting(this.getClass().getName(), "findByTipo", null);
            return null;
        } else {
            logger.exiting(this.getClass().getName(), "findByTipo", retorno.toString());
            return retorno;
        }
    }
    
}
