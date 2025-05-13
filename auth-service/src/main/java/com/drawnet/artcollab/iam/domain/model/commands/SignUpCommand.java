package com.drawnet.artcollab.iam.domain.model.commands;

import com.drawnet.artcollab.iam.domain.model.entities.Role;

public record SignUpCommand(String username, String password, Role role, String nombre, String apellido, String biografia, String foto, String redes, Long suscripcion) {
}
