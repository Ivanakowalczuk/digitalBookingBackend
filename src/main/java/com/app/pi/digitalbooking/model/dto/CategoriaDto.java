package com.app.pi.digitalbooking.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class CategoriaDto {
    private Integer idCategoria;
    @NotBlank(message = "no puede ser null o vacío")
    private String codigo;
    @NotBlank(message = "no puede ser null o vacío")
    private String nombre;
    @NotBlank(message = "no puede ser null o vacío")
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private Boolean indicadorHabilitado;
    @NotNull(message = "no puede ser null o vacío")
    private Integer idImagen;
    private String urlImagen;
}
