package com.app.pi.digitalbooking.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;
    private String codigo;
    private String nombre;
    private Boolean indicadorHabilitado;
}
