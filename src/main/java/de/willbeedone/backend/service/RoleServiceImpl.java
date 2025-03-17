package de.willbeedone.backend.service;


import de.willbeedone.backend.domain.entity.Role;
import de.willbeedone.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl {

    private final RoleRepository repository;


    public Role addRole(Role role) {

        return repository.save(role);
    }

    public void deleteRole(Long id) {
repository.deleteById(id);
    }


    public List<Role> getAllRoles() {

        return repository.findAll().stream()
                .toList();
}}
