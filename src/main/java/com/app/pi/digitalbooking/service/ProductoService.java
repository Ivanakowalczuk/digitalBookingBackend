package com.app.pi.digitalbooking.service;

import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.ProductoDto;

import java.util.List;
import java.util.Optional;

public interface ProductoService extends CrudService<ProductoDto, Integer>{
    Optional<ProductoDto> buscarPorNombre(String nombre);
    List<ProductoDto> buscarPorCategoriaPaginable(Integer idCategoria, PaginableDTO paginableDTO);
    List<ProductoDto> buscarAleatoriamente(PaginableDTO paginableDTO);

    List<ProductoDto> buscarPorSede(PaginableDTO paginableDTO, Integer idSede);

    List<ProductoDto> buscarPorCiudad(PaginableDTO paginableDTO, Integer idCiudad);

    List<ProductoDto> listaInhabilitados(PaginableDTO paginableDTO);
}
