package com.app.pi.digitalbooking.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persona", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPersona;
    private String nombre;
    private String apellido;
    private String correo;
    private Boolean indicadorHabilitado;

}
