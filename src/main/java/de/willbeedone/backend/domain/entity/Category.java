package de.willbeedone.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = "offers")
@NoArgsConstructor
@Table(name = "category")
public class Category {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Setter(AccessLevel.NONE)
        @Column(name = "id")
        private Long id;

        @NotBlank
        @Column(name = "name")
        @Pattern(regexp = "^[A-Za-zА-Яа-я0-9\\s-]{3,50}$", message = "Name must be between 3 and 50 characters and contain only letters, numbers, spaces, or hyphens")
        private String name;

        @JsonIgnore
        @OneToMany(mappedBy = "category")
        private List<Offer> offers = new ArrayList<>();


    @Override
    public String toString() {
        return String.format("Category: id - %d, name - %s", id, name);
    }
}
