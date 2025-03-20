package de.willbeedone.backend.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.Set;

@Entity
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

    @Column(name = "blocked")
    private boolean blocked;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public @NotBlank(message = "First name cannot be empty") @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "First name should start with a capital letter and contain only letters"
    ) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name cannot be empty") @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "First name should start with a capital letter and contain only letters"
    ) String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Last name cannot be empty") @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "Last name should start with a capital letter and contain only letters"
    ) String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Last name cannot be empty") @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "Last name should start with a capital letter and contain only letters"
    ) String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "Email cannot be empty") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be empty") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password cannot be empty") @Size(min = 8, message = "Password must be at least 8 characters long") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password cannot be empty") @Size(min = 8, message = "Password must be at least 8 characters long") String password) {
        this.password = password;
    }

    public @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "Phone number should contain only digits and can start with +"
    ) String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "Phone number should contain only digits and can start with +"
    ) String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
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


