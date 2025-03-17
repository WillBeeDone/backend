package de.willbeedone.backend.repository;

import ait.cohort52.final_project.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//TODO: IgnoreCase
    Optional<User>findUserByEmail(String email);
}
