package ait.cohort52.final_project.controller;


import ait.cohort52.final_project.domain.dto.requestDto.UserRequestDto;
import ait.cohort52.final_project.domain.dto.responseDto.UserResponseDto;
import ait.cohort52.final_project.domain.entity.User;
import ait.cohort52.final_project.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl service;

    @PostMapping("/add")
    public User createUser(@Valid @RequestBody UserRequestDto request) {
        return service.addNewUser(request);
    }

    @GetMapping(value = "/all")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping(value = "/getByUsername")
    Optional<UserResponseDto> getUserByUsername(String username) {
        return service.getUserByUsername(username);
    }

    @GetMapping(value = "/getByEmail")
    Optional<UserResponseDto> getUserByEmail(String email) {
        return service.getUserByEmail(email);
    }

    @GetMapping(value = "/getById")
    Optional<UserResponseDto> getUserById(Long id) {
        return service.getUserById(id);
    }

    @DeleteMapping(value = "/delete")
    void deleteUser(Long id) {
        service.deleteUser(id);
    }

    @PutMapping(value = "/update")
    User updateUser(Long id, UserRequestDto user) {
        return service.updateUser(id, user);
    }

    @PostMapping(value = "/login")
    Optional<UserResponseDto> login(String username, String password) {
        return service.login(username, password);
    }

}
