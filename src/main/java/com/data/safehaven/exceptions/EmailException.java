package com.data.safehaven.exceptions;

import lombok.Getter;

@Getter
public class EmailException extends RuntimeException {
    private final String email;
    public EmailException(String email) {
        super("El email '" + email + "' ya está registrado.");
        this.email = email;
    }

}
