package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.CategoriaDto;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.RolDto;
import com.app.pi.digitalbooking.repository.CategoriaRepository;
import com.app.pi.digitalbooking.repository.RolRepository;
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
public class RolServiceImplTest {

    @Autowired
    private RolRepository repository;
    private RolServiceImpl subject;

    @Before
    public void setUp() {
        subject = new RolServiceImpl(repository, new MapperObject());
    }


    @Test
    public void crear() throws RegistroExistenteException {
        //Dado
        RolDto rolDto = new RolDto();
        rolDto.setNombre("Tester");
        rolDto.setCodigo("TESTER");
        rolDto.setIndicadorHabilitado(true);

        //Cuando
        RolDto response = subject.crear(rolDto);

        //Entonces
        Assertions.assertEquals(rolDto.getNombre(), response.getNombre());
    }

    @Test
    public void listar() {
        //Dado
        PaginableDTO paginableDTO = new PaginableDTO();
        paginableDTO.setCantidad(2);
        paginableDTO.setPagina(0);

        //Cuando
        List<RolDto> response = subject.listar(paginableDTO);

        //Entonces
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void eliminar() throws RegistroExistenteException {
        //Dado
        RolDto rolDto = new RolDto();
        rolDto.setNombre("Soporte");
        rolDto.setCodigo("SOPORTE");
        rolDto.setIndicadorHabilitado(true);
        RolDto rolAEliminar = subject.crear(rolDto);

        //Cuando
        subject.eliminar(rolAEliminar.getIdRol());

        RolDto response = subject.buscarPorId(rolAEliminar.getIdRol()).orElse(null);

        //Entonces
        assert response != null;
        Assertions.assertFalse(response.getIndicadorHabilitado());
    }

    @Test
    public void actualizar() throws RegistroNoEncontradoException {
        //Dado
        RolDto rolDto = new RolDto();
        rolDto.setIdRol(1);
        rolDto.setNombre("Admin Cambio");
        rolDto.setCodigo("ADMIN");
        rolDto.setIndicadorHabilitado(true);

        //Cuando
        subject.actualizar(rolDto);
        RolDto response = subject.buscarPorCodigo("ADMIN");

        //Entonces
        assert response != null;
        Assertions.assertEquals("Admin Cambio", response.getNombre());
    }

    @Test
    public void buscarPorId() {
        //Dado

        //Cuando
        RolDto response = subject.buscarPorId(2).orElse(null);

        //Entonces
        Assertions.assertNotNull(response);
    }

    @Test
    public void buscarPorCodigo() {
        //Dado

        //Cuando
        RolDto response = subject.buscarPorCodigo("ADMIN");

        //Entonces
        Assertions.assertNotNull(response);
    }
}