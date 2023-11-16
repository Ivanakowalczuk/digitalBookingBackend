package com.app.pi.digitalbooking.controller;

import com.app.pi.digitalbooking.excepciones.*;
import com.app.pi.digitalbooking.model.dto.RegistroDto;
import com.app.pi.digitalbooking.service.AutenticacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/v1/autenticacion")
public class AutenticacionController {
    @Autowired AutenticacionService autenticacionService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody RegistroDto registroDto) throws RegistroExistenteException, CamposInvalidosException, MessagingException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.OK).body(autenticacionService.registrar(registroDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody RegistroDto registroDto) {
        return ResponseEntity.status(HttpStatus.OK).body(autenticacionService.iniciarSesion(registroDto));
    }

    @GetMapping("/validar/{id}/{tokenValidacionCorreo}")
    public ResponseEntity<?> validarUsuario(@PathVariable Integer id, @PathVariable String tokenValidacionCorreo) throws UsuarioNoValidadoException, TokenInvalidoException {
        return ResponseEntity.status(HttpStatus.OK).body(autenticacionService.validarUsuario(id, tokenValidacionCorreo));
    }
}