package com.drawnet.artcollab.portafolioservice.interfaces.rest.resources;

public record AgregarIlustracionAPortafolioResource(
        Long ilustracionId,
        Long ilustradorId,
        String titulo,
        String descripcion,
        String urlImagen
) {
}
