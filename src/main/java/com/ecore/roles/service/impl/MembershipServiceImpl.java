package com.ecore.roles.service.impl;

import com.ecore.roles.client.TeamClient;
import com.ecore.roles.client.UserClient;
import com.ecore.roles.client.dto.TeamDto;
import com.ecore.roles.client.dto.UserDto;
import com.ecore.roles.client.model.Team;
import com.ecore.roles.client.model.User;
import com.ecore.roles.exception.InvalidArgumentException;
import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.exception.UserDoesntBelongTeamException;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.MembershipService;
import com.ecore.roles.web.dto.MembershipDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Log4j2
@RequiredArgsConstructor
@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final RoleRepository roleRepository;
    private final UserClient userClient;
    private final TeamClient teamClient;

    @Override
    public void assignRoleToMembership(@NonNull MembershipDto membershipDto) {
        UUID roleId = ofNullable(membershipDto.getRoleId())
                .orElseThrow(() -> new InvalidArgumentException(Role.class));
        UUID userId = ofNullable(membershipDto.getUserId())
                .orElseThrow(() -> new InvalidArgumentException(User.class));
        UUID teamId = ofNullable(membershipDto.getTeamId())
                .orElseThrow(() -> new InvalidArgumentException(Team.class));
        if (membershipRepository.findByUserIdAndTeamId(userId, teamId)
                .isPresent()) {
            throw new ResourceExistsException(MembershipDto.class);
        }
        UserDto userDto = ofNullable(userClient.getUser(userId).getBody())
                .orElseThrow(() -> new ResourceNotFoundException(User.class, userId));
        TeamDto teamDto = ofNullable(teamClient.getTeam(teamId).getBody())
                .orElseThrow(() -> new ResourceNotFoundException(Team.class, teamId));
        roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException(Role.class, roleId));
        checkIfUserBelongToTeam(userDto, teamDto);
        membershipRepository.save(membershipDto.toModel());
    }

    @Override
    public List<MembershipDto> getMemberships(@NonNull UUID roleId) {
        roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException(Role.class, roleId));
        List<Membership> memberships = membershipRepository.findByRoleId(roleId);
        return memberships
                .stream()
                .map(MembershipDto::fromModel)
                .collect(Collectors.toList());
    }

    private void checkIfUserBelongToTeam(UserDto userDto, TeamDto teamDto) {
        boolean contains = teamDto.getTeamMemberIds().contains(userDto.getId());
        if (!userDto.getId().equals(teamDto.getTeamLeadId()) && !contains) {
            throw new UserDoesntBelongTeamException();
        }
    }
}
