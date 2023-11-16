package com.app.pi.digitalbooking.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
@Getter
@Setter
@Builder
public class ProductosImagenesDto {

    private Integer idProductoImagen;
    @NotNull
    @Positive(message = "debe ser un número secuencial positivo")
    private Integer idImagen;
    @NotNull
    @Positive(message = "debe ser un número secuencial positivo")
    private Integer idProducto;

    private ImagenDto imagen;

    public ProductosImagenesDto(){
    }

    public ProductosImagenesDto(Integer idProductoImagen, Integer idImagen, Integer idProducto, ImagenDto imagen) {
        this.idProductoImagen = idProductoImagen;
        this.idImagen = idImagen;
        this.idProducto = idProducto;
        this.imagen = imagen;
    }
}
