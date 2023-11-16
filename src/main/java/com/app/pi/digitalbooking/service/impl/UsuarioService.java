package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.model.dto.UsuarioDto;
import com.app.pi.digitalbooking.model.entity.Usuario;
import com.app.pi.digitalbooking.repository.UsuarioRepository;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final static String USUARIO_NO_ENCONTRADO = "Usuario con correo %s no registrado";
    @Autowired private final MapperObject mapper;

    @Autowired private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        return usuarioRepository.findByUsuario(usuario).orElseThrow(() -> new UsernameNotFoundException(String.format(USUARIO_NO_ENCONTRADO, usuario)));
    }

    public UsuarioDto registrarUsuario(Usuario usuario) {

        return mapper.map().convertValue(usuario, UsuarioDto.class);
    }

    public UsuarioDto habilitarUsuario(String correo) {
        Usuario usuario = usuarioRepository.findByUsuario(correo).orElseThrow(() -> new UsernameNotFoundException(USUARIO_NO_ENCONTRADO));
        usuario.setVerificado(true);
        usuarioRepository.save(usuario);
        return mapper.map().convertValue(usuario, UsuarioDto.class);
    }
}
