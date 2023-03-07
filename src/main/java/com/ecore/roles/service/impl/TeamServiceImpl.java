package com.ecore.roles.service.impl;

import com.ecore.roles.client.TeamClient;
import com.ecore.roles.client.dto.TeamDto;
import com.ecore.roles.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamClient teamClient;

    public TeamDto getTeam(UUID id) {
        return teamClient.getTeam(id).getBody();
    }

    public List<TeamDto> getTeams() {
        return teamClient.getTeams().getBody();
    }
}
