package de.willbeedone.backend.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstName", nullable = false)
    @NotBlank(message = "First name cannot be empty")
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "First name should start with a capital letter and contain only letters"
    )
    private String firstName;

    @Column(name = "lastName", nullable = false)
    @NotBlank(message = "Last name cannot be empty")
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "Last name should start with a capital letter and contain only letters"
    )
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Column(name = "phoneNumber")
    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "Phone number should contain only digits and can start with +"
    )
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "profilePicture")
    private String profilePicture;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Offer> offers;

    @Column(name = "blocked", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean blocked;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return blocked == user.blocked && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(location, user.location) && Objects.equals(profilePicture, user.profilePicture) && Objects.equals(role, user.role) && Objects.equals(offers, user.offers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, phoneNumber, location, profilePicture, role, offers, blocked);
    }

    @Override
    public String toString() {
        return String.format("User: id - %d, firstName - %s, lastName - %s, email - %s, phoneNumber - %s, location - %s, profilePicture - %s, role - %s, offers - %s, blocked - %s", id, firstName, lastName, email, phoneNumber, location, profilePicture, role, offers, blocked ? "Yes" : "No");
    }
}


