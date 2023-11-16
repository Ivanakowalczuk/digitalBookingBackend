package com.app.pi.digitalbooking.config.security;

import com.app.pi.digitalbooking.config.jwt.JwtAuthenticationFilter;
import static com.app.pi.digitalbooking.model.enums.Role.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    private final String[] URL_ABIERTAS = {
            "/docs",
            "/api-docs",
            "/api-docs/**",
            "/api-docs.yml",
            "/swagger/**",
            "/swagger-ui/**",
            "/api/v1/autenticacion/**",
            "/api/v1/producto/buscarTodos",
            "/api/v1/producto/aleatorio",
            "/api/v1/producto/paginable/categoria/**",
            "/api/v1/producto/{id:\\d+}",
            "/api/v1/categoria/activas",
            "/api/v1/categoria/{id:\\d+}",
            "/api/v1/imagen/buscarTodos",
            "/api/v1/imagen/{id:\\d+}",
            "/api/v1/productosImagenes/buscarTodos",
            "/api/v1/sedes/buscarTodos",
            "/api/v1/sedes/{id:\\d+}",
            "api/v1/sedes/buscarPorCategoria/{id:\\d+}",
            "api/v1/valoracion/{id:\\d+}",
            "/api/v1/valoracion/**",
    };

    private final String[] URL_CLIENTES = {
            "/api/v1/reserva/**"
    };

    private final String[] URL_ADMIN = {
            "/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf()
                .disable()
                .authorizeHttpRequests()
               .antMatchers("/**").permitAll()
//                .antMatchers(URL_ABIERTAS).permitAll()
//                .antMatchers(URL_ADMIN).hasAuthority(ADMIN.getCodigo())
//                .antMatchers(URL_CLIENTES).hasAnyAuthority(ADMIN.getCodigo(), CLIENTE.getCodigo())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
        return http.getOrBuild();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
