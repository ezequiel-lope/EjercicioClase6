package com.models;

import com.models.Profesional;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-11-25T17:15:38", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Contacto.class)
public class Contacto_ { 

    public static volatile SingularAttribute<Contacto, String> tipoDocumento;
    public static volatile SingularAttribute<Contacto, String> foto;
    public static volatile SingularAttribute<Contacto, String> fechaNacimiento;
    public static volatile SingularAttribute<Contacto, String> apellido;
    public static volatile SingularAttribute<Contacto, Profesional> profesional;
    public static volatile SingularAttribute<Contacto, Long> id;
    public static volatile SingularAttribute<Contacto, String> numeroDocumento;
    public static volatile SingularAttribute<Contacto, String> nombre;
    public static volatile SingularAttribute<Contacto, String> cuilCuit;

}