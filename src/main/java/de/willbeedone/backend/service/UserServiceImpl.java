package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.dto.change_password_dto.ChangePasswordDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserEmailRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserForOfferRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserPasswordRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserProfileResponseDto;
import de.willbeedone.backend.domain.entity.*;
import de.willbeedone.backend.exceptions.custom_exceptions.AlreadyExistException;
import de.willbeedone.backend.exceptions.custom_exceptions.ConfirmationCodeIsInvalidException;
import de.willbeedone.backend.exceptions.custom_exceptions.PasswordException;
import de.willbeedone.backend.exceptions.custom_exceptions.UserNotFoundException;
import de.willbeedone.backend.repository.*;
import de.willbeedone.backend.service.interfaces.*;
import de.willbeedone.backend.service.mapping.OfferMappingService;
import de.willbeedone.backend.service.mapping.UserMappingService;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final OfferRepository offerRepository;
    @Autowired
    private final ConfirmationCodeRepository confirmationCodeRepository;
    @Autowired
    private final ResetCodeRepository resetCodeRepository;
    @Autowired
    private final LocationService locationService;
    @Autowired
    private final RoleService roleService;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private final UserMappingService userMappingService;
    @Autowired
    private final OfferMappingService offerMappingService;
    private final BCryptPasswordEncoder encoder;
    @Autowired
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
    public List<OfferFilterResponseDto> getOffersByUserId(String email) {
        return  getActiveValidUserByEmail(email).getOffers().stream()
                .map(offerMappingService::mapEntityToFilterResponseDto)
                .sorted(Comparator.comparing(OfferFilterResponseDto::getPricePerHour))
                .toList();
    }

    @Override
    public User getActiveValidUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .filter(User::isActive)
                .filter(user -> !user.isBlocked())
                .orElseThrow(
                        () -> new UserNotFoundException("User with email " + email + " not found")
                );
    }

    @Override
    public UserProfileResponseDto getUserProfile(String email) {
        return userMappingService.mapEntityToProfileResponseDto(getActiveValidUserByEmail(email));
    }

    @Override
    @Transactional
    public void updateUser(UserForOfferRequestDto dto, String email) {
        User existingUser = getActiveValidUserByEmail(email);
        Location location = locationService.getLocationByCity(dto.getLocationDto().getCityName());

        existingUser.setFirstName(dto.getFirstName());
        existingUser.setLastName(dto.getLastName());
        existingUser.setPhoneNumber(dto.getPhoneNumber());
        existingUser.setLocation(location);

        MultipartFile profilePicture = dto.getProfilePicture();
        String imageUrl = null;

        if (profilePicture != null && !profilePicture.isEmpty()) {
            imageUrl = imageService.uploadImage(profilePicture);
            existingUser.setProfilePicture(imageUrl);
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
        User user = getActiveValidUserByEmail(email);
        Offer offer = offerRepository.getReferenceById(offerId);
        user.getFavourites().addOffer(offer);
    }

    @Override
    @Transactional
    public void removeOfferFromFavourite(String email, Long offerId) {
        User user = getActiveValidUserByEmail(email);
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
        User user = getActiveValidUserByEmail(email);
        user.getFavourites().clear();
    }

    @Override
    @Transactional
    public Long register(UserRequestDto dto) {
        if (dto.getEmail().equals(dto.getPassword())) {
            throw new PasswordException("Password must differ from E-mail.");
        }

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
        User foundUser = getActiveValidUserByEmail(dto.getEmail());

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

        if (encoder.matches(dto.getPassword(), codeEntity.getUser().getPassword())) {
            throw new PasswordException("New password must differ from the old password.");
        }

        codeEntity.getUser().setPassword(encoder.encode(dto.getPassword()));

        resetCodeRepository.delete(codeEntity);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDto changePasswordDto, String email) {
        User user = getActiveValidUserByEmail(email);

        if (encoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(changePasswordDto.getNewPassword()));
        } else {
            throw new PasswordException("Passwords don't match.");
        }
    }
    public boolean getUserStatus(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.isActive();
    }

    @Override
    @Transactional
    public void toggleActiveStatus(String email) {
    User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    user.setActive(!user.isActive());
    userRepository.save(user);
    }


    //By email (= username)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
