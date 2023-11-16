package com.app.pi.digitalbooking.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class CiudadDto {
    private Integer idCiudad;
    private Integer idProvincia;
    private String ciudad;
    private Boolean indicadorHabilitado;
}
