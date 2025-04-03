package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserEmailRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserForOfferRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserPasswordRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.entity.*;
import de.willbeedone.backend.exceptions.custom_exceptions.AlreadyExistException;
import de.willbeedone.backend.exceptions.custom_exceptions.ConfirmationCodeIsInvalidException;
import de.willbeedone.backend.exceptions.custom_exceptions.UserNotFoundException;
import de.willbeedone.backend.exceptions.custom_validation_exceptions.UserValidationException;
import de.willbeedone.backend.repository.*;
import de.willbeedone.backend.service.interfaces.EmailService;
import de.willbeedone.backend.service.interfaces.LocationService;
import de.willbeedone.backend.service.interfaces.RoleService;
import de.willbeedone.backend.service.interfaces.UserService;
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

import java.time.LocalDateTime;
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

    public UserServiceImpl(UserRepository userRepository, LocationRepository locationRepository, OfferRepository offerRepository, ConfirmationCodeRepository codeRepository, ResetCodeRepository resetCodeRepository, FavouriteRepository favouriteRepository, LocationService locationService, RoleService roleService, EmailService emailService, UserMappingService mappingService, UserMappingService userMappingService, OfferMappingService offerMappingService, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.confirmationCodeRepository = codeRepository;
        this.resetCodeRepository = resetCodeRepository;
        this.userMappingService = userMappingService;
        this.offerMappingService = offerMappingService;
        this.locationService = locationService;
        this.roleService = roleService;
        this.emailService = emailService;
        this.encoder = encoder;
    }

    private boolean checkIfUserExists(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        try {
            return userRepository.findUserByEmail(email);
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public User getActiveValidUserById(Long id) {
        return userRepository.findById(id)
                .filter(User::isActive)
                .filter(user -> !user.isBlocked())
                .orElseThrow(
                        () -> new UserNotFoundException(id)
                );
    }

    @Override
    @Transactional
    public void updateUser(UserForOfferRequestDto dto, Long id) {
        User existingUser = getActiveValidUserById(id);
        Location location = locationService.getLocationByCity(dto.getLocationDto().getCityName());

        existingUser.setFirstName(dto.getFirstName());
        existingUser.setLastName(dto.getLastName());
        existingUser.setPhoneNumber(dto.getPhoneNumber());
        existingUser.setLocation(location);
        existingUser.setProfilePicture(dto.getProfilePicture());
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new UserNotFoundException(id);
            } else {
                userRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new UserValidationException(e);
        }
    }

    //Почему getReferenceById?
    //Скорее всего, код использует getReferenceById вместо getById, потому что:
    //Эффективность: Он не сразу загружает объект из БД, а только когда он действительно понадобится.
    //Оптимизация работы с Hibernate: Если тебе нужно просто сохранить связь (например, установить offer в Favourite),
    // а поля Offer не используются, реальный запрос в БД может даже не выполняться.
    //Современные версии Spring Data JPA (после 2.5) рекомендуют getReferenceById, и в некоторых местах он автоматически подставляется.
    @Override
    @Transactional
    public void addOfferToFavourite(String email, Long offerId) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(
                        () -> new UserNotFoundException("User with email "  + email + " not found")
                );
        Offer offer = offerRepository.getReferenceById(offerId);
        user.getFavourites().addOffer(offer);
    }

    @Override
    @Transactional
    public void removeOfferFromFavourite(String email, Long offerId) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(
                        () -> new UserNotFoundException("User with email "  + email + " not found")
                );
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

        List<OfferFilterResponseDto> offers = pageOffers
                .stream()
                .filter(offer -> offer == null || "all".equals(cityName) || offer.getUser().getLocation().getCityName().equals(cityName))
                .filter(offer -> offer == null || "all".equals(category) || offer.getCategory().getName().equals(category))
                .filter(offer -> offer == null || "all".equals(keyPhrase) || offer.getUser().getFirstName().contains(keyPhrase) || offer.getUser().getLastName().contains(keyPhrase) || offer.getTitle().contains(keyPhrase) || offer.getDescription().contains(keyPhrase))
                .map(offerMappingService::mapEntityToFilterResponseDto)
                .toList();

        return new PageImpl<>(offers, pageable, pageOffers.getTotalElements());
    }

    @Override
    @Transactional
    public void removeAllOffersFromFavourite(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(
                        () -> new UserNotFoundException("User with email "  + email + " not found")
                );
        user.getFavourites().clear();
    }

    @Override
    @Transactional
    public Long register(UserRequestDto dto) {
        if (userRepository.findUserByEmail(dto.getEmail()).isPresent()) {
            throw new AlreadyExistException(dto.getEmail());
        }

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
                .orElseThrow(
                        () -> new ConfirmationCodeIsInvalidException("Confirmation code not found.")
                );

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
        User foundUser = userRepository.findUserByEmail(dto.getEmail())
                .orElseThrow(
                        () -> new UserNotFoundException("User with email '" + dto.getEmail() + "' not found")
                );

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
                .orElseThrow(
                        () -> new RuntimeException("Reset code not found")
                );

        codeEntity.getUser().setPassword(encoder.encode(dto.getPassword()));

        resetCodeRepository.delete(codeEntity);
    }

    //By email (= username)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
