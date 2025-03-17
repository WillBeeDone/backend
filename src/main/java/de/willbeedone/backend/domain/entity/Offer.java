package de.willbeedone.backend.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.willbeedone.backend.domain.entity.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean active;

//    @Column
//    private Long destination;


    @Column(name = "location")
    private String location;

    @Column(name = "name", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal pricePerHour;

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @ElementCollection
    @JsonIgnore
    private List<String> gallery;
}
