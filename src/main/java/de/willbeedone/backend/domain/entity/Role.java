package de.willbeedone.backend.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "title", unique = true, nullable = false)
    private String title;

    //SECURITY METHOD
    @Override
    public String getAuthority() {
        return title;
    }

    @Override
    public String toString() {
        return String.format("Role: id - %d, title - %s", id, title);
    }
}
