package de.willbeedone.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    @Column(name = "city_name", nullable = false)
    @Size(min = 2, max = 50, message = "City name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]+$", message = "City name can only contain letters, spaces, and hyphens")
    private String cityName;

    public Location() {
    }

    public Long getId() {
        return id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public @Size(min = 3, max = 255, message = "City name must be between 3 and 255 characters") String getCityName() {
        return cityName;
    }

    public void setCityName(@Size(min = 3, max = 255, message = "City name must be between 3 and 255 characters") String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;
        return Objects.equals(id, location.id) && Objects.equals(users, location.users) && Objects.equals(cityName, location.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, users, cityName);
    }

    @Override
    public String toString() {
        return String.format("Location: id - %d, cityName - %s", id, cityName);
    }
}
