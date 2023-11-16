package com.app.pi.digitalbooking.model.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ImagenDto {
    private Integer idImagen;
    private String keyImagen;
    private String urlImagen;
    private Boolean indicadorHabilitado;
}
