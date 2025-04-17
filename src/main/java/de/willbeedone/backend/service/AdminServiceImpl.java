package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.exceptions.custom_exceptions.UserNotFoundException;
import de.willbeedone.backend.repository.UserRepository;
import de.willbeedone.backend.service.interfaces.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public boolean blockUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("email " + email));
        user.setBlocked(!user.isBlocked());
        userRepository.save(user);
        return user.isBlocked();
    }


}
