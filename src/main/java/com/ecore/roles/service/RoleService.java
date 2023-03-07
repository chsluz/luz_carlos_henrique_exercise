package com.ecore.roles.service;

import com.ecore.roles.web.dto.RoleDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RoleService {

    void createRole(RoleDto role);

    RoleDto getRole(UUID id);

    RoleDto getRole(UUID teamId, UUID teamMemberId);

    List<RoleDto> getRoles();

    Set<RoleDto> getRoles(UUID teamId, UUID teamMemberId);
}
