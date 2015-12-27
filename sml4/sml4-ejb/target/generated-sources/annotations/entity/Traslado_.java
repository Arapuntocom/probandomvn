package entity;

import entity.Formulario;
import entity.TipoMotivo;
import entity.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-27T07:23:11")
@StaticMetamodel(Traslado.class)
public class Traslado_ { 

    public static volatile SingularAttribute<Traslado, Formulario> formularioNUE;
    public static volatile SingularAttribute<Traslado, Integer> idInvolucrado;
    public static volatile SingularAttribute<Traslado, Usuario> usuarioidUsuario;
    public static volatile SingularAttribute<Traslado, Usuario> usuarioidUsuario1;
    public static volatile SingularAttribute<Traslado, Date> fechaEntrega;
    public static volatile SingularAttribute<Traslado, String> observaciones;
    public static volatile SingularAttribute<Traslado, TipoMotivo> tipoMotivoidMotivo;

}