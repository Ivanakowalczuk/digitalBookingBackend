package com.app.pi.digitalbooking.model.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PeticionFechasDto {
    private Integer idProducto;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
}
