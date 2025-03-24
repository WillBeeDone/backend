package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserFilterResponseDto;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.exceptions.custom_exceptions.AlreadyExistException;
import de.willbeedone.backend.exceptions.custom_exceptions.UserNotFoundException;
import de.willbeedone.backend.exceptions.custom_validation_exceptions.UserValidationException;
import de.willbeedone.backend.repository.UserRepository;
import de.willbeedone.backend.service.interfaces.UserService;
import de.willbeedone.backend.service.mapping.UserMappingService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMappingService mappingService;
    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository, UserMappingService mappingService, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.encoder = encoder;
    }

    @Override
    public User addNewUser(UserRequestDto request) {
        try {
            if (checkIfUserExists(request.getEmail())) {
                throw new AlreadyExistException(request.getEmail());
            }
            User user = mappingService.mapRequestDtoToEntity(request);
            user.setPassword(encoder.encode(request.getPassword()));
            User savedUser = repository.save(user);
            return savedUser;
        } catch (Exception e) {
            throw new UserValidationException(e.getMessage());
        }
    }

    private boolean checkIfUserExists(String email) {
        return repository.findUserByEmail(email).isPresent();
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<UserFilterResponseDto> getUserByEmail(String email) {
        try {
            return repository.findUserByEmail(email)
                    .map(mappingService::mapEntityToFilterResponseDto);
        } catch (Exception e) {
            throw new UserValidationException(e);
        }
    }

    @Override
    public Optional<UserFilterResponseDto> getUserById(Long id) {
        try {
            return Optional.ofNullable(repository.findById(id)
                    .map(mappingService::mapEntityToFilterResponseDto)
                    .orElseThrow(
                            () -> new UserNotFoundException(id)));
        } catch (Exception e) {
            throw new UserValidationException(e);
        }
    }

    @Override
    public UserFilterResponseDto updateUser(UserRequestDto dto, Long id) {
        try {
            return repository.findById(id)
                    .map(existingUser -> {
                        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
                            existingUser.setEmail(dto.getEmail());
                        }
                        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                            existingUser.setPassword(encoder.encode(dto.getPassword())); // Шифруем пароль
                        }
                        User updatedUser = repository.save(existingUser);
                        return mappingService.mapEntityToFilterResponseDto(updatedUser); // Маппинг в DTO
                    }).orElseThrow(() -> new UserNotFoundException(id));
        } catch (Exception e) {
            throw new UserValidationException(e);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            if (!repository.existsById(id)) {
                throw new UserNotFoundException(id);
            } else {
                repository.deleteById(id);
            }
        } catch (Exception e) {
            throw new UserValidationException(e);
        }
    }

    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        return repository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
