package com.app.pi.digitalbooking.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SedeDatosDto {
    private String sede;
    private String direccion;
    private String latitud;
    private String longitud;
    private Boolean indicadorHabilitado;
    private Integer idProvincia;
    private Integer idCiudad;
    private Integer idSede;
}