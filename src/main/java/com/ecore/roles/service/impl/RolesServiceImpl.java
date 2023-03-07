package com.ecore.roles.service.impl;

import com.ecore.roles.client.TeamClient;
import com.ecore.roles.client.UserClient;
import com.ecore.roles.client.model.Team;
import com.ecore.roles.client.model.User;
import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.RoleService;
import com.ecore.roles.web.dto.RoleDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Log4j2
@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RoleService {

    public static final String DEFAULT_ROLE = "Developer";

    private final RoleRepository roleRepository;

    private final MembershipRepository membershipRepository;

    private final UserClient userClient;

    private final TeamClient teamClient;

    @Override
    public void createRole(@NonNull RoleDto r) {
        if (roleRepository.findByName(r.getName()).isPresent()) {
            throw new ResourceExistsException(Role.class);
        }
        roleRepository.save(r.toModel());
    }

    @Override
    public RoleDto getRole(@NonNull UUID rid) {
        return RoleDto.fromModel(roleRepository.findById(rid)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class, rid)));
    }

    @Override
    public RoleDto getRole(UUID teamId, UUID teamMemberId) {
        ofNullable(userClient.getUser(teamMemberId).getBody())
                .orElseThrow(() -> new ResourceNotFoundException(User.class, teamMemberId));
        ofNullable(teamClient.getTeam(teamId).getBody())
                .orElseThrow(() -> new ResourceNotFoundException(Team.class, teamId));
        Membership membership =
                membershipRepository.findByUserIdAndTeamId(teamMemberId, teamId)
                        .orElseThrow(() -> new ResourceNotFoundException(Team.class, teamId));
        return RoleDto.fromModel(membership.getRole());
    }

    @Override
    public List<RoleDto> getRoles() {
        return roleRepository
                .findAll()
                .stream()
                .map(RoleDto::fromModel)
                .collect(Collectors.toList());
    }

    @Override
    public Set<RoleDto> getRoles(UUID teamId, UUID teamMemberId) {
        if (teamId != null && teamMemberId != null) {
            Membership membership = membershipRepository.findByUserIdAndTeamId(teamMemberId, teamId)
                    .orElseThrow(() -> new ResourceNotFoundException(Team.class, teamId));
            return Set.of(RoleDto.fromModel(membership.getRole()));
        }
        if (teamId != null) {
            return membershipRepository
                    .findByTeamId(teamId)
                    .stream()
                    .map(m -> m.getRole())
                    .map(RoleDto::fromModel)
                    .collect(Collectors.toSet());
        }
        if (teamMemberId != null) {
            return membershipRepository
                    .findByUserId(teamMemberId)
                    .stream()
                    .map(m -> m.getRole())
                    .map(RoleDto::fromModel)
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }
}
