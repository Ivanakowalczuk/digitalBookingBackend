package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.model.dto.CategoriaDto;
import com.app.pi.digitalbooking.model.dto.CategoriaParcialDto;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.repository.CategoriaRepository;
import com.app.pi.digitalbooking.util.mapper.MapperObject;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoriaServiceImplTest {

    @Autowired
    private CategoriaRepository repository;
    private CategoriaServiceImpl subject;

    @Before
    public void setUp() {
        subject = new CategoriaServiceImpl(repository, new MapperObject());
    }

    @Test
    public void crearCategoriaDeFormaExitosa() {

        //Dado
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setCodigo("Codigo");
        categoriaDto.setDescripcion("Descripcion");
        categoriaDto.setFechaCreacion(LocalDateTime.now());
        categoriaDto.setIdImagen(1);
        categoriaDto.setIndicadorHabilitado(true);
        categoriaDto.setNombre("Nombre");
        categoriaDto.setUrlImagen("https://example.org/example");

        //Cuando
        CategoriaDto response = subject.crear(categoriaDto);

        //Entonces
        Assertions.assertEquals(categoriaDto.getNombre(), response.getNombre());

    }

    @Test
    public void listarCategoriasDeFormaExitosa() {

        //Dado
        PaginableDTO paginableDTO = new PaginableDTO();
        paginableDTO.setCantidad(2);
        paginableDTO.setPagina(0);

        //Cuando
        List<CategoriaDto> response = subject.listar(paginableDTO);

        //Entonces
        Assertions.assertEquals(2, response.size());

    }

    @Test
    public void eliminarCategoria() {

        //Dado
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setCodigo("Codigo");
        categoriaDto.setDescripcion("Descripcion");
        categoriaDto.setFechaCreacion(LocalDateTime.now());
        categoriaDto.setIdImagen(1);
        categoriaDto.setIndicadorHabilitado(true);
        categoriaDto.setNombre("Nombre");
        categoriaDto.setUrlImagen("https://example.org/example");

        //Cuando
        CategoriaDto categoria = subject.crear(categoriaDto);
        subject.eliminar(categoria.getIdCategoria());

        CategoriaDto response = subject.buscarPorId(categoria.getIdCategoria()).orElse(null);

        //Entonces
        assert response != null;
        Assertions.assertFalse(response.getIndicadorHabilitado());

    }

    @Test
    public void buscarCategoriaPorId() {

        //Dado
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setCodigo("IDBUSCADO");
        categoriaDto.setDescripcion("Descripcion");
        categoriaDto.setFechaCreacion(LocalDateTime.now());
        categoriaDto.setIdImagen(1);
        categoriaDto.setIndicadorHabilitado(true);
        categoriaDto.setNombre("Nombre");
        categoriaDto.setUrlImagen("https://example.org/example");

        //Cuando
        CategoriaDto categoria = subject.crear(categoriaDto);
        CategoriaDto response = subject.buscarPorId(categoria.getIdCategoria()).orElse(null);

        //Entonces
        assert response != null;
        Assertions.assertEquals(categoriaDto.getCodigo(),response.getCodigo());

    }

    @Test
    public void buscarCategoriasHabilitadas() {

        //Dado

        //Cuando
        List<CategoriaParcialDto> response = subject.buscarTodosPorIndicadorHabilitadoEsTrue();

        //Entonces
        Assertions.assertFalse(response.isEmpty());

    }
}