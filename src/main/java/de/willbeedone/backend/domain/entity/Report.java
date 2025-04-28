package de.willbeedone.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table (name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @NotBlank
    @Size(min = 6, max = 500, message = "Reason must be between 6 and 500 characters")
    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @NotNull
    @CreationTimestamp
    @Column(updatable = false, name = "createdAt", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active;

    @Override
    public String toString() {
        return String.format(
                "Report{id=%d, reason='%s', createdAt=%s, active=%s, userId=%s, offerId=%s}",id,reason,createdAt,active,user,offer );
    }

}
