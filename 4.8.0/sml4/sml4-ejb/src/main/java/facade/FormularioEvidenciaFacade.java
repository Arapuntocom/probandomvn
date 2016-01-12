/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Formulario;
import entity.FormularioEvidencia;
import static facade.AbstractFacade.logger;
import java.util.List;
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
 * @author Alan
 */
@Stateless
public class FormularioEvidenciaFacade extends AbstractFacade<FormularioEvidencia> implements FormularioEvidenciaFacadeLocal {

    @PersistenceContext(unitName = "com.pingeso_sml4-ejb_ejb_3.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FormularioEvidenciaFacade() {
        super(FormularioEvidencia.class);
    }
    
    //creara por programador 
    //   @NamedQuery(name = "FormularioEvidencia.findByFormulario", query = "SELECT f FROM FormularioEvidencia f WHERE f.formularioNUE = :formularioNUE")
    @Override
    public List<FormularioEvidencia> findByFormulario(Formulario formulario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findByFormulario", formulario);
        List<FormularioEvidencia> retorno = null;
        try {
            Query q = em.createNamedQuery("FormularioEvidencia.findByFormulario", FormularioEvidencia.class).setParameter("formularioNUE", formulario);
            retorno =  q.getResultList();
            logger.log(Level.INFO, "buscar formulario-evidencia by numero de parte -> {0}", formulario);
        } catch (IllegalArgumentException e) {
            logger.severe("FormularioEvidenciaFacade: el nombre o el parametro de la Query no existe -> " + e);
            retorno = null;
        } catch (NoResultException e) {
            logger.severe("FormularioEvidenciaFacade: No hay resultados -> " + e);
            retorno = null;
        } catch (NonUniqueResultException e) {
            logger.severe("FormularioEvidenciaFacade: hay mas de un resulado -> " + e);
            retorno = null;
        } catch (IllegalStateException e) {
            logger.severe("FormularioEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (QueryTimeoutException e) {
            logger.severe("FormularioEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (TransactionRequiredException e) {
            logger.severe("FormularioEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PessimisticLockException e) {
            logger.severe("FormularioEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (LockTimeoutException e) {
            logger.severe("FormularioEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        } catch (PersistenceException e) {
            logger.severe("FormularioEvidenciaFacade: ocurrio un problema con la consulta -> " + e);
            retorno = null;
        }
        if (retorno == null) {
            logger.exiting(this.getClass().getName(), "findByFormulario", null);
            return null;
        } else {
            logger.exiting(this.getClass().getName(), "findByFormulario", retorno.toString());
            return retorno;
        }
    }
    
}
