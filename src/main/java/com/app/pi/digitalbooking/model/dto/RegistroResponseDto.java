package com.app.pi.digitalbooking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RegistroResponseDto {

    private String token;
    private UsuarioDto usuario;
}
