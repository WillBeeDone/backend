package ait.cohort52.final_project.controller;


import ait.cohort52.final_project.domain.dto.requestDto.RoleRequestDto;
import ait.cohort52.final_project.domain.dto.responseDto.RoleResponseDto;
import ait.cohort52.final_project.domain.entity.Role;
import ait.cohort52.final_project.service.RoleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleServiceImpl service;

    @PostMapping
    public Role createUser(@Valid @RequestBody RoleRequestDto request) {
        return service.addRole(request);
    }
    @DeleteMapping
    public void deleteRole(@RequestBody Role request) {
        service.deleteRole(request.getId());
    }
    @GetMapping
    public List<RoleResponseDto> getAllRoles(){
        return service.getAllRoles();
}}
