package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserEmailRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserForOfferRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserPasswordRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserProfileResponseDto;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.domain.entity.User;
import jakarta.security.auth.message.AuthException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    public List<Offer> getUserOffers(Long userId);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    UserProfileResponseDto getUserProfile(String email);

    User getActiveValidUserById(Long id);

    void updateUser(UserForOfferRequestDto dto, String email);

    void deleteUserById(Long id);

    void addOfferToFavourite(String email, Long offerId);

    void removeOfferFromFavourite(String email, Long offerId);

    Page<OfferFilterResponseDto> getAllFavouriteOffers(String email, Pageable pageable);

    Page<OfferFilterResponseDto> getAllFavouriteFilteredOffers(String email, Pageable pageable, String cityName, String category, String keyPhrase);

    void removeAllOffersFromFavourite(String email);

    Long register(UserRequestDto user);

    Long confirmRegistration(String code);

    void forgotPassword(UserEmailRequestDto dto) throws AuthException;

    void resetPassword(String code, UserPasswordRequestDto dto);

}
