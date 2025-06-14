package com.drawnet.artcollab.portafolioservice.interfaces.rest.transform;

import com.drawnet.artcollab.portafolioservice.domain.model.aggregates.Portafolio;
import com.drawnet.artcollab.portafolioservice.interfaces.rest.resources.IlustracionResource;
import com.drawnet.artcollab.portafolioservice.interfaces.rest.resources.PortafolioResource;

import java.util.List;
import java.util.stream.Collectors;

public class PortafolioResourceFromEntityAssembler {
    public static PortafolioResource toResource(Portafolio portafolio) {
        List<IlustracionResource> ilustraciones = portafolio.getIlustraciones().stream()
                .map(IlustracionResourceFromEntityAssembler::toResource)
                .collect(Collectors.toList());

        return new PortafolioResource(
                portafolio.getId(),
                portafolio.getTitulo(),
                portafolio.getDescripcion(),
                portafolio.getUrlImagen(),
                ilustraciones
        );
    }
}
