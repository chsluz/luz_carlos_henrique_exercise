package com.ecore.roles.web.rest;

import com.ecore.roles.web.dto.RoleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RoleRestController {

    ResponseEntity<Void> createRole(
            RoleDto role);

    ResponseEntity<List<RoleDto>> getRoles();

    ResponseEntity<RoleDto> getRole(
            UUID roleId);

    ResponseEntity<RoleDto> getRole(
            UUID teamId,
            UUID teamMemberId);

    ResponseEntity<Set<RoleDto>> getRoles(
            UUID teamId,
            UUID teamMemberId);

}
