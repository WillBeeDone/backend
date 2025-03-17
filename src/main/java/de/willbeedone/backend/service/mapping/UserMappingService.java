package de.willbeedone.backend.service.mapping;

import de.willbeedone.backend.domain.dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.response_dto.UserResponseDto;
import de.willbeedone.backend.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMappingService {

    UserRequestDto mapRequestEntityToDto(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blocked", constant = "false")
    User mapDtoToRequestEntity(UserRequestDto dto);


    UserResponseDto mapResponseEntityToDto(User entity);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blocked", constant = "false")
    User mapDtoToResponseEntity(UserResponseDto dto);



    }
