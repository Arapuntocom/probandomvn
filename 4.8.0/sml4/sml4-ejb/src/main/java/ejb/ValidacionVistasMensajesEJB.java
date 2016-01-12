/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Alan
 */
@Stateless
public class ValidacionVistasMensajesEJB implements ValidacionVistasMensajesEJBLocal {

    @EJB
    private ValidacionEJBLocal validacionEJB;

    @Override
    public String checkRuc(String ruc){
        if(ruc == null || ruc.equals("") || !validacionEJB.checkRucOrRit(ruc)){
            System.out.println("Error ruc ------> "+ruc);
            return "Debe ingresar un R.U.C. válido";
        }
        System.out.println("RUC ------> "+ruc);
        return "Exito";
    }
    
    @Override
    public String checkRit(String rit){
        if(rit == null || rit.equals("") || !validacionEJB.checkRucOrRit(rit)){
            return "Debe ingresar un R.I.T. válido";
        }
        return "Exito";
    }

    @Override
    public String checkParte(Integer numeroParte) {
        if(numeroParte <= 0){
            return "Debe ingresar un N° Parte válido";
        }
        return "Exito";
    }
}
