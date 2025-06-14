package com.drawnet.artcollab.monetizationservice.interfaces.rest;

import com.drawnet.artcollab.monetizationservice.application.internal.queryservices.MonederoQueryServiceImpl;
import com.drawnet.artcollab.monetizationservice.domain.model.aggregates.Monedero;
import com.drawnet.artcollab.monetizationservice.interfaces.rest.resources.MonederoResource;
import com.drawnet.artcollab.monetizationservice.interfaces.rest.transform.MonederoResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/monederos", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Monederos", description = "Operaciones relacionadas con monedero")
public class MonederoController {

    private final MonederoQueryServiceImpl monederoQueryServiceImpl;

    public MonederoController(MonederoQueryServiceImpl monederoQueryServiceImpl) {
        this.monederoQueryServiceImpl = monederoQueryServiceImpl;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<MonederoResource> getMonederoByUsuarioId(@PathVariable Long usuarioId) {
        Optional<Monedero> suscripcion = monederoQueryServiceImpl.getByUsuarioId(usuarioId);
        return suscripcion.map(source -> new ResponseEntity<>(MonederoResourceFromEntityAssembler.toResourceFromEntity(source), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}