package com.drawnet.artcollab.profiles.interfaces.rest;


import com.drawnet.artcollab.profiles.infrastructure.persistence.jpa.repositories.EscritorRepository;
import com.drawnet.artcollab.profiles.interfaces.rest.resources.EscritorResource;
import com.drawnet.artcollab.profiles.interfaces.rest.transform.EscritorResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/escritores", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Escritores", description = "Endpoints de Gestión de Escritores")
public class EscritorController {

    private final EscritorRepository escritorRepository;

    public EscritorController(EscritorRepository escritorRepository) {
        this.escritorRepository = escritorRepository;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<EscritorResource> getDoctorByUserId(@PathVariable Long userId) {
        return escritorRepository.findByUser_Id(userId)
                .map(escritor -> ResponseEntity.ok(EscritorResourceFromEntityAssembler.toResourceFromEntity(escritor)))
                .orElse(ResponseEntity.notFound().build());
    }
}
