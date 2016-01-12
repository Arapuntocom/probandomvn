/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Local;

/**
 *
 * @author Alan
 */
@Local
public interface ValidacionVistasMensajesEJBLocal {
    public String checkRuc(String ruc);
    public String checkRit(String rit);    

    public String checkParte(Integer numeroParte);
}
