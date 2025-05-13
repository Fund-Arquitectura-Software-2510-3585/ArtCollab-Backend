package com.drawnet.artcollab.CollaborativeProjects.interfaces.rest;

import com.drawnet.artcollab.CollaborativeProjects.domain.model.entities.Postulacion;
import com.drawnet.artcollab.CollaborativeProjects.domain.model.queries.GetAllPostulacionesQuery;
import com.drawnet.artcollab.CollaborativeProjects.domain.services.PostulacionCommandService;
import com.drawnet.artcollab.CollaborativeProjects.domain.services.PostulacionQueryService;
import com.drawnet.artcollab.CollaborativeProjects.interfaces.rest.resources.CreatePostulacionResource;
import com.drawnet.artcollab.CollaborativeProjects.interfaces.rest.resources.PostulacionResource;
import com.drawnet.artcollab.CollaborativeProjects.interfaces.rest.transform.CreatePostulacionCommandFromResourceAssembler;
import com.drawnet.artcollab.CollaborativeProjects.interfaces.rest.transform.PostulacionResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/postulaciones", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Postulación", description = "Operaciones relacionadas con postulaciones")
public class PostulacionController {

    private final PostulacionCommandService postulacionCommandService;
    private final PostulacionQueryService postulacionQueryService;

    public PostulacionController(PostulacionCommandService postulacionCommandService, PostulacionQueryService postulacionQueryService) {
        this.postulacionCommandService = postulacionCommandService;
        this.postulacionQueryService = postulacionQueryService;
    }

    @Operation(summary = "Crear una postulación", description = "Crea una postulación con los datos proporcionados en el cuerpo de la solicitud")
    @ApiResponse(responseCode = "201", description = "Postulación creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    @PostMapping
    public ResponseEntity<PostulacionResource> createPostulacion(@RequestBody CreatePostulacionResource resource) {
        Optional<Postulacion> postulacion = postulacionCommandService
                .handle(CreatePostulacionCommandFromResourceAssembler.toCommandFromResource(resource));
        return postulacion.map(source -> new ResponseEntity<>(PostulacionResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Obtener postulaciones", description = "Obtiene todas las postulaciones en la solicitud")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Postulaciones encontradas"),
            @ApiResponse(responseCode = "404", description = "Postulaciones no encontradas")
    })
    @GetMapping
    public ResponseEntity<List<PostulacionResource>> getAllPostulaciones() {
        List<Postulacion> postulaciones = postulacionQueryService
                .handle(new GetAllPostulacionesQuery());
        return ResponseEntity.ok(postulaciones.stream()
                .map(PostulacionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Obtener postulaciones por ilustrador", description = "Obtiene todas las postulaciones por ilustrador en la solicitud")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Postulaciones encontradas"),
            @ApiResponse(responseCode = "404", description = "Postulaciones no encontradas")
    })
    @GetMapping("/ilustradorId/{ilustradorId}")
    public ResponseEntity<List<PostulacionResource>> getPostulacionesByIlustradorId(@PathVariable Long ilustradorId) {
        List<Postulacion> postulaciones = postulacionQueryService.getByIlustradorId(ilustradorId);
        return ResponseEntity.ok(postulaciones.stream()
                .map(PostulacionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Obtener postulaciones por proyecto", description = "Obtiene todas las postulaciones por proyecto en la solicitud")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Postulaciones encontradas"),
            @ApiResponse(responseCode = "404", description = "Postulaciones no encontradas")
    })
    @GetMapping("/proyectoId/{proyectoId}")
    public ResponseEntity<List<PostulacionResource>> getPostulacionesByProyectoId(@PathVariable Long proyectoId) {
        List<Postulacion> postulaciones = postulacionQueryService.getByProyectoId(proyectoId);
        return ResponseEntity.ok(postulaciones.stream()
                .map(PostulacionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList()));
    }

}