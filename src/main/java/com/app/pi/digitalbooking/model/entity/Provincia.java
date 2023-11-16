package com.app.pi.digitalbooking.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "provincias", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProvincia;
    //private Integer idPais;
    private String provincia;
    private Boolean indicadorHabilitado;
}
