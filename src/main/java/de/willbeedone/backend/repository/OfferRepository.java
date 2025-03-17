package de.willbeedone.backend.repository;

import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.domain.entity.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface OfferRepository extends JpaRepository<Offer, Long> {
    Optional<Offer> findOfferByTitle(String title);

    Optional<List<Offer>> findOffersByCategory(Category category);

    Optional<List<Offer>> findOffersByUser(User user);

}
