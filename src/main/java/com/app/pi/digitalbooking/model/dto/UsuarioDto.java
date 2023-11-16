package com.app.pi.digitalbooking.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioDto {
    private Integer idUsuario;
    private String usuario;
    private RolDto rol;
    private PersonaDto persona;
    private Boolean indicadorHabilitado;
    private Boolean verificado;
    private String tokenValidacionCorreo;
    private LocalDateTime fechaExpiracionToken;
}
