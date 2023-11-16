package com.app.pi.digitalbooking.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "valoraciones", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idValoracion;
    private Integer idProducto;
    private Integer idUsuario;
    private Integer  valoracion;
    private String  comentario;

    @OneToOne
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto", insertable = false, updatable = false)
    private Producto producto;

    @OneToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    private Usuario usuario;

}
