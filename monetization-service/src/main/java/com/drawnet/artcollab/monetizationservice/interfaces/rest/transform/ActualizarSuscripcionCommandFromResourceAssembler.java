package com.drawnet.artcollab.monetizationservice.interfaces.rest.transform;

import com.drawnet.artcollab.monetizationservice.domain.model.commands.ActualizarSuscripcionCommand;
import com.drawnet.artcollab.monetizationservice.interfaces.rest.resources.ActualizarSuscripcionResource;

public class ActualizarSuscripcionCommandFromResourceAssembler {
    public static ActualizarSuscripcionCommand toCommandFromResource(Long usuarioId, ActualizarSuscripcionResource resource) {
        return new ActualizarSuscripcionCommand(usuarioId);
    }
}