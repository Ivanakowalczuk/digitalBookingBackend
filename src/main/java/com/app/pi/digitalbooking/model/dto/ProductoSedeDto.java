package com.app.pi.digitalbooking.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoSedeDto {
    private Integer idSedeProducto;
    private Integer idProducto;
    private Integer idSede;
    private Boolean indicadorHabilitado;
}
