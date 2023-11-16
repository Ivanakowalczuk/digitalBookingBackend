package com.app.pi.digitalbooking.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class ValoracionDto {
    private Integer idValoracion;
    @NotNull
    @Positive(message = "debe ser un número secuencial positivo")
    private Integer idProducto;
    @NotNull
    @Positive(message = "debe ser un número secuencial positivo")
    private Integer idUsuario;
    @NotNull(message = "debe ser un número positivo entre el 1 y el 5")
    private Integer  valoracion;
    private String  comentario;
}
