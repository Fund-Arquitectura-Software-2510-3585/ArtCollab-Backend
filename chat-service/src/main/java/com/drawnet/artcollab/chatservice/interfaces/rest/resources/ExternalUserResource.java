package com.drawnet.artcollab.chatservice.interfaces.rest.resources;

public class ExternalUserResource {
    private Long id;
    private String username;

    public ExternalUserResource() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}