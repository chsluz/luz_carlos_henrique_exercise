package com.ecore.roles.service;

import com.ecore.roles.client.dto.TeamDto;

import java.util.List;
import java.util.UUID;

public interface TeamService {

    TeamDto getTeam(UUID id);

    List<TeamDto> getTeams();
}
