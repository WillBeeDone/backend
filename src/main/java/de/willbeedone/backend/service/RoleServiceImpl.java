package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.entity.Role;
import de.willbeedone.backend.repository.RoleRepository;
import de.willbeedone.backend.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role getRoleUser() {
        return repository.findByTitle("ROLE_USER")
                .orElseThrow(
                        () -> new RuntimeException("ROLE_USER doesn't exist.")
                );
    }

}
