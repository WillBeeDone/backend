package ait.cohort52.final_project.repository;

import ait.cohort52.final_project.domain.entity.Offer;
import ait.cohort52.final_project.domain.entity.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface OfferRepository extends JpaRepository<Offer, Long> {
Optional<Offer> findOfferById(Long id);
Optional<Offer> findOfferByTitle(String title);
Optional<List<Offer>>findOffersByCategory(Category category);
}
