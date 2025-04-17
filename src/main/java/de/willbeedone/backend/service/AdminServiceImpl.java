package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.exceptions.custom_exceptions.UserNotFoundException;
import de.willbeedone.backend.repository.UserRepository;
import de.willbeedone.backend.service.interfaces.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void blockUserByEmail(String email) {
        email = email.trim();
        email = email.replaceAll("\"", "").replaceAll("'", "");
        String finalEmail = email;
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("email " + finalEmail ));
        user.setBlocked(!user.isBlocked());
        userRepository.save(user);
    }
}
