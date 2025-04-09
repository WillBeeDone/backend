package de.willbeedone.backend.domain.dto.user_dto.response_dto;
import de.willbeedone.backend.domain.entity.Location;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedUserResponseDto {
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Location location;

    private String profilePicture;


}
