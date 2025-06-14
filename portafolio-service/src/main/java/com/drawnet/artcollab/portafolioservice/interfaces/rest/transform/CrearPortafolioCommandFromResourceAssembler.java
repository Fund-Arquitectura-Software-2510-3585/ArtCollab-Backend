package com.drawnet.artcollab.portafolioservice.interfaces.rest.transform;

import com.drawnet.artcollab.portafolioservice.domain.model.aggregates.Portafolio;
import com.drawnet.artcollab.portafolioservice.domain.model.commands.CrearPortafolioCommand;
import com.drawnet.artcollab.portafolioservice.interfaces.rest.resources.CrearPortafolioResource;

public class CrearPortafolioCommandFromResourceAssembler {
    public static CrearPortafolioCommand toCommandFromResource(CrearPortafolioResource resource) {
        return new CrearPortafolioCommand(
                resource.ilustradorId(),
                resource.titulo(),
                resource.descripcion(),
                resource.urlImagen()
        );
    }
}
