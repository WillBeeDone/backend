package de.willbeedone.backend.repository;

import de.willbeedone.backend.domain.entity.ResetCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetCodeRepository extends JpaRepository<ResetCode, Long> {

    Optional<ResetCode> findByCode(String code);

}
