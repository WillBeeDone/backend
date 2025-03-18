package de.willbeedone.backend.domain.entity;
import de.willbeedone.backend.domain.entity.enums.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Pattern(
            regexp = "[A-Z][a-z]{1,}",
            message = "Product title should be at least three characters length and start with Capital letter"
    )
    private String firstName;

    @Column
    private boolean blocked;

    @Column(nullable = false)
    @Pattern(
            regexp = "[A-Z][a-z]{1,}",
            message = "Product title should be at least three characters length and start with Capital letter"
    )
    private String lastName;

//    @Column(nullable = false, unique = true)
//    private String username;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email cannot be empty or null")
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Location location;

    private String profilePicture;

    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Offer> offers;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return blocked == user.blocked && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(role, user.role) && location == user.location && Objects.equals(profilePicture, user.profilePicture) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(offers, user.offers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, blocked, lastName, email, password, role, location, profilePicture, phoneNumber, offers);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", blocked=" + blocked +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", location=" + location +
                ", profilePicture='" + profilePicture + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", offers=" + offers +
                '}';
    }
}


