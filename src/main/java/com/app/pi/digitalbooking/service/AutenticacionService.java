package com.app.pi.digitalbooking.service;

import com.app.pi.digitalbooking.excepciones.*;
import com.app.pi.digitalbooking.model.dto.RegistroDto;
import com.app.pi.digitalbooking.model.dto.RegistroResponseDto;
import com.app.pi.digitalbooking.model.dto.UsuarioDto;

import javax.mail.MessagingException;

public interface AutenticacionService {

    RegistroResponseDto registrar(RegistroDto registroDto) throws RegistroExistenteException, CamposInvalidosException, MessagingException, RegistroNoEncontradoException;

    RegistroResponseDto iniciarSesion(RegistroDto registroDto);

    UsuarioDto validarUsuario(Integer id, String tokenValidacionCorreo) throws UsuarioNoValidadoException, TokenInvalidoException;

}
