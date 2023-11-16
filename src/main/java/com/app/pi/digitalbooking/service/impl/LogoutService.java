package com.app.pi.digitalbooking.service.impl;


import com.app.pi.digitalbooking.model.entity.Token;
import com.app.pi.digitalbooking.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        jwt = authHeader.substring(7);
        tokenRepository.findByToken(jwt).ifPresent(t -> {
            t.setExpirado(true);
            t.setRevocado(true);
            tokenRepository.save(t);
        });
    }
}
