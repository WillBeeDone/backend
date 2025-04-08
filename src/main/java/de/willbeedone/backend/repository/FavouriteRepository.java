package de.willbeedone.backend.repository;

import de.willbeedone.backend.domain.entity.Favourite;
import de.willbeedone.backend.domain.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    List<Favourite> findAllByOffersContaining(Offer offer);

}
