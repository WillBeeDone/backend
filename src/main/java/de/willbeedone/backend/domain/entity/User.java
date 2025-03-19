package de.willbeedone.backend.domain.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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
    @Pattern(
            regexp = "[A-Z][a-z]{1,}",
            message = "Product title should be at least three characters length and start with Capital letter"
    )
    private String firstName;

    @Column(name = "blocked")
    private boolean blocked;

    @Column(name = "lastName", nullable = false)
    @Pattern(
            regexp = "[A-Z][a-z]{1,}",
            message = "Product title should be at least three characters length and start with Capital letter"
    )
    private String lastName;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email cannot be empty or null")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "profilePicture")
    private String profilePicture;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Offer> offers;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Pattern(
            regexp = "[A-Z][a-z]{1,}",
            message = "Product title should be at least three characters length and start with Capital letter"
    ) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Pattern(
            regexp = "[A-Z][a-z]{1,}",
            message = "Product title should be at least three characters length and start with Capital letter"
    ) String firstName) {
        this.firstName = firstName;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public @Pattern(
            regexp = "[A-Z][a-z]{1,}",
            message = "Product title should be at least three characters length and start with Capital letter"
    ) String getLastName() {
        return lastName;
    }

    public void setLastName(@Pattern(
            regexp = "[A-Z][a-z]{1,}",
            message = "Product title should be at least three characters length and start with Capital letter"
    ) String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "Email cannot be empty or null") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be empty or null") String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return blocked == user.blocked && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(role, user.role) && Objects.equals(profilePicture, user.profilePicture) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(offers, user.offers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, blocked, lastName, email, password, role, profilePicture, phoneNumber, offers);
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
                ", profilePicture='" + profilePicture + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", offers=" + offers +
                '}';
    }
}


