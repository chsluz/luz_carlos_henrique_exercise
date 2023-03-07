package com.ecore.roles.web.rest;

import com.ecore.roles.client.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UserRestController {

    ResponseEntity<List<UserDto>> getUsers();

    ResponseEntity<UserDto> getUser(UUID userId);
}
