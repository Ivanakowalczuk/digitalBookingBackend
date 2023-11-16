package com.app.pi.digitalbooking.service;

import com.app.pi.digitalbooking.model.dto.ValoracionDto;

import java.util.List;
import java.util.Optional;

public interface ValoracionService extends CrudService<ValoracionDto, Integer> {
    List<ValoracionDto> buscarTodosPorIdProducto(Integer idProduct);
    List<ValoracionDto> buscarTodoPorIdUsuario(Integer idUsuario);
    Optional<ValoracionDto> buscarUnico (Integer idProduct, Integer idUsuario);
}
