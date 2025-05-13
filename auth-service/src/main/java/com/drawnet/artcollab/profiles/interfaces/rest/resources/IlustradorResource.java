package com.drawnet.artcollab.profiles.interfaces.rest.resources;

import com.drawnet.artcollab.iam.domain.model.aggregates.User;

public record IlustradorResource(Long id, User user_id) {
}
