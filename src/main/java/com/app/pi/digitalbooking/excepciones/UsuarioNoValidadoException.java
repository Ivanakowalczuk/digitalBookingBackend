package com.app.pi.digitalbooking.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioNoValidadoException extends Exception{
    public UsuarioNoValidadoException(String message) {
        super(message);
    }
}
