package ait.cohort52.final_project.service;

import ait.cohort52.final_project.domain.dto.requestDto.RoleRequestDto;
import ait.cohort52.final_project.domain.dto.responseDto.RoleResponseDto;
import ait.cohort52.final_project.domain.entity.Role;
import ait.cohort52.final_project.repository.RoleRepository;
import ait.cohort52.final_project.service.intrfaces.RoleService;
import ait.cohort52.final_project.service.mapping.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final MappingService mappingService;


    @Override
    public Role addRole(RoleRequestDto roleRequestDto) {

        return repository.save(mappingService.getRoleFromDto(roleRequestDto));
    }


    @Override
    public void deleteRole(Long id) {
repository.deleteById(id);
    }

    @Override
    public List<RoleResponseDto> getAllRoles() {

        return repository.findAll().stream()
                .map(mappingService::getRoleDtoFromEntity)
                .toList();
}}
