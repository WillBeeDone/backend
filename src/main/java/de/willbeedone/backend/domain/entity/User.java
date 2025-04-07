package de.willbeedone.backend.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"offers", "favourites"})
@NoArgsConstructor
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "First name should start with a capital letter and contain only letters"
    )
    private String firstName;

    @Column(name = "last_name")
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "Last name should start with a capital letter and contain only letters"
    )
    private String lastName;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Column(name = "phone_number")
    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "Phone number should contain only digits and can start with +"
    )
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "profile_picture")
    private String profilePicture;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Offer> offers;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Favourite favourites;

    @Column(name = "active", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean active;

    @Column(name = "blocked", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean blocked;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("User: id - %d, firstName - %s, lastName - %s, email - %s, phoneNumber - %s, location - %s, profilePicture - %s, roles - %s, offers - %s, active - %s, blocked - %s", id, firstName, lastName, email, phoneNumber, location, profilePicture, roles, offers, active ? "Yes" : "No", blocked ? "Yes" : "No");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return blocked;
    }

}


