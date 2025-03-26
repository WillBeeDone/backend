package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.entity.Role;

import java.util.List;

public interface RoleService {

    Role getRoleUser();

    void deleteRole(Long id);

    Role addRole(Role role);

    List<Role> getAllRoles();

}
