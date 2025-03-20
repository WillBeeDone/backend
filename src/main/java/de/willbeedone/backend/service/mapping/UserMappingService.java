package de.willbeedone.backend.service.mapping;


import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserFilterResponseDto;
import de.willbeedone.backend.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blocked", constant = "false")
    User mapRequestDtoToEntity(UserRequestDto dto);

    UserFilterResponseDto mapEntityToFilterResponseDto(User entity);

}