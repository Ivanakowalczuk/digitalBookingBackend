package com.app.pi.digitalbooking.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "categorias", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;
    private String codigo;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private Boolean indicadorHabilitado;
    private Integer idImagen;

    @Transient
    private String urlImagen;

    @OneToOne
    @JoinColumn(name = "idImagen", referencedColumnName = "idImagen", insertable = false, updatable = false)
    private Imagen imagen;

    public String getUrlImagen(){
        return imagen != null ? imagen.getUrlImagen() : null;
    }
}