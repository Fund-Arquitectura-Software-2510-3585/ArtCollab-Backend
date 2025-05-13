package com.drawnet.artcollab.chatservice.domain.model.commands;

public record EnviarMensajeCommand(Long chatId, Long remitenteId, String texto) {
}
