
package com.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import java.util.Date;
import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "contratos")
public class Contrato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "fecha")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    @Column(name = "lugar")
    private String lugar;

    @ManyToOne
    @JoinColumn(name = "rango_horario_id")
    private RangoHorario rangoHorario;

    @ManyToMany
    @JoinTable(
            name = "contrato_tareas",
            joinColumns = @JoinColumn(name = "contrato_id"),
            inverseJoinColumns = @JoinColumn(name = "tarea_id")
    )
    private List<Tarea> tareas;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoContrato estado;

    public Contrato() {
    }

    public Contrato(Long id, Profesional profesional, Cliente cliente, Date fecha, String lugar, RangoHorario rangoHorario, List<Tarea> tareas, EstadoContrato estado) {
        this.id = id;
        this.profesional = profesional;
        this.cliente = cliente;
        this.fecha = fecha;
        this.lugar = lugar;
        this.rangoHorario = rangoHorario;
        this.tareas = tareas;
        this.estado = estado;
    }

    public RangoHorario getRangoHorario() {
        return rangoHorario;
    }

    public void setRangoHorario(RangoHorario rangoHorario) {
        this.rangoHorario = rangoHorario;
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    
    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }
}