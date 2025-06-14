package com.drawnet.artcollab.portafolioservice.domain.services;

import com.drawnet.artcollab.portafolioservice.domain.model.aggregates.Portafolio;
import com.drawnet.artcollab.portafolioservice.domain.model.commands.*;
import com.drawnet.artcollab.portafolioservice.domain.model.entities.Ilustracion;

import java.util.Optional;

public interface PortafolioCommandService {
    Optional<Portafolio> handle(CrearPortafolioCommand command);
    Optional<Ilustracion> handle(AgregarIlustracionAPortafolioCommand command);
    Optional<Ilustracion> handle(PublicarIlustracionCommand command);
    void handle(CalificarIlustracionCommand command);
    void eliminarPortafolio(Long portafolioId);
    Optional<Portafolio> actualizarPortafolio(Long portafolioId, ActualizarPortafolioCommand command);
    void eliminarIlustracion(Long ilustracionId);
    Optional<Ilustracion> actualizarIlustracion(Long ilustracionId, ActualizarIlustracionCommand command);
}
