package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserForOfferRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserFilterResponseDto;
import de.willbeedone.backend.domain.entity.ConfirmationCode;
import de.willbeedone.backend.domain.entity.Location;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.exceptions.custom_exceptions.AlreadyExistException;
import de.willbeedone.backend.exceptions.custom_exceptions.ConfirmationCodeIsInvalidException;
import de.willbeedone.backend.exceptions.custom_exceptions.UserNotFoundException;
import de.willbeedone.backend.exceptions.custom_validation_exceptions.UserValidationException;
import de.willbeedone.backend.repository.ConfirmationCodeRepository;
import de.willbeedone.backend.repository.LocationRepository;
import de.willbeedone.backend.repository.UserRepository;
import de.willbeedone.backend.service.interfaces.EmailService;
import de.willbeedone.backend.service.interfaces.LocationService;
import de.willbeedone.backend.service.interfaces.RoleService;
import de.willbeedone.backend.service.interfaces.UserService;
import de.willbeedone.backend.service.mapping.UserMappingService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConfirmationCodeRepository codeRepository;
    private final LocationService locationService;
    private final RoleService roleService;
    private final EmailService emailService;
    private final UserMappingService mappingService;
    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, LocationRepository locationRepository, ConfirmationCodeRepository codeRepository, LocationService locationService, RoleService roleService, EmailService emailService, UserMappingService mappingService, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
        this.locationService = locationService;
        this.roleService = roleService;
        this.emailService = emailService;
        this.mappingService = mappingService;
        this.encoder = encoder;
    }

    @Override
    public User addNewUser(UserRequestDto request) {
        try {
            if (checkIfUserExists(request.getEmail())) {
                throw new AlreadyExistException(request.getEmail());
            }
            User user = mappingService.mapRequestDtoToEntity(request);
            user.setPassword(encoder.encode(request.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserValidationException(e.getMessage());
        }
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

        ConfirmationCode codeEntity = codeRepository.findByCode(code)
                .orElseThrow(
                        () -> new ConfirmationCodeIsInvalidException("Confirmation code not found.")
                );

        if (codeEntity.getExpired().isBefore(LocalDateTime.now())) {
            throw new ConfirmationCodeIsInvalidException("Confirmation code is expired.");
        }

        codeEntity.getUser().setActive(true);

        return codeEntity.getUser().getId();
    }

    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
