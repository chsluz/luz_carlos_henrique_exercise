package com.ecore.roles.service;

import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.web.dto.MembershipDto;

import java.util.List;
import java.util.UUID;

public interface MembershipService {

    void assignRoleToMembership(MembershipDto memberShipDTO) throws ResourceNotFoundException;

    List<MembershipDto> getMemberships(UUID roleId);
}
