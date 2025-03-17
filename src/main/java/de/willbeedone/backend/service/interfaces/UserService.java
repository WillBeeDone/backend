package de.willbeedone.backend.service.interfaces;


import de.willbeedone.backend.domain.dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.response_dto.UserResponseDto;
import de.willbeedone.backend.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User addNewUser(UserRequestDto request);

    List<User> getAllUsers();

    Optional<UserResponseDto> getUserByEmail(String email);

    Optional<UserResponseDto> getUserById(Long id);

    User updateUser(Long id, UserRequestDto user);

    void deleteUserById(Long id);

    Optional<UserResponseDto> registration(String email, String password);
}
