package com.app.pi.digitalbooking.config.jwt;

import com.app.pi.digitalbooking.repository.TokenRepository;
import com.app.pi.digitalbooking.service.impl.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioService usuarioService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (request.getServletPath().contains("/api/v1/autenticacion")) {
            filterChain.doFilter(request, response);
            return;
        }
        //Extraer token de autorización en la petición
        final String authHeader = request.getHeader("Authorization");

        //Verificar que la petición viene autenticada
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtener la parte util del token
        final String jwt  = authHeader.substring(7);
        // Extraer correo del usuario del token
        final String correo = jwtService.extractUsername(jwt);
        // Verificar si el usuario ya fue previamente autenticado
        if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.usuarioService.loadUserByUsername(correo);

            Boolean esTokenValido = tokenRepository.findByToken(jwt)
                    .map(t -> !t.getExpirado() && !t.getRevocado())
                    .orElse(false);

            // Verificar si el token es válido
            if (jwtService.isTokenValid(jwt, userDetails) && esTokenValido && userDetails.isEnabled() && userDetails.isAccountNonLocked()) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
