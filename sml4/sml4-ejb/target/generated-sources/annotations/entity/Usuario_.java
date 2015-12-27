package entity;

import entity.Area;
import entity.Cargo;
import entity.EdicionFormulario;
import entity.Formulario;
import entity.TipoUsuario;
import entity.Traslado;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-27T07:23:11")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> passUsuario;
    public static volatile SingularAttribute<Usuario, TipoUsuario> tipoUsuarioidTipoUsuario;
    public static volatile ListAttribute<Usuario, Traslado> trasladoList;
    public static volatile ListAttribute<Usuario, Formulario> formularioList1;
    public static volatile SingularAttribute<Usuario, String> cuentaUsuario;
    public static volatile ListAttribute<Usuario, EdicionFormulario> edicionFormularioList;
    public static volatile SingularAttribute<Usuario, Boolean> estadoUsuario;
    public static volatile SingularAttribute<Usuario, Cargo> cargoidCargo;
    public static volatile SingularAttribute<Usuario, Integer> idUsuario;
    public static volatile ListAttribute<Usuario, Traslado> trasladoList1;
    public static volatile SingularAttribute<Usuario, Area> areaidArea;
    public static volatile SingularAttribute<Usuario, String> apellidoUsuario;
    public static volatile SingularAttribute<Usuario, String> rutUsuario;
    public static volatile ListAttribute<Usuario, Formulario> formularioList;
    public static volatile SingularAttribute<Usuario, String> unidad;
    public static volatile SingularAttribute<Usuario, String> mailUsuario;
    public static volatile SingularAttribute<Usuario, String> nombreUsuario;

}