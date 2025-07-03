package com.drawnet.artcollab.portafolioservice.interfaces;

import com.drawnet.artcollab.portafolioservice.application.internal.commandservices.PortafolioCommandServiceImpl;
import com.drawnet.artcollab.portafolioservice.application.internal.queryservices.PortafolioQueryServiceImpl;
import com.drawnet.artcollab.portafolioservice.domain.model.aggregates.Portafolio;
import com.drawnet.artcollab.portafolioservice.domain.model.commands.ActualizarPortafolioCommand;
import com.drawnet.artcollab.portafolioservice.domain.model.queries.ObtenerPortafoliosPorIlustradorQuery;
import com.drawnet.artcollab.portafolioservice.infrastructure.external.clients.IlustradorCliente;
import com.drawnet.artcollab.portafolioservice.interfaces.rest.resources.AgregarIlustracionAPortafolioResource;
import com.drawnet.artcollab.portafolioservice.interfaces.rest.resources.CrearPortafolioResource;
import com.drawnet.artcollab.portafolioservice.interfaces.rest.resources.UserResource;
import com.drawnet.artcollab.portafolioservice.interfaces.rest.transform.AgregarIlustracionAPortafolioCommandFromResourceAssembler;
import com.drawnet.artcollab.portafolioservice.interfaces.rest.transform.CrearPortafolioCommandFromResourceAssembler;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/portafolios")
public class PortafolioController {
    private final PortafolioCommandServiceImpl commandService;
    private final PortafolioQueryServiceImpl queryService;
    private final IlustradorCliente ilustradorCliente;
    private static final Logger logger = LoggerFactory.getLogger(PortafolioController.class);


    public PortafolioController(PortafolioCommandServiceImpl commandService, PortafolioQueryServiceImpl queryService, IlustradorCliente ilustradorCliente) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.ilustradorCliente = ilustradorCliente;
    }

    @PostMapping("/ilustrador/{ilustradorId}")
    public ResponseEntity<?> crearPortafolio(
            @PathVariable Long ilustradorId,
            @RequestBody CrearPortafolioResource resource) {
        try {
            UserResource user = ilustradorCliente.verificarUsuario(ilustradorId);
            if (user == null) {
                return ResponseEntity.status(404).body("Usuario no encontrado.");
            }

            if (!"ILUSTRADOR".equals(user.getRole())) {
                return ResponseEntity.status(403).body("El usuario no tiene el rol de ILUSTRADOR.");
            }

            var command = CrearPortafolioCommandFromResourceAssembler.toCommandFromResource(resource, ilustradorId);
            var result = commandService.handle(command);

            if (result.isPresent()) {
                Long portafolioId = result.get().getId();
                return ResponseEntity.ok().body("Portafolio creado con ID: " + portafolioId);
            }
            return ResponseEntity.badRequest().body("Error al crear el portafolio.");
        } catch (Exception e) {
            logger.error("Error interno al procesar la solicitud: ", e);
            return ResponseEntity.status(500).body("Error interno al procesar la solicitud.");
        }
    }

    @DeleteMapping("/{portafolioId}")
    public ResponseEntity<Void> eliminarPortafolio(@PathVariable Long portafolioId) {
        commandService.eliminarPortafolio(portafolioId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{portafolioId}")
    public ResponseEntity<Portafolio> actualizarPortafolio(
            @PathVariable Long portafolioId,
            @RequestBody ActualizarPortafolioCommand command) {
        Optional<Portafolio> portafolioOpt = commandService.actualizarPortafolio(portafolioId, command);
        return portafolioOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{portafolioId}/ilustraciones")
    public ResponseEntity<?> agregarIlustracion(@PathVariable Long portafolioId, @RequestBody AgregarIlustracionAPortafolioResource resource) {
        var command = AgregarIlustracionAPortafolioCommandFromResourceAssembler.toCommandFromResource(resource, portafolioId);
        var result = commandService.handle(command);
        return result.map(ilustracion -> ResponseEntity.ok().body("Ilustración agregada con ID: " + ilustracion.getId()))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/ilustrador/{ilustradorId}")
    public ResponseEntity<?> obtenerPortafoliosPorIlustrador(@PathVariable Long ilustradorId) {
        var result = queryService.handle(new ObtenerPortafoliosPorIlustradorQuery(ilustradorId));
        return ResponseEntity.ok(result);
    }


}
