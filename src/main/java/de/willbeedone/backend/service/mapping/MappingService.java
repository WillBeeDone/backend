package de.willbeedone.backend.service.mapping;

import de.willbeedone.backend.domain.dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.response_dto.OfferResponseDto;
import de.willbeedone.backend.domain.dto.response_dto.UserResponseDto;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.domain.entity.User;
import org.springframework.stereotype.Component;


@Component
public class MappingService {


    public User getUserFromDto(UserRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return new User(dto.getEmail(),
                dto.getPassword()
        );
    }

    public UserResponseDto getUserDtoFromEntity(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponseDto(
                user.getId(),
                user.getEmail()
        );

    }

    public Offer getOfferFromDto(OfferRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return new Offer(
                null,
                dto.getTitle(),
                dto.getDescription(),
                true
        );
    }

    public OfferResponseDto getOfferDtoFromEntity(Offer offer) {
        if (offer == null) {
            return null;
        }
        return new OfferResponseDto(
                offer.getId(),
                offer.getTitle(),
                offer.getDescription()
        );
    }


}
