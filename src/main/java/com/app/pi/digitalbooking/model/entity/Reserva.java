package com.app.pi.digitalbooking.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "reservas", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReserva;
    private Integer idProducto;
    private LocalDate fechaInicioReserva;
    private LocalDate fechaFinReserva;
    private BigDecimal precioReserva;
    private Integer idUsuario;
    private Integer idSede;
}
