package com.drawnet.artcollab.portafolioservice.interfaces.rest.transform;

import com.drawnet.artcollab.portafolioservice.domain.model.aggregates.Portafolio;
import com.drawnet.artcollab.portafolioservice.domain.model.commands.AgregarIlustracionAPortafolioCommand;
import com.drawnet.artcollab.portafolioservice.interfaces.rest.resources.AgregarIlustracionAPortafolioResource;

public class AgregarIlustracionAPortafolioCommandFromResourceAssembler {
    public static AgregarIlustracionAPortafolioCommand toCommandFromResource(AgregarIlustracionAPortafolioResource resource, Long portafolioId) {
        return new AgregarIlustracionAPortafolioCommand(
                portafolioId,
                resource.ilustracionId(),
                resource.ilustradorId(),
                resource.titulo(),
                resource.descripcion(),
                resource.urlImagen()
        );

    }
}
