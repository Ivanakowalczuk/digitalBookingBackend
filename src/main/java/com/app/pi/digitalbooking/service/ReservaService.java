package com.app.pi.digitalbooking.service;

import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.FechasRespuestasDto;
import com.app.pi.digitalbooking.model.dto.PeticionFechasDto;
import com.app.pi.digitalbooking.model.dto.ReservaDto;
import com.app.pi.digitalbooking.model.entity.Reserva;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservaService extends CrudService<ReservaDto, Integer>{
    Optional<ReservaDto> buscarUnico(Integer idProducto, Integer idUsuario, LocalDate fehaInicio, LocalDate fechaFin) throws RegistroNoEncontradoException;
    List<Reserva> buscarPorIdUsuario(Integer idUsuario);
    List<ReservaDto> buscarPorIdProducto(Integer idProducto);
    FechasRespuestasDto fechasInhabilitadas(PeticionFechasDto requestDto);
}
