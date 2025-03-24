package de.willbeedone.backend.service.mapping;


import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserFilterResponseDto;
import de.willbeedone.backend.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {LocationMappingService.class})
public interface UserMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blocked", constant = "false")
    @Mapping(target = "firstName", source = "firstName", qualifiedByName = "mapNullableString")
    @Mapping(target = "lastName", source = "lastName", qualifiedByName = "mapNullableString")
    User mapRequestDtoToEntity(UserRequestDto dto);

    @Mapping(source = "location", target = "locationResponseDto")
    UserFilterResponseDto mapEntityToFilterResponseDto(User entity);

    @Named("mapNullableString")
    default String mapNullableString(String value) {
        return (value != null && !value.trim().isEmpty()) ? value : null;
    }
}
