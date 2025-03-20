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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserMappingService mappingService;
    private final UserRepository repository;

    public UserServiceImpl(UserMappingService mappingService, UserRepository repository) {
        this.mappingService = mappingService;
        this.repository = repository;
    }

    @Override
    public User addNewUser(UserRequestDto request) {
        try {
            if (checkIfUserExists(request.getEmail())) {
                User newUser = mappingService.mapRequestDtoToEntity(request);
                return repository.save(newUser);
            } else {
                throw new AlreadyExistException(request.getEmail());
            }
        } catch (Exception e) {
            throw new UserValidationException(e);
        }
    }

    private boolean checkIfUserExists(String email) {
        Optional<User> foundUser = repository.findUserByEmail(email);
        return foundUser.isEmpty();
    }

//    @Override
//    public Optional<UserResponseDto> getUserByUsername(String username) {
//        if (username == null || username.trim().isEmpty()) {
//            throw new IllegalArgumentException("Username cannot be empty or null");
//        }
//        return repository.findUserByUsername(username)
//                .map(mappingService::getUserDtoFromEntity)
//                .or(() -> {
//                    throw new UserNotFoundException("User not found with username: " + username);
//                });
//    }

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
    public User updateUser(Long id, UserRequestDto user) {
        try {
            return null;
        } catch (Exception e) {
            throw new UserValidationException(e);
        }
    }

//    @Override
//    public User updateUser(Long id, UserRequestDto dto) {
//
//        return repository.findById(id)
//                .map(existingUser -> {

    /// /                    existingUser.setUsername(dto.getUsername());
//                    existingUser.setEmail(dto.getEmail());
//                    existingUser.setPassword(dto.getPassword());
//
//                    return repository.save(existingUser);
//                })
//                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
//    }
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
    public List<User> getAllUsers() {
        try {
            List<User> users = repository.findAll();
            if (users.isEmpty()) {
                throw new UserNotFoundException();
            }
            return users;
        } catch (Exception e) {
            throw new UserValidationException(e);
        }
    }

//    @Override
//    public Optional<UserResponseDto> registration(String email) {
//        if (email == null || email.trim().isEmpty()) {
//            throw new IllegalArgumentException("Email cannot be empty or null");
//        }
//
//        return repository.findUserByEmail(email)
//                .or(() -> {
//                    throw new UserNotFoundException("Invalid email or password");
//                })
//                .map(mappingService::getUserDtoFromEntity);
//    }

}
