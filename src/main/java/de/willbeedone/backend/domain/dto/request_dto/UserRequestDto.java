package de.willbeedone.backend.domain.dto.request_dto;


import de.willbeedone.backend.domain.entity.enums.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserRequestDto {
//    @NotBlank
//    private String username;
//    @Email
    private String email;
    private String firstName;
    private String lastName;
    private Location location;
    private String password;
    private String profilePicture;
    private String phoneNumber;
}
