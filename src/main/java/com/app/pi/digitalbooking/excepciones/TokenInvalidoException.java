package com.app.pi.digitalbooking.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenInvalidoException extends Exception{
    public TokenInvalidoException(String message) {
        super(message);
    }
}
