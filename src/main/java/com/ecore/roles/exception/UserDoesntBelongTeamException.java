package com.ecore.roles.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDoesntBelongTeamException extends RuntimeException {

    public <T> UserDoesntBelongTeamException() {

        super("Invalid 'Membership' object. The provided user doesn't belong to the provided team.");
    }
}
