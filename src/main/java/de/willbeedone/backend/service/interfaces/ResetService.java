package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.entity.User;

public interface ResetService {

    String generateResetPasswordCode(User user);

}
