package com.drawnet.artcollab.monetizationservice.domain.model.aggregates;

import com.drawnet.artcollab.monetizationservice.domain.model.commands.ActualizarSuscripcionCommand;
import com.drawnet.artcollab.monetizationservice.domain.model.commands.CambiarTipoPlanSuscripcionCommand;
import com.drawnet.artcollab.monetizationservice.domain.model.commands.CrearSuscripcionCommand;
import com.drawnet.artcollab.monetizationservice.domain.model.valueobjects.Plan;
import com.drawnet.artcollab.monetizationservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "suscripciones")
public class Suscripcion extends AuditableAbstractAggregateRoot<Suscripcion> {

    @Column(name = "id_usuario", nullable = false)
    private Long usuarioId;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;


    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_suscripcion", nullable = false)
    private Plan tipo;

    public Suscripcion() {}

    public Suscripcion(CrearSuscripcionCommand command, Plan plan) {
        this.usuarioId = command.usuarioId();
        if (plan == Plan.PREMIUM) {
            this.fechaInicio = obtenerFechaActual();
            this.fechaFin = obtenerFechaFin();
        } else if (plan == Plan.FREEMIUM) {
            this.fechaInicio = null;
            this.fechaFin = null;
        }
        this.tipo = plan;
    }

    private LocalDate obtenerFechaActual() {
        return Date.valueOf(LocalDate.now()).toLocalDate();
    }

    private LocalDate obtenerFechaFin() {
        return Date.valueOf(LocalDate.now().plusMonths(3)).toLocalDate();
    }

    public void actualizarSuscripcion(ActualizarSuscripcionCommand command) {
        this.fechaInicio = this.fechaFin;
        this.fechaFin = this.fechaFin.plusMonths(3);
    }

    public Plan getTipo() {
        return tipo;
    }

    public void cambiarTipoPlan(CambiarTipoPlanSuscripcionCommand command) {
        this.tipo = command.tipo();
        if (this.tipo == Plan.PREMIUM) {
            this.fechaInicio = obtenerFechaActual();
            this.fechaFin = obtenerFechaFin();
        } else if (this.tipo == Plan.FREEMIUM) {
            this.fechaInicio = null;
            this.fechaFin = null;
        }
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setTipo(Plan tipo) {
        this.tipo = tipo;
    }
}
