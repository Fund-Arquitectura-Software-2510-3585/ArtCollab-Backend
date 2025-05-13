package com.drawnet.artcollab.CollaborativeProjects.application.internal.commandservices;

import com.drawnet.artcollab.CollaborativeProjects.domain.model.commands.CreatePostulacionCommand;
import com.drawnet.artcollab.CollaborativeProjects.domain.model.entities.Postulacion;
import com.drawnet.artcollab.CollaborativeProjects.domain.services.PostulacionCommandService;
import com.drawnet.artcollab.CollaborativeProjects.infrastructure.persistence.jpa.repositories.PostulacionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostulacionCommandServiceImpl implements PostulacionCommandService {

    private final PostulacionRepository postulacionCommandService;

    public PostulacionCommandServiceImpl(PostulacionRepository postulacionCommandService) {
        this.postulacionCommandService = postulacionCommandService;
    }

    @Override
    public Optional<Postulacion> handle(CreatePostulacionCommand command) {
        //if (postulacionCommandService.existsByProyectoIdAndIlustradorId(command.proyectoId(), command.ilustradorId())) {
        //    throw new IllegalArgumentException("Ya existe una postulación para el proyecto con id '" + command.proyectoId() + "' y el ilustrador con id '" + command.ilustradorId() );
        //}
        //if (!postulacionCommandService.existsByProyectoId(command.proyectoId())) {
        //    throw new IllegalArgumentException("El proyecto con id " + command.proyectoId() + " no existe");
        //}
        //if (!postulacionCommandService.existsByIlustradorId(command.ilustradorId())) {
        //    throw new IllegalArgumentException("El ilustrador con id " + command.ilustradorId() + " no existe");
        //}
        var postulacion = new Postulacion(command);
        var createdPostulacion = postulacionCommandService.save(postulacion);
        return Optional.of(createdPostulacion);
    }
}