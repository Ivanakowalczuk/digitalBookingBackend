package com.app.pi.digitalbooking.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "imagenes", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImagen;
    private String keyImagen;
    private String urlImagen;
    private Boolean indicadorHabilitado;
}
