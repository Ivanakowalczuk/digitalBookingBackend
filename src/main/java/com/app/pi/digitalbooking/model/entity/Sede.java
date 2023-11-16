package com.app.pi.digitalbooking.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "sedes", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class Sede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSede;
    private String sede;
    private Integer idProvincia;
    private Integer idCiudad;
    private String direccion;
    private String latitud;
    private String longitud;
    private Boolean indicadorHabilitado;
}
