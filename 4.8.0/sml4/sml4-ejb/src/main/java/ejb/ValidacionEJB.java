/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Usuario;
import facade.FormularioFacadeLocal;
import facade.UsuarioFacadeLocal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author sebastian
 */
@Stateless
public class ValidacionEJB implements ValidacionEJBLocal {

    @EJB
    private FormularioFacadeLocal formularioFacade;

    @EJB
    private UsuarioFacadeLocal usuarioFacade;     

    static final Logger logger = Logger.getLogger(ValidacionEJB.class.getName());

    @Override
    public boolean compareFechas(Date fechaT, Date fechaFormulario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "compareFechas");
        if (fechaT != null && fechaFormulario != null) {
            Date dateTraslado = fechaT;
            Date dateFormulario = fechaFormulario;
            if (dateTraslado.equals(dateFormulario) || dateTraslado.after(dateFormulario)) {
                logger.exiting(this.getClass().getName(), "compareFechas", true);
                return true;
            }
        } else {
            logger.severe("Error con fechas");
        }
        logger.exiting(this.getClass().getName(), "compareFechas", false);
        return false;
    }

    @Override
    //Función que verifica el rut, entrega true solo con el siguiente formato (18486956k) sin puntos ni guión.
    public boolean val(String rut) {

        int contadorPuntos = 0;
        int contadorGuion = 0;

        int largoR = rut.length();

        //Verifico que no tenga puntos y que tenga 1 solo guion
        for (int i = 0; i < largoR; i++) {
            if (rut.charAt(i) == 46) {
                contadorPuntos++;
            }
            if (rut.charAt(i) == 45) {
                contadorGuion++;
            }

        }

        if (contadorPuntos > 0 || contadorGuion > 0) {
            return false;
        }

        try {
            rut = rut.toUpperCase();
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                return true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public boolean soloCaracteres(String palabra) {

        Pattern patron = Pattern.compile("/^[a-zA-Z áéíóúAÉÍÓÚÑñ]+$/");
        Matcher encaja = patron.matcher(palabra);

        if (!encaja.find()) {
            System.out.println(palabra + " -> solo tiene letras y espacio!");
            return true;
        } else {
            System.out.println(palabra + " -> tiene otra cosa");
            return false;
        }

    }
    
    @Override
    //Función que verifica el ruc y el rit, solamente entrega true en los siguientes casos (513-21321) y ().
    public boolean checkRucOrRit(String rucOrRit) {

        if (rucOrRit.equals("")) {
            return true;
        }
        int largoTotal = rucOrRit.length();
        String lastGuion = "" + rucOrRit.charAt(largoTotal - 1);
        String[] numeros = rucOrRit.split("-");
        int largoN = numeros.length;
        int largoInterno = 0;
        System.out.println("Largo: " + largoN);

        if (lastGuion.equals("-")) {
            return false;
        }
        if (largoN == 2) {
            for (int i = 0; i < largoN; i++) {
                largoInterno = numeros[i].length();
                if (largoInterno == 0) {
                    return false;
                }
                for (int j = 0; j < largoInterno; j++) {
                    if (numeros[i].charAt(j) < 48 || numeros[i].charAt(j) > 57) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;

    }

    @Override
    public boolean esNumero(String numero) {

        try {
            Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException n) {
            return false;
        }
        return true;
    }   

    //Funcion para validar el email
    //Retorna true en el caso que sea valido
    @Override
    public boolean validarEmail(String email) {
        String patronEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(patronEmail);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    //Función para verificar la existencia del usuario en el sistema
    @Override
    public String verificarUsuario(String user, String pass) {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "Función verificarUsuario", user);
        //Buscamos al usuario segun su cuenta usuario
        Usuario foundUser = usuarioFacade.findByCuentaUsuario(user);
        String direccion = "";
        //Si lo encuentro verifico si la contraseña es igual a la que se ingreso
        if (foundUser != null) {
            if (foundUser.getPassUsuario().equals(pass)) {
                if(!foundUser.getEstadoUsuario()){
                    return "off";
                }
                //Redirecciono según el cargo a su respectiva vista
                if (foundUser.getCargoidCargo().getNombreCargo().equals("Perito")) {
                    direccion = "/perito/peritoFormulario.xhtml?faces-redirect=true";
                } else if (foundUser.getCargoidCargo().getNombreCargo().equals("Chofer")) {
                    direccion = "/chofer/choferFormulario.xhtml?faces-redirect=true";
                } else if (foundUser.getCargoidCargo().getNombreCargo().equals("Digitador")) {
                    direccion = "/digitador/digitadorFormularioHU11.xhtml?faces-redirect=true";
                } else if (foundUser.getCargoidCargo().getNombreCargo().equals("Técnico")) {
                    direccion = "/perito/buscadorPerito.xhtml?faces-redirect=true";
                } else if (foundUser.getCargoidCargo().getNombreCargo().equals("Jefe de área")) {
                    direccion = "/jefeArea/buscadorJefeArea.xhtml?faces-redirect=true";
                } else if(foundUser.getCargoidCargo().getNombreCargo().equals("Administrador")) {
                    direccion = "";
                
                }
            }
        }
        logger.exiting(this.getClass().getName(), "Función verificarUsuario", direccion);
        return direccion;
    }

    //minimo 8 caracteres
    @Override
    public boolean validarCuentaUsuario(String cuentaUsuario) {

        int largo = cuentaUsuario.length();
        if (largo < 8) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validarPassUsuario(String passUsuario) {

        int largo = passUsuario.length();
        if (largo < 8) {
            return false;
        }
        return true;
    }

}
