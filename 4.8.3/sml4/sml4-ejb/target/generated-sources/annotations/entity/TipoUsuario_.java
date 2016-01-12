package entity;

import entity.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-12T07:26:08")
@StaticMetamodel(TipoUsuario.class)
public class TipoUsuario_ { 

    public static volatile SingularAttribute<TipoUsuario, String> nombreTipo;
    public static volatile ListAttribute<TipoUsuario, Usuario> usuarioList;
    public static volatile SingularAttribute<TipoUsuario, Integer> idTipoUsuario;

}