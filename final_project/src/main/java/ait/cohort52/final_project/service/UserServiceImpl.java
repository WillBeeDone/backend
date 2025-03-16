package ait.cohort52.final_project.service;

import ait.cohort52.final_project.domain.dto.requestDto.UserRequestDto;
import ait.cohort52.final_project.domain.dto.responseDto.UserResponseDto;
import ait.cohort52.final_project.domain.entity.User;
import ait.cohort52.final_project.repository.UserRepository;
import ait.cohort52.final_project.service.exception.AlreadyExistException;
import ait.cohort52.final_project.service.exception.UserNotFoundException;
import ait.cohort52.final_project.service.intrfaces.UserService;
import ait.cohort52.final_project.service.mapping.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final MappingService mappingService;
    private final UserRepository repository;


    @Override
    public User addNewUser(UserRequestDto request) {
        if (CheckIfUserExists(request.getUsername())) {
            User newUser = mappingService.getUserFromDto(request);
            return repository.save(newUser);
        } else {
            throw new AlreadyExistException(
                    "User with username " + request.getUsername() + " already exists");
        }
    }

    private boolean CheckIfUserExists(String username) {
        Optional<User> foundedUser = repository.findUserByUsername(username);
        return foundedUser.isEmpty();
    }

    @Override
    public Optional<UserResponseDto> getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty or null");
        }
        return repository.findUserByUsername(username)
                .map(mappingService::getUserDtoFromEntity)
                .or(() -> {
                    throw new UserNotFoundException("User not found with username: " + username);
                });
    }

    @Override
    public Optional<UserResponseDto> getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty or null");
        }
        return repository.findUserByEmail(email)
                .map(mappingService::getUserDtoFromEntity)
                .or(
                        () -> {
                            throw new UserNotFoundException("User not found with email: " + email);
                        });
    }

    @Override
    public Optional<UserResponseDto> getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be empty or null");
        }
        return repository.findById(id)
                .map(mappingService::getUserDtoFromEntity)
                .or(
                        () -> {
                            throw new UserNotFoundException("User not found with id: " + id);
                        }
                );
    }

    @Override
    public User updateUser(Long id, UserRequestDto dto) {

        return repository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(dto.getUsername());
                    existingUser.setEmail(dto.getEmail());
                    existingUser.setPassword(dto.getPassword());
                    return repository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    public void deleteUser(Long id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        } else {
            repository.deleteById(id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }


    //   TODO: добавить password encoder в этот метод, когда разработаем безопасность
    @Override
    public Optional<UserResponseDto> login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty or null");
        }

        //   TODO: Вот сюда вместо user.getPassword...
        return repository.findUserByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .map(mappingService::getUserDtoFromEntity)
                .or(() -> {
                    throw new UserNotFoundException("Invalid username or password");
                });
    }
}
