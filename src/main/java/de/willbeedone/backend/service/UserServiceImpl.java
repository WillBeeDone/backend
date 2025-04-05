package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserEmailRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserForOfferRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserPasswordRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserProfileResponseDto;
import de.willbeedone.backend.domain.entity.*;
import de.willbeedone.backend.exceptions.custom_exceptions.AlreadyExistException;
import de.willbeedone.backend.exceptions.custom_exceptions.ConfirmationCodeIsInvalidException;
import de.willbeedone.backend.exceptions.custom_exceptions.UserNotFoundException;
import de.willbeedone.backend.exceptions.custom_validation_exceptions.UserValidationException;
import de.willbeedone.backend.repository.*;
import de.willbeedone.backend.service.interfaces.*;
import de.willbeedone.backend.service.mapping.OfferMappingService;
import de.willbeedone.backend.service.mapping.UserMappingService;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final ResetCodeRepository resetCodeRepository;
    private final LocationService locationService;
    private final RoleService roleService;
    private final EmailService emailService;
    private final UserMappingService userMappingService;
    private final OfferMappingService offerMappingService;
    private final BCryptPasswordEncoder encoder;
    private final ImageService imageService;

    public UserServiceImpl(UserRepository userRepository, ConfirmationCodeRepository confirmationCodeRepository, ResetCodeRepository resetCodeRepository, LocationService locationService, RoleService roleService, EmailService emailService, UserMappingService userMappingService, OfferMappingService offerMappingService, BCryptPasswordEncoder encoder, ImageService imageService, OfferRepository offerRepository) {
        this.userRepository = userRepository;
        this.confirmationCodeRepository = confirmationCodeRepository;
        this.resetCodeRepository = resetCodeRepository;
        this.locationService = locationService;
        this.roleService = roleService;
        this.emailService = emailService;
        this.userMappingService = userMappingService;
        this.offerMappingService = offerMappingService;
        this.encoder = encoder;
        this.imageService = imageService;
        this.offerRepository = offerRepository;
    }

    private boolean checkIfUserExists(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Override
    public List<Offer> getUserOffers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return new ArrayList<>(user.getOffers());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .filter(User::isActive)
                .filter(user -> !user.isBlocked())
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    @Override
    public UserProfileResponseDto getUserProfile(String email) {
        return userMappingService.mapEntityToProfileResponseDto(getUserByEmail(email));
    }

    @Override
    public User getActiveValidUserById(Long id) {
        return userRepository.findById(id)
                .filter(User::isActive)
                .filter(user -> !user.isBlocked())
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional
    public void updateUser(UserForOfferRequestDto dto, String email) {
        User existingUser = getUserByEmail(email);
        Location location = locationService.getLocationByCity(dto.getLocationDto().getCityName());

        existingUser.setFirstName(dto.getFirstName());
        existingUser.setLastName(dto.getLastName());
        existingUser.setPhoneNumber(dto.getPhoneNumber());
        existingUser.setLocation(location);

        MultipartFile profilePicture = dto.getProfilePicture();
        String imageUrl = null;
        Long userId = existingUser.getId();

        if (profilePicture != null && !profilePicture.isEmpty()) {
            imageUrl = imageService.uploadImage(profilePicture, userId);
            existingUser.setProfilePicture(imageUrl);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new UserNotFoundException(id);
            }
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new UserValidationException(e);
        }
    }

    @Override
    @Transactional
    public void addOfferToFavourite(String email, Long offerId) {
        User user = getUserByEmail(email);
        Offer offer = offerRepository.getReferenceById(offerId);
        user.getFavourites().addOffer(offer);
    }

    @Override
    @Transactional
    public void removeOfferFromFavourite(String email, Long offerId) {
        User user = getUserByEmail(email);
        user.getFavourites().removeOfferById(offerId);
    }

    @Override
    public Page<OfferFilterResponseDto> getAllFavouriteOffers(String email, Pageable pageable) {
        return offerRepository.findActiveFavouriteOffersByUserEmail(email, pageable)
                .map(offerMappingService::mapEntityToFilterResponseDto);
    }

    @Override
    public Page<OfferFilterResponseDto> getAllFavouriteFilteredOffers(String email, Pageable pageable, String cityName, String category, String keyPhrase) {
        Page<Offer> pageOffers = offerRepository.findActiveFavouriteOffersByUserEmail(email, pageable);

        List<OfferFilterResponseDto> offers = pageOffers.stream()
                .filter(offer -> offer == null || "all".equals(cityName) || offer.getUser().getLocation().getCityName().equals(cityName))
                .filter(offer -> offer == null || "all".equals(category) || offer.getCategory().equals(category))
                .filter(offer -> offer == null || "all".equals(keyPhrase) || offer.getUser().getFirstName().contains(keyPhrase) || offer.getUser().getLastName().contains(keyPhrase) || offer.getTitle().contains(keyPhrase) || offer.getDescription().contains(keyPhrase))
                .map(offerMappingService::mapEntityToFilterResponseDto)
                .toList();

        return new PageImpl<>(offers, pageable, pageOffers.getTotalElements());
    }

    @Override
    @Transactional
    public void removeAllOffersFromFavourite(String email) {
        User user = getUserByEmail(email);
        user.getFavourites().clear();
    }

    @Override
    @Transactional
    public Long register(UserRequestDto dto) {
        userRepository.findUserByEmail(dto.getEmail())
                .ifPresent(user -> { throw new AlreadyExistException(dto.getEmail()); });

        User userEntity = userMappingService.mapRequestDtoToEntity(dto);
        userEntity.setRoles(Set.of(roleService.getRoleUser()));
        userEntity.setPassword(encoder.encode(dto.getPassword()));

        Favourite favourites = new Favourite();
        favourites.setUser(userEntity);
        userEntity.setFavourites(favourites);

        userRepository.save(userEntity);
        emailService.sendConfirmationEmail(userEntity);

        return userEntity.getId();
    }

    @Override
    @Transactional
    public Long confirmRegistration(String code) {
        ConfirmationCode codeEntity = confirmationCodeRepository.findByCode(code)
                .orElseThrow(() -> new ConfirmationCodeIsInvalidException("Confirmation code not found."));

        if (codeEntity.getExpired().isBefore(LocalDateTime.now())) {
            throw new ConfirmationCodeIsInvalidException("Confirmation code is expired.");
        }

        codeEntity.getUser().setActive(true);
        Long userId = codeEntity.getUser().getId();
        confirmationCodeRepository.delete(codeEntity);

        return userId;
    }

    @Override
    @Transactional
    public void forgotPassword(UserEmailRequestDto dto) throws AuthException {
        User foundUser = getUserByEmail(dto.getEmail());

        if (!foundUser.isEnabled()) {
            throw new AuthException("User is not activated");
        }

        if (foundUser.isAccountNonLocked()) {
            throw new AuthException("User is blocked");
        }

        emailService.sendResetPasswordEmail(foundUser);
    }

    @Override
    @Transactional
    public void resetPassword(String code, UserPasswordRequestDto dto) {
        ResetCode codeEntity = resetCodeRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Reset code not found"));

        codeEntity.getUser().setPassword(encoder.encode(dto.getPassword()));
        resetCodeRepository.delete(codeEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}