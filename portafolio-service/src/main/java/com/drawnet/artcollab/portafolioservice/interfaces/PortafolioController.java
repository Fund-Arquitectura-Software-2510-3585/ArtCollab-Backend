package com.drawnet.artcollab.portafolioservice.interfaces;

import com.drawnet.artcollab.portafolioservice.application.internal.commandservices.PortafolioCommandServiceImpl;
import com.drawnet.artcollab.portafolioservice.application.internal.queryservices.PortafolioQueryServiceImpl;
import com.drawnet.artcollab.portafolioservice.domain.model.aggregates.Portafolio;
import com.drawnet.artcollab.portafolioservice.domain.model.commands.ActualizarPortafolioCommand;
import com.drawnet.artcollab.portafolioservice.domain.model.queries.ObtenerPortafoliosPorIlustradorQuery;
import com.drawnet.artcollab.portafolioservice.infrastructure.external.clients.IlustradorCliente;
import com.drawnet.artcollab.portafolioservice.interfaces.rest.resources.AgregarIlustracionAPortafolioResource;
import com.drawnet.artcollab.portafolioservice.interfaces.rest.resources.CrearPortafolioResource;
import com.drawnet.artcollab.portafolioservice.interfaces.rest.transform.AgregarIlustracionAPortafolioCommandFromResourceAssembler;
import com.drawnet.artcollab.portafolioservice.interfaces.rest.transform.CrearPortafolioCommandFromResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/portafolios")
public class PortafolioController {
    private final PortafolioCommandServiceImpl commandService;
    private final PortafolioQueryServiceImpl queryService;
    private final IlustradorCliente ilustradorCliente;

    public PortafolioController(PortafolioCommandServiceImpl commandService, PortafolioQueryServiceImpl queryService, IlustradorCliente ilustradorCliente) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.ilustradorCliente = ilustradorCliente;
    }

    //@PostMapping
    //public ResponseEntity<?> crearPortafolio(@RequestBody CrearPortafolioResource resource) {
    //    var command = CrearPortafolioCommandFromResourceAssembler.toCommandFromResource(resource);
    //    var result = commandService.handle(command);
    //    return result.map(id -> ResponseEntity.ok().body("Portafolio creado con ID: " + id))
    //            .orElse(ResponseEntity.badRequest().build());
    //}

    @PostMapping
    public ResponseEntity<?> crearPortafolio(@RequestBody CrearPortafolioResource resource) {
        try {
            // Verificar que el usuario existe
            ResponseEntity<?> response = ilustradorCliente.verificarUsuario(resource.ilustradorId());
            if (response.getStatusCode().is2xxSuccessful()) {
                // Crear el portafolio
                var command = CrearPortafolioCommandFromResourceAssembler.toCommandFromResource(resource);
                var result = commandService.handle(command);

                if (result.isPresent()) {
                    Long portafolioId = result.get().getId();
                    return ResponseEntity.ok().body("Portafolio creado con ID: " + portafolioId);
                }
                return ResponseEntity.badRequest().body("Error al crear el portafolio.");
            }
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        } catch (Exception e) {
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
