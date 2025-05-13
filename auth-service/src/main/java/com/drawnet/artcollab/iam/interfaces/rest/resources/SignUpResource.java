package com.drawnet.artcollab.iam.interfaces.rest.resources;


public record SignUpResource(String username, String password, String role, String nombre, String apellido, String biografia, String foto, String redes, Long suscripcion) {
}

//lo q escribo