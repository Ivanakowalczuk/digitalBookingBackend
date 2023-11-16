package com.app.pi.digitalbooking.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ciudades", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCiudad;
    private Integer idProvincia;
    private String ciudad;
    private Boolean indicadorHabilitado;
}
