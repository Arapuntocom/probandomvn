/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.EdicionFormulario;
import entity.Formulario;
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
 * @author sebastian
 */
@Stateless
public class EdicionFormularioFacade extends AbstractFacade<EdicionFormulario> implements EdicionFormularioFacadeLocal {
    @PersistenceContext(unitName = "com.pingeso_sml4-ejb_ejb_3.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EdicionFormularioFacade() {
        super(EdicionFormulario.class);
    }
    
    //creada por programador (ara)
    //@NamedQuery(name = "EdicionFormulario.findByFormulario", query = "SELECT e FROM EdicionFormulario e WHERE e.formularioNUE = :formularioNUE")
    @Override
    public List<EdicionFormulario> listaEdiciones(Formulario formulario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "listEdiciones", formulario);
        List<EdicionFormulario> retorno = null;
        try {
            Query q = em.createNamedQuery("EdicionFormulario.findByFormulario", EdicionFormulario.class).setParameter("formularioNUE", formulario);            
            retorno = q.getResultList();
            logger.log(Level.INFO, "buscar formulario by nue -> {0}", formulario);
        } catch (IllegalArgumentException e) {
            logger.severe("EdicionFormularioFacade: el nombre o el parametro de la Query no existe -> " + e);
            retorno = null;
        } catch (IllegalStateException e) {
            logger.severe("EdicionFormularioFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (QueryTimeoutException e) {
            logger.severe("EdicionFormularioFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (TransactionRequiredException e) {
            logger.severe("EdicionFormularioFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PessimisticLockException e) {
            logger.severe("EdicionFormularioFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (LockTimeoutException e) {
            logger.severe("EdicionFormularioFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PersistenceException e) {
            logger.severe("EdicionFormularioFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        }
        if (retorno == null) {
            logger.exiting(this.getClass().getName(), "listaEdiciones", null);
            return null;
        } else {
            logger.exiting(this.getClass().getName(), "listaEdiciones", retorno.size());
            return retorno;
        }
    }
    
}