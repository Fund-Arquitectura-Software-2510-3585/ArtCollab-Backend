package com.drawnet.artcolab.libroservice.interfaces.rest;

import com.drawnet.artcolab.libroservice.application.internal.commandservices.LibroCommandServiceImpl;
import com.drawnet.artcolab.libroservice.application.internal.queryservices.LibroQueryServiceImpl;
import com.drawnet.artcolab.libroservice.domain.model.aggregates.Libro;
import com.drawnet.artcolab.libroservice.domain.model.queries.ObtenerCalificacionesDeLibroQuery;
import com.drawnet.artcolab.libroservice.domain.model.queries.ObtenerLibroPorEscritorQuery;
import com.drawnet.artcolab.libroservice.domain.model.valueobjects.Calificacion;
import com.drawnet.artcolab.libroservice.interfaces.rest.resources.CalificarLibroResource;
import com.drawnet.artcolab.libroservice.interfaces.rest.resources.CrearLibroResource;
import com.drawnet.artcolab.libroservice.interfaces.rest.resources.LibroResource;
import com.drawnet.artcolab.libroservice.interfaces.rest.transform.CalificarLibroCommandFromResourceAssembler;
import com.drawnet.artcolab.libroservice.interfaces.rest.transform.CrearLibroCommandFromResourceAssembler;
import com.drawnet.artcolab.libroservice.interfaces.rest.transform.LibroResourceFromEntityAssembler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/libros")
public class LibroController {

    private final LibroCommandServiceImpl libroCommandService;
    private final LibroQueryServiceImpl libroQueryService;

    public LibroController(LibroCommandServiceImpl libroCommandService, LibroQueryServiceImpl libroQueryService) {
        this.libroCommandService = libroCommandService;
        this.libroQueryService = libroQueryService;
    }

    @PostMapping("/{escritorId}")
    public LibroResource crearLibro(@PathVariable Long escritorId, @RequestBody CrearLibroResource resource) {
        Optional<Libro> libro = libroCommandService.handle(
                CrearLibroCommandFromResourceAssembler.toCommandFromResource(resource, escritorId)
        );
        return libro.map(LibroResourceFromEntityAssembler::toResourceFromEntity).orElse(null);
    }

    @GetMapping("/escritor/{escritorId}")
    public List<LibroResource> obtenerLibrosPorEscritor(@PathVariable Long escritorId) {
        List<Libro> libros = libroQueryService.handle(new ObtenerLibroPorEscritorQuery(escritorId));
        return libros.stream().map(LibroResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
    }



}
