package com.models;

import com.models.Administrador;
import com.models.Tarea;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-11-25T17:15:38", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Servicio.class)
public class Servicio_ { 

    public static volatile SingularAttribute<Servicio, Administrador> administrador;
    public static volatile SingularAttribute<Servicio, Long> id;
    public static volatile ListAttribute<Servicio, Tarea> tareas;

}