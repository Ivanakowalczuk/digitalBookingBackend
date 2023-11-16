package com.app.pi.digitalbooking.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class SedeDto {
    private Integer idSede;
    private String sede;
    private Integer idProvincia;
    private Integer idCiudad;
    private String direccion;
    private String latitud;
    private String longitud;
    private Boolean indicadorHabilitado;
}