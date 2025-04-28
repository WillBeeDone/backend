package de.willbeedone.backend.domain.dto.report_dto.response_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "A class that defines the Report DTO for responses")
public class ReportResponseDto {
    @NotNull
    @Schema(description = "Report ID", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Reason for report", example = "Invalid contacts")
    private String reason;

    @Email
    @Schema(description = "Email of the user who reports", example = "john.doe@example.com")
    private String reporterEmail;

    @Email
    @Schema(description = "Email of the user being reported", example = "jane.doe@example.com")
    private String reportedEmail;

    @NotNull
    @CreationTimestamp
    @Column(updatable = false, name = "createdAt", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active;

    public String toString() {
        return String.format("Report: id - %d, reporterEmail - %s, reportedEmail - %s, reason - %s, active=%s, createdAt=%s, ", id, reporterEmail, reportedEmail, reason, active, createdAt);
    }

}

