package com.app.pi.digitalbooking.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoriaParcialDto {
    private Integer idCategoria;
    private String nombre;
    private String descripcion;
    private Integer idImagen;
    private String urlImagen;
}
