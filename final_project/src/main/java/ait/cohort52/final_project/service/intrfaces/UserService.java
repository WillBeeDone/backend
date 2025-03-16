package ait.cohort52.final_project.service.intrfaces;

import ait.cohort52.final_project.domain.dto.requestDto.UserRequestDto;
import ait.cohort52.final_project.domain.dto.responseDto.UserResponseDto;
import ait.cohort52.final_project.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User addNewUser(UserRequestDto request);

    Optional<UserResponseDto> getUserByUsername(String username);

    Optional<UserResponseDto> getUserByEmail(String email);
    Optional<UserResponseDto> getUserById(Long id);

    User updateUser(Long id, UserRequestDto user);

    void deleteUser(Long id);

    List<User> getAllUsers();

    Optional<UserResponseDto> login(String username, String password);
}
