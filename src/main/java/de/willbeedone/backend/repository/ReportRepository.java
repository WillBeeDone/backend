package de.willbeedone.backend.repository;

import de.willbeedone.backend.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
