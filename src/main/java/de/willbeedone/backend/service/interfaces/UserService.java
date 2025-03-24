package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserFilterResponseDto;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.security.sec_dto.CustomUserDetailService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends CustomUserDetailService {

    User addNewUser(UserRequestDto request);

    List<User> getAllUsers();

    Optional<UserFilterResponseDto> getUserByEmail(String email);

    Optional<UserFilterResponseDto> getUserById(Long id);



    UserFilterResponseDto updateUser(UserRequestDto dto, Long id);

    void deleteUserById(Long id);

//    Optional<UserResponseDto> registration(String email);
}
