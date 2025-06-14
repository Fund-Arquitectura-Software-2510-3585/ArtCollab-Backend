package com.drawnet.artcollab.portafolioservice.interfaces.rest.resources;

public record CrearPortafolioResource(
        Long ilustradorId,
        String titulo,
        String descripcion,
        String urlImagen
) {
}
