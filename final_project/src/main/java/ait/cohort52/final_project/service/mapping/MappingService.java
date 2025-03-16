package ait.cohort52.final_project.service.mapping;

import ait.cohort52.final_project.domain.dto.requestDto.OfferRequestDto;
import ait.cohort52.final_project.domain.dto.requestDto.RoleRequestDto;
import ait.cohort52.final_project.domain.dto.requestDto.UserRequestDto;
import ait.cohort52.final_project.domain.dto.responseDto.OfferResponseDto;
import ait.cohort52.final_project.domain.dto.responseDto.RoleResponseDto;
import ait.cohort52.final_project.domain.dto.responseDto.UserResponseDto;
import ait.cohort52.final_project.domain.entity.Offer;
import ait.cohort52.final_project.domain.entity.Role;
import ait.cohort52.final_project.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    // Конвертация UserRequestDto -> User
    public User getUserFromDto(UserRequestDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    // Конвертация User -> UserResponseDto
    public UserResponseDto getUserDtoFromEntity(User user) {
        return UserResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    // Конвертация OfferRequestDto -> Offer
    public Offer getOfferFromDto(OfferRequestDto dto) {
        return Offer.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }

    // Конвертация Offer -> OfferResponseDto
    public OfferResponseDto getOfferDtoFromEntity(Offer offer) {
        return OfferResponseDto.builder()
                .title(offer.getTitle())
                .description(offer.getDescription())
                .build();
    }

    // Конвертация RoleRequestDto -> Role
    public Role getRoleFromDto(RoleRequestDto dto) {
        return Role.builder()
                .title(dto.getTitle())
                .build();
    }

    // Конвертация Role -> RoleResponseDto
    public RoleResponseDto getRoleDtoFromEntity(Role role) {
        return RoleResponseDto.builder()
                .title(role.getTitle())
                .build();
    }
}
