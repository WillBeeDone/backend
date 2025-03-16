package ait.cohort52.final_project.domain.dto.requestDto;

import ait.cohort52.final_project.domain.entity.enums.Location;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserRequestDto {
    @NotBlank
    private String username;
    @Email
    private String email;
    private String firstName;
    private String lastName;
    private Location location;
    private String password;
    private String profilePicture;
    private String phoneNumber;
}
