package com.ecore.roles.web.rest.impl;

import com.ecore.roles.service.RoleService;
import com.ecore.roles.web.dto.RoleDto;
import com.ecore.roles.web.rest.RoleRestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/roles")
public class RoleRestControllerImpl implements RoleRestController {

    private final RoleService roleService;

    @Override
    @PostMapping(
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<Void> createRole(
            @Valid @RequestBody RoleDto role) {
        roleService.createRole(role);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    @GetMapping(
            produces = {"application/json"})
    public ResponseEntity<List<RoleDto>> getRoles() {
        return ResponseEntity
                .ok()
                .body(roleService.getRoles());
    }

    @Override
    @GetMapping(
            path = "/{roleId}",
            produces = {"application/json"})
    public ResponseEntity<RoleDto> getRole(
            @PathVariable UUID roleId) {
        return ResponseEntity
                .ok()
                .body(roleService.getRole(roleId));
    }

    @Override
    @GetMapping(
            path = "/teams/{teamId}/member/{teamMemberId}",
            produces = {"application/json"})
    public ResponseEntity<RoleDto> getRole(@PathVariable UUID teamId, @PathVariable UUID teamMemberId) {
        return ResponseEntity
                .ok()
                .body(roleService.getRole(teamId, teamMemberId));
    }

    @Override
    @GetMapping(
            path = "/search",
            produces = {"application/json"})
    public ResponseEntity<Set<RoleDto>> getRoles(
            @RequestParam(required = false, defaultValue = "") UUID teamId,
            @RequestParam(required = false, defaultValue = "") UUID teamMemberId) {
        return ResponseEntity
                .ok()
                .body(roleService.getRoles(teamId, teamMemberId));
    }

}
