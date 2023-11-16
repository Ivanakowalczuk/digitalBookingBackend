package com.app.pi.digitalbooking.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "productos", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;
    private Integer idCategoria;
    private String nombre;
    private String marca;
    private String modelo;
    private Boolean tieneProtector;
    private Boolean esElectrico;
    private BigDecimal precio;
    private Boolean indicadorHabilitado;
    private String descripcion;
    private Double promedio;
    @OneToOne
    @JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria", insertable = false, updatable = false)
    private Categoria categoria;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @Fetch(value = FetchMode.SUBSELECT)
    List<ProductosImagenes> listaProductosImagenes;
    @Transient
    private List<Integer> listasIdsImagen;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @Fetch(value = FetchMode.SUBSELECT)
    List<ProductoSede> listaProductoSedes;

    @Transient
    private List<Sede> sede;
    public List<Sede> getSede() {
        return listaProductoSedes != null ? listaProductoSedes.stream().map(ProductoSede::getSede).collect(Collectors.toList()) : new ArrayList<>();
    }

    @Transient
    private List<Integer> listaIdsSedes;

}