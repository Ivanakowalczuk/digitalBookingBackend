package com.app.pi.digitalbooking.service;
import com.app.pi.digitalbooking.model.dto.CategoriaDto;
import com.app.pi.digitalbooking.model.dto.CategoriaParcialDto;

import java.util.List;


public interface CategoriaService extends CrudService<CategoriaDto, Integer>{
    CategoriaDto buscarPorCodigo(String codigo);
    List<CategoriaParcialDto> buscarTodosPorIndicadorHabilitadoEsTrue();
}
