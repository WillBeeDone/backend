package de.willbeedone.backend.domain.dto.report_dto.request_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
    @Schema(description = "Reason for report", example = "Invalid contacts")
    private String reason;

    @Email
    @Schema(description = "Email of the user who reports", example = "john.doe@example.com")
    private String reporterEmail;

    @Email
    @Schema(description = "Email of the user being reported", example = "jane.doe@example.com")
    private String reportedEmail;

    public String toString() {
        return String.format("Report: reporterEmail - %s, reportedEmail - %s, reason - %s", reporterEmail, reportedEmail, reason);
    }


}
