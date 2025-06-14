package com.drawnet.artcollab.portafolioservice.domain.model.commands;

public record CrearPortafolioCommand(
        Long ilustradorId,
        String titulo,
        String descripcion,
        String urlImagen
) {}
