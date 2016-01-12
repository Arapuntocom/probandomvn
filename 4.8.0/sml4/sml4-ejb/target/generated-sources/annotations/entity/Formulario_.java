package entity;

import entity.EdicionFormulario;
import entity.FormularioEvidencia;
import entity.Semaforo;
import entity.Traslado;
import entity.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-12T00:06:38")
@StaticMetamodel(Formulario.class)
public class Formulario_ { 

    public static volatile SingularAttribute<Formulario, Usuario> usuarioidUsuarioInicia;
    public static volatile SingularAttribute<Formulario, Boolean> bloqueado;
    public static volatile ListAttribute<Formulario, Traslado> trasladoList;
    public static volatile ListAttribute<Formulario, FormularioEvidencia> formularioEvidenciaList;
    public static volatile SingularAttribute<Formulario, String> lugarLevantamiento;
    public static volatile SingularAttribute<Formulario, Integer> nue;
    public static volatile SingularAttribute<Formulario, String> rit;
    public static volatile SingularAttribute<Formulario, String> delitoRef;
    public static volatile SingularAttribute<Formulario, String> direccionSS;
    public static volatile SingularAttribute<Formulario, String> ruc;
    public static volatile SingularAttribute<Formulario, String> unidadPolicial;
    public static volatile SingularAttribute<Formulario, String> direccionFotografia;
    public static volatile SingularAttribute<Formulario, Date> fechaIngreso;
    public static volatile SingularAttribute<Formulario, String> observaciones;
    public static volatile ListAttribute<Formulario, EdicionFormulario> edicionFormularioList;
    public static volatile SingularAttribute<Formulario, Usuario> usuarioidUsuario;
    public static volatile SingularAttribute<Formulario, Date> ultimaEdicion;
    public static volatile SingularAttribute<Formulario, Integer> numeroParte;
    public static volatile SingularAttribute<Formulario, Date> fechaOcurrido;
    public static volatile SingularAttribute<Formulario, Semaforo> semaforoidSemaforo;
    public static volatile SingularAttribute<Formulario, String> descripcionEspecieFormulario;

}