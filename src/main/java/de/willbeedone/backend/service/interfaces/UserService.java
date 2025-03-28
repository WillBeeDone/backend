package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserForOfferRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserFilterResponseDto;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.security.sec_dto.CustomUserDetailService;

import java.util.List;
import java.util.Optional;

public interface UserService extends CustomUserDetailService {

    User addNewUser(UserRequestDto request);

    List<User> getAllUsers();

    Optional<User> getUserByEmail(String email);

    User getActiveValidUserById(Long id);

    void updateUser(UserForOfferRequestDto dto, Long id);

    void deleteUserById(Long id);

    Long register(UserRequestDto user);

    Long confirmRegistration(String code);
}
