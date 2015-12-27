package entity;

import entity.Formulario;
import entity.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-27T07:23:12")
@StaticMetamodel(EdicionFormulario.class)
public class EdicionFormulario_ { 

    public static volatile SingularAttribute<EdicionFormulario, Formulario> formularioNUE;
    public static volatile SingularAttribute<EdicionFormulario, Usuario> usuarioidUsuario;
    public static volatile SingularAttribute<EdicionFormulario, Date> fechaEdicion;
    public static volatile SingularAttribute<EdicionFormulario, Integer> idEdicion;
    public static volatile SingularAttribute<EdicionFormulario, String> observaciones;

}