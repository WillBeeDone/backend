package de.willbeedone.backend.repository;

import de.willbeedone.backend.domain.entity.ImageGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageGalleryRepository extends JpaRepository<ImageGallery, Long> {
    void deleteByOfferId(Long offerId);
}