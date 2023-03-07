package com.ecore.roles.web.rest.impl;

import com.ecore.roles.service.TeamService;
import com.ecore.roles.client.dto.TeamDto;
import com.ecore.roles.web.rest.TeamsRestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/teams")
public class TeamRestControllerImpl implements TeamsRestController {

    private final TeamService teamService;

    @Override
    @GetMapping(
            produces = {"application/json"})
    public ResponseEntity<List<TeamDto>> getTeams() {
        return ResponseEntity
                .ok()
                .body(teamService.getTeams());
    }

    @Override
    @GetMapping(
            path = "/{teamId}",
            produces = {"application/json"})
    public ResponseEntity<TeamDto> getTeam(
            @PathVariable UUID teamId) {
        return ResponseEntity
                .ok()
                .body(teamService.getTeam(teamId));
    }

}
