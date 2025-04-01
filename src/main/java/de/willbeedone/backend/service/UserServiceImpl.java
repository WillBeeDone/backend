package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserEmailRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserForOfferRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserPasswordRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.entity.ConfirmationCode;
import de.willbeedone.backend.domain.entity.Location;
import de.willbeedone.backend.domain.entity.ResetCode;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.exceptions.custom_exceptions.AlreadyExistException;
import de.willbeedone.backend.exceptions.custom_exceptions.ConfirmationCodeIsInvalidException;
import de.willbeedone.backend.exceptions.custom_exceptions.UserNotFoundException;
import de.willbeedone.backend.exceptions.custom_validation_exceptions.UserValidationException;
import de.willbeedone.backend.repository.ConfirmationCodeRepository;
import de.willbeedone.backend.repository.LocationRepository;
import de.willbeedone.backend.repository.ResetCodeRepository;
import de.willbeedone.backend.repository.UserRepository;
import de.willbeedone.backend.service.interfaces.EmailService;
import de.willbeedone.backend.service.interfaces.LocationService;
import de.willbeedone.backend.service.interfaces.RoleService;
import de.willbeedone.backend.service.interfaces.UserService;
import de.willbeedone.backend.service.mapping.UserMappingService;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
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
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final ResetCodeRepository resetCodeRepository;
    private final LocationService locationService;
    private final RoleService roleService;
    private final EmailService emailService;
    private final UserMappingService mappingService;
    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, LocationRepository locationRepository, ConfirmationCodeRepository codeRepository, ResetCodeRepository resetCodeRepository, LocationService locationService, RoleService roleService, EmailService emailService, UserMappingService mappingService, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.confirmationCodeRepository = codeRepository;
        this.resetCodeRepository = resetCodeRepository;
        this.locationService = locationService;
        this.roleService = roleService;
        this.emailService = emailService;
        this.mappingService = mappingService;
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
        try {
            return userRepository.findById(id)
                    .filter(User::isActive)
                    .filter(user -> !user.isBlocked())
                    .orElseThrow(
                            () -> new UserNotFoundException(id));
        } catch (Exception e) {
            throw new UserValidationException(e);
        }
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

    @Override
    @Transactional
    public Long register(UserRequestDto dto) {
        if(userRepository.findUserByEmail(dto.getEmail()).isPresent()) {
            throw new AlreadyExistException(dto.getEmail());
        }

            User user = mappingService.mapRequestDtoToEntity(dto);
            user.setRoles(Set.of(roleService.getRoleUser()));
            user.setPassword(encoder.encode(dto.getPassword()));

            userRepository.save(user);

            emailService.sendConfirmationEmail(user);

            return user.getId();

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
