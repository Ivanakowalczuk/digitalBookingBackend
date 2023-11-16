package com.app.pi.digitalbooking.repository;

import com.app.pi.digitalbooking.model.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    Reserva findByIdProductoAndIdUsuarioAndFechaInicioReservaAndFechaFinReserva(Integer idProducto, Integer idUsuario, LocalDate fehaInicio, LocalDate fechaFin);
    List<Reserva> findAllByIdUsuario(Integer idUsuario);
    List<Reserva> findAllByIdProducto(Integer idProducto);
    List<Reserva> findByIdProductoAndFechaInicioReservaIsGreaterThanEqualAndFechaFinReservaLessThanEqual(Integer idProducto, LocalDate fehaInicio, LocalDate fechaFin);
}
