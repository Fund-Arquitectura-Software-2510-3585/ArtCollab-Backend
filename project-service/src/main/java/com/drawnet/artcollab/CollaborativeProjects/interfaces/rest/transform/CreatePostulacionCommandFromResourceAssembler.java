package com.drawnet.artcollab.CollaborativeProjects.interfaces.rest.transform;

import com.drawnet.artcollab.CollaborativeProjects.domain.model.commands.CreatePostulacionCommand;
import com.drawnet.artcollab.CollaborativeProjects.interfaces.rest.resources.CreatePostulacionResource;

public class CreatePostulacionCommandFromResourceAssembler {
    public static CreatePostulacionCommand toCommandFromResource(CreatePostulacionResource resource) {
        return new CreatePostulacionCommand(
                resource.proyectoId(),
                resource.ilustradorId(),
                resource.estado(),
                resource.fecha()
        );
    }
}
