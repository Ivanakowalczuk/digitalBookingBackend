package com.app.pi.digitalbooking.service;


import com.app.pi.digitalbooking.model.dto.ImagenDto;
import com.app.pi.digitalbooking.model.dto.ProductosImagenesDto;

import java.util.List;

public interface ProductosImagenesService extends CrudService<ProductosImagenesDto, Integer>{
    List<ImagenDto> buscarTodasLasImagenesPorIdProducto(Integer idProducto);
}
