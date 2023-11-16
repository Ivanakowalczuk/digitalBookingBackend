package com.app.pi.digitalbooking.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDto {
    private Integer idReserva;
    private Integer idProducto;
    private LocalDate fechaInicioReserva;
    private LocalDate fechaFinReserva;
    private BigDecimal precioReserva;
    private Integer idUsuario;
    private Integer idSede;

    private ProductoDto producto;
}
