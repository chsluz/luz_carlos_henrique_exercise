package com.ecore.roles.service;

import com.ecore.roles.client.TeamClient;
import com.ecore.roles.client.UserClient;
import com.ecore.roles.client.dto.TeamDto;
import com.ecore.roles.client.dto.UserDto;
import com.ecore.roles.exception.InvalidArgumentException;
import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.impl.MembershipServiceImpl;
import com.ecore.roles.web.dto.MembershipDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.ecore.roles.utils.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    @InjectMocks
    private MembershipServiceImpl membershipsService;
    @Mock
    private MembershipRepository membershipRepository;
    @Mock
    private UserClient userClient;

    @Mock
    private TeamClient teamClient;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserService usersService;
    @Mock
    private TeamService teamService;

    @Test
    public void shouldCreateMembership() {
        MembershipDto membershipDto = MembershipDto.fromModel(DEFAULT_MEMBERSHIP());
        when(roleRepository.findById(membershipDto.getRoleId()))
                .thenReturn(Optional.ofNullable(DEVELOPER_ROLE()));
        when(membershipRepository.findByUserIdAndTeamId(membershipDto.getUserId(),
                membershipDto.getTeamId()))
                        .thenReturn(Optional.empty());
        when(userClient.getUser(membershipDto.getUserId()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(UserDto.fromModel(GIANNI_USER())));
        when(teamClient.getTeam(membershipDto.getTeamId()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK)
                        .body(TeamDto.fromModel(ORDINARY_CORAL_LYNX_TEAM())));
        when(membershipRepository.save(any())).thenReturn(membershipDto.toModel());
        membershipsService.assignRoleToMembership(membershipDto);
        verify(roleRepository).findById(membershipDto.getRoleId());
    }

    @Test
    public void shouldFailToCreateMembershipWhenMembershipsIsNull() {
        assertThrows(NullPointerException.class,
                () -> membershipsService.assignRoleToMembership(null));
    }

    @Test
    public void shouldFailToCreateMembershipWhenItExists() {
        MembershipDto membershipDto = MembershipDto.fromModel(DEFAULT_MEMBERSHIP());
        when(membershipRepository.findByUserIdAndTeamId(membershipDto.getUserId(),
                membershipDto.getTeamId()))
                        .thenReturn(Optional.of(membershipDto.toModel()));
        ResourceExistsException exception = assertThrows(ResourceExistsException.class,
                () -> membershipsService.assignRoleToMembership(membershipDto));
        assertEquals("MembershipDto already exists", exception.getMessage());
        verify(roleRepository, times(0)).getById(any());
        verify(usersService, times(0)).getUser(any());
        verify(teamService, times(0)).getTeam(any());
    }

    @Test
    public void shouldFailToCreateMembershipWhenItHasInvalidRole() {
        MembershipDto membershipDto = MembershipDto.fromModel(DEFAULT_MEMBERSHIP());
        membershipDto.setRoleId(null);
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class,
                () -> membershipsService.assignRoleToMembership(membershipDto));
        assertEquals("Invalid 'Role' object", exception.getMessage());
        verify(membershipRepository, times(0)).findByUserIdAndTeamId(any(), any());
        verify(roleRepository, times(0)).getById(any());
        verify(usersService, times(0)).getUser(any());
        verify(teamService, times(0)).getTeam(any());
    }

    @Test
    public void shouldFailToGetMembershipsWhenRoleIdIsNull() {
        assertThrows(NullPointerException.class,
                () -> membershipsService.getMemberships(null));
    }

}
