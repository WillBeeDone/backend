package de.willbeedone.backend.domain.dto.report_dto.request_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "A class that defines the Report DTO for requests")
public class ReportRequestDto {

    @NotNull
    @Schema(description = "Offer ID", example = "12")
    private Long offerId;

    @NotBlank
    @Schema(description = "Reason for report", example = "Invalid contacts")
    private String reason;

    @NotBlank
    @Email
    @Schema(description = "Email of the user who reports", example = "john.doe@example.com")
    private String reporterEmail;


    public String toString() {
        return String.format("Report: offer ID - %d, reporterEmail - %s, reason - %s",offerId, reporterEmail,reason);
    }


}
