package de.willbeedone.backend.domain.entity;

import de.willbeedone.backend.domain.entity.Role;
import de.willbeedone.backend.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        String defaultRole = "USER";

        boolean roleExists = roleRepository.findByTitle(defaultRole).isPresent();

        if (!roleExists) {
            Role role = new Role();
            role.setTitle(defaultRole);
            roleRepository.save(role);
            System.out.println("[INIT] Добавлена роль: " + defaultRole);
        } else {
            System.out.println("[INIT] Роль уже существует: " + defaultRole);
        }
    }
}
