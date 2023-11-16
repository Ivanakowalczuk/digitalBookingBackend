package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.config.jwt.JwtService;
import com.app.pi.digitalbooking.excepciones.*;
import com.app.pi.digitalbooking.model.dto.RegistroDto;
import com.app.pi.digitalbooking.model.dto.RegistroResponseDto;
import com.app.pi.digitalbooking.model.dto.UsuarioDto;
import com.app.pi.digitalbooking.model.entity.Persona;
import com.app.pi.digitalbooking.model.entity.Token;
import com.app.pi.digitalbooking.model.entity.Usuario;

import static com.app.pi.digitalbooking.model.enums.Role.*;

import com.app.pi.digitalbooking.repository.PersonaRepository;
import com.app.pi.digitalbooking.repository.RolRepository;
import com.app.pi.digitalbooking.repository.TokenRepository;
import com.app.pi.digitalbooking.repository.UsuarioRepository;
import com.app.pi.digitalbooking.service.AutenticacionService;
import com.app.pi.digitalbooking.service.NotificacionesService;
import static com.app.pi.digitalbooking.util.email.impl.DatosCorreo.*;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AutenticacionServiceImpl implements AutenticacionService {

    @Autowired
    private final MapperObject mapper;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private NotificacionesService envioEmail;


    @Override
    public RegistroResponseDto registrar(RegistroDto registroDto) throws RegistroExistenteException, MessagingException, RegistroNoEncontradoException {

        Optional<Persona> personaEncontrada = personaRepository.findByCorreo(registroDto.getCorreo());
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByUsuario(registroDto.getUsuario());

        if (personaEncontrada.isPresent() || usuarioEncontrado.isPresent()) {
            throw new RegistroExistenteException(MensajesException.USUARIO_EXISTENTE.getMensaje());
        }

        Persona persona = Persona.builder()
                .correo(registroDto.getCorreo())
                .nombre(registroDto.getNombre())
                .apellido(registroDto.getApellido())
                .indicadorHabilitado(true)
                .build();

        Usuario usuario = Usuario.builder()
                .usuario(registroDto.getUsuario())
                .indicadorHabilitado(true)
                .verificado(false)
                .contrasenia(registroDto.getContrasenia())
                .rol(rolRepository.findByNombre(CLIENTE.getCodigo()).get())
                .build();


        String claveCifrada = bCryptPasswordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(claveCifrada);

        Persona personaRegistrada = personaRepository.save(persona);
        usuario.setPersona(personaRegistrada);
        usuario.setTokenValidacionCorreo(generarTokenAleatorio());
        usuario.setFechaExpiracionToken(LocalDateTime.now());
        Usuario usuarioRegistrado = usuarioRepository.save(usuario);

        RegistroResponseDto respuesta = new RegistroResponseDto(null, mapper.map().convertValue(usuarioRegistrado, UsuarioDto.class));

        Runnable hiloEjecutable = () -> {
            try {
                envioEmail.enviarNotificacion(VALIDAR, personaRegistrada.getCorreo());
            } catch (RegistroNoEncontradoException | MessagingException e) {
                throw new RuntimeException(e);
            }
        };
        if (usuario.getIdUsuario() != null) {
            Thread thread = new Thread(hiloEjecutable);
            thread.start();
        }

        return respuesta;
    }

    private String generarTokenAleatorio() {

        return UUID.randomUUID().toString();
    }

    public RegistroResponseDto iniciarSesion(RegistroDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsuario(),
                        loginDto.getContrasenia()
                )
        );
        Usuario usuario = usuarioRepository.findByUsuario(loginDto.getUsuario())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(usuario);
        revokeUserToken(usuario);
        saveUserToken(usuario, jwtToken);

        return RegistroResponseDto.builder()
                .token(jwtToken)
                .usuario(mapper.map().convertValue(usuario, UsuarioDto.class))
                .build();
    }

    private void saveUserToken(Usuario usuario, String jwtToken) {
        var token = Token.builder()
                .usuario(usuario)
                .token(jwtToken)
                .expirado(false)
                .revocado(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeUserToken(Usuario usuario) {
        List<Token> validUserToken = tokenRepository.buscarTokenValidosPorUsuario(usuario.getIdUsuario());
        if (validUserToken.isEmpty()) {
            return;
        }
        validUserToken.forEach(t -> {
            t.setExpirado(true);
            t.setRevocado(true);
        });

        tokenRepository.saveAll(validUserToken);
    }

    @Override
    public UsuarioDto validarUsuario(Integer id, String tokenValidacionCorreo) throws UsuarioNoValidadoException, TokenInvalidoException {

        Usuario usuario = usuarioRepository.findByIdUsuario(id).orElse(null);
        if (Objects.nonNull(usuario) &&
                tokenValidacionCorreo.equals(usuario.getTokenValidacionCorreo())) {

            validarTokenExpirado(usuario);
            usuario.setVerificado(true);
            usuario.setTokenValidacionCorreo(null);
            usuarioRepository.save(usuario);
            Runnable hiloEjecutable = () -> {
                try {
                    envioEmail.enviarNotificacion(CONFIRMAR, usuario.getPersona().getCorreo());
                } catch (RegistroNoEncontradoException | MessagingException e) {
                    throw new RuntimeException(e);
                }
            };
            if (usuario.getIdUsuario() != null) {
                Thread thread = new Thread(hiloEjecutable);
                thread.start();
            }

        } else {
            throw new UsuarioNoValidadoException("El token o el usuario no son válidos");
        }
        return mapper.map().convertValue(usuario, UsuarioDto.class);
    }

    private void validarTokenExpirado(Usuario usuario) throws  TokenInvalidoException {
        Duration duration = Duration.between(usuario.getFechaExpiracionToken(), LocalDateTime.now());
        int TIEMPO_EXPIRA_TOKEN = 48;
        Duration maxDuration = Duration.ofHours(TIEMPO_EXPIRA_TOKEN);
        if (duration.compareTo(maxDuration) >= 0) {
            throw new TokenInvalidoException("El token expiró");
        }
    }
}
