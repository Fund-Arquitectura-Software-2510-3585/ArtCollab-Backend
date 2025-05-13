package com.drawnet.artcollab.profiles.interfaces.rest;

import com.drawnet.artcollab.profiles.infrastructure.persistence.jpa.repositories.IlustradorRepository;
import com.drawnet.artcollab.profiles.interfaces.rest.resources.IlustradorResource;
import com.drawnet.artcollab.profiles.interfaces.rest.transform.IlustradorResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/ilustradores", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Ilustradores", description = "Endpoints de Gestión de Ilustradores")
public class IlustradoresController {
    private final IlustradorRepository ilustradorRepository;

    public IlustradoresController(IlustradorRepository ilustradorRepository) {
        this.ilustradorRepository = ilustradorRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<IlustradorResource> getIlustradorByUserId(@PathVariable Long userId) {
        return ilustradorRepository.findByUser_Id(userId)
                .map(ilustrador -> ResponseEntity.ok(IlustradorResourceFromEntityAssembler.toResourceFromEntity(ilustrador)))
                .orElse(ResponseEntity.notFound().build());
    }
}
