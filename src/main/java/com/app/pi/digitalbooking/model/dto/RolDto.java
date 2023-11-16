package com.app.pi.digitalbooking.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolDto {
    private Integer idRol;
    private String nombre;
    private String codigo;
    private Boolean indicadorHabilitado;
}
