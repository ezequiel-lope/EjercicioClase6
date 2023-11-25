package com.models;

import com.models.Cliente;
import com.models.EstadoContrato;
import com.models.Profesional;
import com.models.RangoHorario;
import com.models.Tarea;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-11-25T17:15:38", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Contrato.class)
public class Contrato_ { 

    public static volatile SingularAttribute<Contrato, RangoHorario> rangoHorario;
    public static volatile SingularAttribute<Contrato, Cliente> cliente;
    public static volatile SingularAttribute<Contrato, Date> fecha;
    public static volatile SingularAttribute<Contrato, EstadoContrato> estado;
    public static volatile SingularAttribute<Contrato, String> lugar;
    public static volatile SingularAttribute<Contrato, Profesional> profesional;
    public static volatile SingularAttribute<Contrato, Long> id;
    public static volatile ListAttribute<Contrato, Tarea> tareas;

}