package com.app.pi.digitalbooking.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductoDto {

    private Integer idProducto;
    @NotNull
    @Positive(message = "debe ser un número secuencial positivo")
    private Integer idCategoria;
    @NotBlank(message = "no puede ser null o vacío")
    private String nombre;
    private String marca;
    private String modelo;
    @NotNull
    private Boolean tieneProtector;
    @NotNull
    private Boolean esElectrico;
    @NotNull
    private BigDecimal precio;
    private Boolean indicadorHabilitado;
    @NotBlank(message = "no puede ser null o vacío")
    private String descripcion;
    @NotEmpty(message = "no puede ser vacia, el producto debe estar asociado por lo menos a una imagen")
    private List<Integer> listasIdsImagen;
    @NotEmpty(message = "no puede ser vacia, el producto debe estar asociado por lo menos a una sede")
    private List<Integer> listaIdsSedes;
    private CategoriaDto categoria;
    List<ProductosSoloImagenesDto> listaProductosImagenes;
    private List<SedeDatosDto> sede;
    private Double promedio;
}
