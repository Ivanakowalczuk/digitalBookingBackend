package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.model.entity.Token;
import com.app.pi.digitalbooking.model.entity.Usuario;
import com.app.pi.digitalbooking.repository.TokenRepository;
import com.app.pi.digitalbooking.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Autowired
    private final TokenRepository tokenRepository;

    public void crearToken(Token token) {
        tokenRepository.save(token);
    }

    public Optional<Token> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public List<Token> getTokenByUsuario(Usuario usuario) {
        return tokenRepository.findByUsuario(usuario);
    }

    public int actualizarConfirmacion(String token) {
        return tokenRepository.updateFechaConfirmacion(
                token, LocalDateTime.now());
    }
}
