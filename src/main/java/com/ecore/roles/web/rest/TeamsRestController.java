package com.ecore.roles.web.rest;

import com.ecore.roles.client.dto.TeamDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TeamsRestController {

    ResponseEntity<List<TeamDto>> getTeams();

    ResponseEntity<TeamDto> getTeam(UUID teamId);

}
