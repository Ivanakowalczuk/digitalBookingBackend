package com.app.pi.digitalbooking.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "productosImagenes", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class ProductosImagenes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProductoImagen;
    private Integer idImagen;
    private Integer idProducto;

    @OneToOne
    @JoinColumn(name = "idImagen", referencedColumnName = "idImagen", insertable = false, updatable = false)
    private Imagen imagen;

}
