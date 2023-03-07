package com.ecore.roles.service;

import com.ecore.roles.client.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto getUser(UUID id);

    List<UserDto> getUsers();
}
