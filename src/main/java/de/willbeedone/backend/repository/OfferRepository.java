package de.willbeedone.backend.repository;

import de.willbeedone.backend.domain.entity.Category;
import de.willbeedone.backend.domain.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import de.willbeedone.backend.domain.entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long>, JpaSpecificationExecutor<Offer>  {

    List<Offer> findOfferByTitle(String title);

    @Query("SELECT o FROM Offer o WHERE o.user.location.cityName = :cityName")
    Page<Offer> findByCity(@Param("cityName") String cityName, Pageable pageable);

    List<Offer> findOfferByTitleAndActiveIsTrue(String title);

    Optional<List<Offer>> findOffersByCategory(Category category);

    Optional<List<Offer>> findOffersByUser(User user);

    @Query("SELECT o FROM Offer o WHERE o.active = true ORDER BY o.pricePerHour ASC")
    Page<Offer> findActiveOffers(Pageable pageable);

    @Query("SELECT o FROM Offer o, Favourite f WHERE o MEMBER OF f.offers AND f.user.email = :userEmail AND o.active = true ORDER BY o.pricePerHour ASC")
    Page<Offer> findActiveFavouriteOffersByUserEmail(@Param("userEmail") String userEmail, Pageable pageable);

    @Query("from Offer")
    List<Offer> findAllOffersByPageRequest(PageRequest pageRequest);
}