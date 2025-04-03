package de.willbeedone.backend.repository;

import de.willbeedone.backend.domain.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {



}
