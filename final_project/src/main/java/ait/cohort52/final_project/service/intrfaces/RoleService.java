package ait.cohort52.final_project.service.intrfaces;

import ait.cohort52.final_project.domain.dto.requestDto.RoleRequestDto;
import ait.cohort52.final_project.domain.dto.responseDto.RoleResponseDto;
import ait.cohort52.final_project.domain.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {


    Role addRole(RoleRequestDto roleRequestDto);


    void deleteRole(Long id);

    List<RoleResponseDto> getAllRoles();
}

