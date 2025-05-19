package de.willbeedone.backend.domain.dto.report_dto.response_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotBlank
    @Schema(description = "Reason for report", example = "Invalid contacts")
    private String reason;

    @NotBlank
    @Schema(description = "Email of the user who reports", example = "john.doe@example.com")
    private String reporterEmail;

    @NotNull
    @Schema(description = "Offer ID", example = "12")
    private Long offerId;

    @NotNull
    @Schema(description = "Date and time when the report was created (ISO format)", example = "2025-04-29T15:30:00")
    private LocalDateTime createdAt;

    @NotNull
    @Schema(description = "Indicates whether the report is currently active", example = "true")
    private boolean active;

    public String toString() {
        return String.format("Report: id - %d, reporterEmail - %s, offer ID - %d, reason - %s, active=%s, createdAt=%s", id, reporterEmail, offerId, reason, active, createdAt);
    }

}

