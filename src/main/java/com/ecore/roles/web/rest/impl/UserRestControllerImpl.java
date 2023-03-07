package com.ecore.roles.web.rest.impl;

import com.ecore.roles.service.UserService;
import com.ecore.roles.client.dto.UserDto;
import com.ecore.roles.web.rest.UserRestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/users")
public class UserRestControllerImpl implements UserRestController {

    private final UserService userService;

    @Override
    @GetMapping(
            produces = {"application/json"})
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity
                .ok()
                .body(userService.getUsers());
    }

    @Override
    @GetMapping(
            path = "/{userId}",
            produces = {"application/json"})
    public ResponseEntity<UserDto> getUser(
            @PathVariable UUID userId) {
        return ResponseEntity
                .ok()
                .body(userService.getUser(userId));
    }
}
