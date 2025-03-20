package de.willbeedone.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9\\s-]{3,50}$", message = "Name must be between 3 and 50 characters and contain only letters, numbers, spaces, or hyphens")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(offers, category.offers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, offers);
    }

    @Override
    public String toString() {
        return String.format("Category: id - %d, name - %s", id, name);
    }
}
