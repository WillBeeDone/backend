package de.willbeedone.backend.service;


import de.willbeedone.backend.domain.dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.response_dto.UserResponseDto;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.exceptions.AlreadyExistException;
import de.willbeedone.backend.exceptions.UserNotFoundException;
import de.willbeedone.backend.repository.UserRepository;
import de.willbeedone.backend.service.interfaces.UserService;

import de.willbeedone.backend.service.mapping.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {

    public UserServiceImpl(MappingService mappingService, UserRepository repository) {
        this.mappingService = mappingService;
        this.repository = repository;
    }

    private final MappingService mappingService;
    private final UserRepository repository;


    @Override
    public User addNewUser(UserRequestDto request) {
        if (checkIfUserExists(request.getEmail())) {
            User newUser = mappingService.getUserFromDto(request);
            return repository.save(newUser);
        } else {
            throw new AlreadyExistException(
                    "User with Email " + request.getEmail() + " already exists");
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
    public Optional<UserResponseDto> getUserByEmail(String email) {

        return repository.findUserByEmail(email)
                .map(mappingService::getUserDtoFromEntity)
                .or(
                        () -> {
                            throw new UserNotFoundException("User not found with email: " + email);
                        });
    }

    @Override
    public Optional<UserResponseDto> getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be empty or null");
        }
        return repository.findById(id)
                .map(mappingService::getUserDtoFromEntity)
                .or(
                        () -> {
                            throw new UserNotFoundException("User not found with id: " + id);
                        }
                );
    }

    @Override
    public User updateUser(Long id, UserRequestDto user) {
        return null;
    }

//    @Override
//    public User updateUser(Long id, UserRequestDto dto) {
//
//        return repository.findById(id)
//                .map(existingUser -> {
////                    existingUser.setUsername(dto.getUsername());
//                    existingUser.setEmail(dto.getEmail());
//                    existingUser.setPassword(dto.getPassword());
//
//                    return repository.save(existingUser);
//                })
//                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
//    }

    @Override
    public void deleteUserById(Long id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        } else {
            repository.deleteById(id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }


    @Override
    public Optional<UserResponseDto> registration(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Email and password cannot be empty or null");
        }

        return repository.findUserByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .map(mappingService::getUserDtoFromEntity)
                .or(() -> {
                    throw new UserNotFoundException("Invalid email or password");
                });
    }


}
