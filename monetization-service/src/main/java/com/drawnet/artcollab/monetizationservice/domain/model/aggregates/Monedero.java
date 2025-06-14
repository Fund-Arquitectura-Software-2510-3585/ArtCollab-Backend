package com.drawnet.artcollab.monetizationservice.domain.model.aggregates;

import com.drawnet.artcollab.monetizationservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;

@Entity
public class Monedero extends AuditableAbstractAggregateRoot<Monedero> {

    private Long id;
    private Long usuarioId;
    private Double saldo;

    public Monedero(Long id, Long usuarioId, Double saldo) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.saldo = saldo;
    }

    public Monedero() {
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void restarSaldo(Double monto) {
        if (this.saldo < monto) throw new IllegalArgumentException("Saldo insuficiente");
        this.saldo -= monto;
    }

    public void sumarSaldo(Double monto) {
        this.saldo += monto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}