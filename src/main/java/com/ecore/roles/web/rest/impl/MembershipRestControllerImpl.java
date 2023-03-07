package com.ecore.roles.web.rest.impl;

import com.ecore.roles.service.MembershipService;
import com.ecore.roles.web.dto.MembershipDto;
import com.ecore.roles.web.rest.MembershipRestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/memberships")
public class MembershipRestControllerImpl implements MembershipRestController {

    private final MembershipService membershipService;

    @Override
    @PostMapping(
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity assignRoleToMembership(
            @NotNull @Valid @RequestBody MembershipDto membershipDto) {
        membershipService.assignRoleToMembership(membershipDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @GetMapping(
            path = "/roles/{roleId}",
            produces = {"application/json"})
    public ResponseEntity<List<MembershipDto>> getMemberships(
            @PathVariable UUID roleId) {
        List<MembershipDto> newMembershipDto = membershipService.getMemberships(roleId);
        return ResponseEntity
                .ok()
                .body(newMembershipDto);
    }

}
