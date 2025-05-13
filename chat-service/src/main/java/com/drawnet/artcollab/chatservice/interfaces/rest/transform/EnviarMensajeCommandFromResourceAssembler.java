package com.drawnet.artcollab.chatservice.interfaces.rest.transform;

import com.drawnet.artcollab.chatservice.domain.model.commands.EnviarMensajeCommand;
import com.drawnet.artcollab.chatservice.interfaces.rest.resources.EnviarMensajeResource;

public class EnviarMensajeCommandFromResourceAssembler {
    public static EnviarMensajeCommand toCommandFromResource(EnviarMensajeResource resource) {
        return new EnviarMensajeCommand(resource.chatId(), resource.remitenteId(), resource.texto());
    }
}
