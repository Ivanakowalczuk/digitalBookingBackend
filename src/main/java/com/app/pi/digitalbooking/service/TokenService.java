package com.app.pi.digitalbooking.service;

import com.app.pi.digitalbooking.model.entity.Token;
import com.app.pi.digitalbooking.model.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface TokenService {
    void crearToken(Token token);
    int actualizarConfirmacion(String token);
    Optional<Token> getToken(String token);

    List<Token> getTokenByUsuario(Usuario usuario);

}
