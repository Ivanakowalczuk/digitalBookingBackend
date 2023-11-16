package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.MensajesException;
import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.RegistroDto;
import com.app.pi.digitalbooking.model.dto.UsuarioDto;
import com.app.pi.digitalbooking.model.entity.Persona;
import com.app.pi.digitalbooking.model.entity.Rol;
import com.app.pi.digitalbooking.model.entity.Usuario;
import com.app.pi.digitalbooking.repository.PersonaRepository;
import com.app.pi.digitalbooking.repository.RolRepository;
import com.app.pi.digitalbooking.repository.UsuarioRepository;
import com.app.pi.digitalbooking.service.GestionUsuariosService;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GestionUsuariosServiceImpl implements GestionUsuariosService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MapperObject mapper;

    @Override
    public List<UsuarioDto> listar(PaginableDTO pageable) {
        return Optional.of(usuarioRepository.findAll(PageRequest.of(pageable.getPagina(), pageable.getCantidad()))).get().get().map(c -> mapper.map().convertValue(c, UsuarioDto.class)).collect(Collectors.toList());
    }

    @Override
    public UsuarioDto crear(UsuarioDto usuarioDto) throws NombreProductoExisteExcepcion {
        return null;
    }

    @Override
    public void eliminar(Integer id) {

    }

    @Override
    public UsuarioDto actualizar(UsuarioDto usuarioDto) throws NombreProductoExisteExcepcion {
        return null;
    }

    @Override
    public Optional<UsuarioDto> buscarPorId(Integer id) {
        return Optional.of(Optional.of(mapper.map().convertValue(usuarioRepository.findById(id).orElse(new Usuario()), UsuarioDto.class))).orElse(null);
    }

    @Override
    public UsuarioDto crear(RegistroDto registroDto) throws RegistroExistenteException, RegistroNoEncontradoException {

        Optional<Persona> personaEncontrada = personaRepository.findByCorreo(registroDto.getCorreo());
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByUsuario(registroDto.getUsuario());
        Optional<Rol> rol = rolRepository.findById(registroDto.getRol());

        if (rol.isEmpty()) {
            throw new RegistroNoEncontradoException("Rol invalido");
        }

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
                .verificado(rol.get().getCodigo().equals("ADMIN"))
                .contrasenia(registroDto.getContrasenia())
                .rol(rol.get())
                .build();

        String claveCifrada = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(claveCifrada);


        Persona personaRegistrada = personaRepository.save(persona);
        usuario.setPersona(personaRegistrada);
        Usuario usuarioRegistrado = usuarioRepository.save(usuario);

        return mapper.map().convertValue(usuarioRegistrado, UsuarioDto.class);

    }

    @Override
    public UsuarioDto actualizar(Integer id, RegistroDto registroDto) throws RegistroNoEncontradoException { //NombreProductoExisteExcepcion {

 //       validarDataUsuario(registroDto);
        Usuario usuario = usuarioRepository.findByIdUsuario(id).orElse(null);
        Rol rol = rolRepository.findById(registroDto.getRol()).orElse(null);

        if (rol == null) {
            throw new RegistroNoEncontradoException("Rol no válido");
        }

        if (usuario != null) {
            Persona persona = personaRepository.findByIdPersona(usuario.getPersona().getIdPersona()).orElse(null);

            if (persona != null) {
                persona.setNombre(registroDto.getNombre() != null ? registroDto.getNombre() : persona.getNombre());
                persona.setApellido(registroDto.getApellido() != null ? registroDto.getApellido() : persona.getApellido());
                persona.setCorreo(registroDto.getCorreo() != null ? registroDto.getCorreo() : persona.getCorreo());
                personaRepository.save(persona);

                usuario.setUsuario(registroDto.getUsuario() != null ? registroDto.getUsuario() : usuario.getUsuario());
                usuario.setContrasenia(registroDto.getContrasenia() != null ? passwordEncoder.encode(registroDto.getContrasenia()) : usuario.getContrasenia());
                usuario.setVerificado(registroDto.getVerificado() != null ? registroDto.getVerificado() : usuario.getVerificado());
                usuario.setIndicadorHabilitado(registroDto.getIndicadorHabilitado() != null ? registroDto.getIndicadorHabilitado() : usuario.getIndicadorHabilitado());
                usuario.setRol(rol);
                usuario.setPersona(persona);

                usuarioRepository.save(usuario);
                return mapper.map().convertValue(usuario, UsuarioDto.class);

            }

        }

        throw new RegistroNoEncontradoException("Usuario no registra en la bd");
    }

    @Override
    public UsuarioDto buscarUsuarioPorCorreo(String correo) throws RegistroNoEncontradoException {
        return usuarioRepository.findByPersona_Correo(correo)
                .map(u -> mapper.map().convertValue(u, UsuarioDto.class))
                .orElseThrow(() -> new RegistroNoEncontradoException(MensajesException.REGISTRO_NO_ENCONTRADO.getMensaje()));
    }

    private void validarDataUsuario(RegistroDto registroDto) throws NombreProductoExisteExcepcion {
        Usuario usuario = usuarioRepository.findByUsuario(registroDto.getUsuario()).orElse(null);
        Persona persona = personaRepository.findByCorreo(registroDto.getCorreo()).orElse(null);

        if (usuario != null || persona != null) {
            throw new NombreProductoExisteExcepcion("Correo o usuario previamente registrados");
        }
    }
}
