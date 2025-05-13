package com.drawnet.artcollab.CollaborativeProjects.interfaces.rest;


import com.drawnet.artcollab.CollaborativeProjects.domain.model.aggregates.Proyecto;
import com.drawnet.artcollab.CollaborativeProjects.domain.model.queries.GetAllProyectosQuery;
import com.drawnet.artcollab.CollaborativeProjects.domain.services.ProyectoCommandService;
import com.drawnet.artcollab.CollaborativeProjects.domain.services.ProyectoQueryService;
import com.drawnet.artcollab.CollaborativeProjects.interfaces.rest.resources.CreateProyectoResource;
import com.drawnet.artcollab.CollaborativeProjects.interfaces.rest.resources.ProyectoResource;
import com.drawnet.artcollab.CollaborativeProjects.interfaces.rest.transform.CreateProyectoCommandFromResourceAssembler;
import com.drawnet.artcollab.CollaborativeProjects.interfaces.rest.transform.ProyectoResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/proyectos", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Proyecto", description = "Operaciones relacionadas con proyectos")
public class ProyectoController {

    private final ProyectoCommandService proyectoCommandService;
    private final ProyectoQueryService proyectoQueryService;

    public ProyectoController(ProyectoCommandService proyectoCommandService, ProyectoQueryService proyectoQueryService) {
        this.proyectoCommandService = proyectoCommandService;
        this.proyectoQueryService = proyectoQueryService;
    }

    @Operation(summary = "Crear un proyecto", description = "Crea un proyecto con los datos proporcionados en el cuerpo de la solicitud")
    @ApiResponse(responseCode = "201", description = "Proyecto creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    @PostMapping
    public ResponseEntity<ProyectoResource> createProyecto(@RequestBody CreateProyectoResource resource) {
        Optional<Proyecto> proyecto = proyectoCommandService
                .handle(CreateProyectoCommandFromResourceAssembler.toCommandFromResource(resource));
        return proyecto.map(source -> new ResponseEntity<>(ProyectoResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Obtener proyectos", description = "Obtiene todas los proyectos en la solicitud")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proyectos encontradas"),
            @ApiResponse(responseCode = "404", description = "Proyectos no encontradas")
    })
    @GetMapping
    public ResponseEntity<List<ProyectoResource>> getAllProyectos() {
        List<Proyecto> proyectos = proyectoQueryService
                .handle(new GetAllProyectosQuery());
        return ResponseEntity.ok(proyectos.stream()
                .map(ProyectoResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Obtener proyectos por escritor", description = "Obtiene todas los proyectos por escritor en la solicitud")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proyectos encontradas"),
            @ApiResponse(responseCode = "404", description = "Proyectos no encontradas")
    })
    @GetMapping("/escritorId/{escritorId}")
    public ResponseEntity<List<ProyectoResource>> getProyectosByEscritorId(@PathVariable Long escritorId) {
        List<Proyecto> proyectos = proyectoQueryService.getByEscritorId (escritorId);
        return ResponseEntity.ok(proyectos.stream()
                .map(ProyectoResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList()));
    }
}