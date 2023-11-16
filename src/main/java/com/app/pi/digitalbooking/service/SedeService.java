package com.app.pi.digitalbooking.service;

import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.SedeDto;

import java.util.List;

public interface SedeService extends CrudService<SedeDto, Integer>{
    SedeDto buscarUnico(String sede, Integer idProvincia, Integer idCiudad);
    List<SedeDto> buscarTodosPorIdCiudad(Integer idCiudad, PaginableDTO pageable);
}
