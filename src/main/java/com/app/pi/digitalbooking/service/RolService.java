package com.app.pi.digitalbooking.service;
import com.app.pi.digitalbooking.model.dto.RolDto;


public interface RolService extends CrudService<RolDto, Integer>{
    RolDto buscarPorCodigo(String codigo);
}
