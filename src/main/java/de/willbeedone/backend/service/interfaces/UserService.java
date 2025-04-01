package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserEmailRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserForOfferRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserPasswordRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.entity.User;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    Optional<User> getUserByEmail(String email);

    User getActiveValidUserById(Long id);

    void updateUser(UserForOfferRequestDto dto, Long id);

    void deleteUserById(Long id);

    Long register(UserRequestDto user);

    Long confirmRegistration(String code);

    void forgotPassword(UserEmailRequestDto dto) throws AuthException;

    void resetPassword(String code, UserPasswordRequestDto dto);

}
