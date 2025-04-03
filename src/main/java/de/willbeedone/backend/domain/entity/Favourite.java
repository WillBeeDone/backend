package de.willbeedone.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "favourite")
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "favourite_offer",
            joinColumns = @JoinColumn(name = "favourite_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id")
    )
    private Set<Offer> offers;

    //Add new offer to favourites
    public void addOffer(Offer offer) {
        offers.add(offer);
    }

    //Clear favourites (erase all)
    public void clear() {
        offers.clear();
    }

    //Remove offer from favourites
    public void removeOfferById(Long offerId) {
        offers.removeIf(offer -> offer.getId().equals(offerId));
    }

    @Override
    public String toString() {
        return String.format("Favourite: id - %d", id);
    }
}
