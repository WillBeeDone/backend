package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.entity.User;

public interface EmailService {

    void sendConfirmationEmail(User user);

}
