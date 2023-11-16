package com.app.pi.digitalbooking.service;

import com.app.pi.digitalbooking.model.dto.ProductoSedeDto;

public interface ProductoSedeService extends CrudService<ProductoSedeDto, Integer>{
    ProductoSedeDto buscarUnico(Integer idProducto, Integer idSede);
}
