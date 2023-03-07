package com.ecore.roles.service.impl;

import com.ecore.roles.client.UserClient;
import com.ecore.roles.client.dto.UserDto;
import com.ecore.roles.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UserService {

    private final UserClient userClient;

    public UserDto getUser(UUID id) {
        return userClient.getUser(id).getBody();
    }

    public List<UserDto> getUsers() {
        return userClient.getUsers().getBody().stream()
                .map(UserDto::fromModel)
                .collect(Collectors.toList());
    }
}
