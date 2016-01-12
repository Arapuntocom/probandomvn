package entity;

import entity.Formulario;
import entity.TipoMotivo;
import entity.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-12T06:48:46")
@StaticMetamodel(Traslado.class)
public class Traslado_ { 

    public static volatile SingularAttribute<Traslado, Formulario> formularioNUE;
    public static volatile SingularAttribute<Traslado, Integer> idTraslado;
    public static volatile SingularAttribute<Traslado, Date> fechaEntrega;
    public static volatile SingularAttribute<Traslado, Usuario> usuarioidUsuarioRecibe;
    public static volatile SingularAttribute<Traslado, String> observaciones;
    public static volatile SingularAttribute<Traslado, TipoMotivo> tipoMotivoidMotivo;
    public static volatile SingularAttribute<Traslado, Usuario> usuarioidUsuarioEntrega;

}