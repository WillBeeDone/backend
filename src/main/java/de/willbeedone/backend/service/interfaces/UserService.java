package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.dto.change_password_dto.ChangePasswordDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserEmailRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserForOfferRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserPasswordRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserProfileResponseDto;
import de.willbeedone.backend.domain.entity.User;
import jakarta.security.auth.message.AuthException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<OfferFilterResponseDto> getOffersByUserId(String email);

    UserProfileResponseDto getUserProfile(String email);

    User getActiveValidUserByEmail(String email);

    void updateUser(UserForOfferRequestDto dto, String email);

    void addOfferToFavourite(String email, Long offerId);

    void removeOfferFromFavourite(String email, Long offerId);

    Page<OfferFilterResponseDto> getAllFavouriteOffers(String email, Pageable pageable);

    Page<OfferFilterResponseDto> getAllFavouriteFilteredOffers(String email, Pageable pageable, String cityName, String category, String keyPhrase);

    void removeAllOffersFromFavourite(String email);

    Long register(UserRequestDto user);

    Long confirmRegistration(String code);

    void forgotPassword(UserEmailRequestDto dto) throws AuthException;

    void resetPassword(String code, UserPasswordRequestDto dto);

    void changePassword(ChangePasswordDto changePasswordDto, String email);

    boolean getUserStatus(String email);

    void toggleActiveStatus(String email);

    boolean blockUserByEmail(String email);
}
