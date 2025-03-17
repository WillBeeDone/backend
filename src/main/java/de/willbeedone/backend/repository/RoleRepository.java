package de.willbeedone.backend.repository;


import de.willbeedone.backend.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
