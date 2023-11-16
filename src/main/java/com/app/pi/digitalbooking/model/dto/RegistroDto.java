package com.app.pi.digitalbooking.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDto {
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String usuario;
    private String contrasenia;
    private Integer rol;
    private Boolean verificado;
    private Boolean indicadorHabilitado;
}
