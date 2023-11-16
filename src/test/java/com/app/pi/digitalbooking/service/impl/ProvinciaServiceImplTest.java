package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.ProvinciaDto;
import com.app.pi.digitalbooking.repository.ProvinciaRepository;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProvinciaServiceImplTest {

    @Autowired
    private ProvinciaRepository repository;
    private ProvinciaServiceImpl subject;

    @Before
    public void setUp() {
        subject = new ProvinciaServiceImpl(repository, new MapperObject());
    }


    @Test
    public void crear() throws RegistroExistenteException, NombreProductoExisteExcepcion {
        //Dado
        ProvinciaDto provinciaDto = new ProvinciaDto();
        provinciaDto.setProvincia("Provincia Test");
        provinciaDto.setIndicadorHabilitado(true);

        //Cuando
        ProvinciaDto response = subject.crear(provinciaDto);

        //Entonces
        Assertions.assertEquals(provinciaDto.getProvincia(), response.getProvincia());

    }

    @Test
    public void listar() {
        //Dado
        PaginableDTO paginableDTO = new PaginableDTO();
        paginableDTO.setCantidad(2);
        paginableDTO.setPagina(0);

        //Cuando
        List<ProvinciaDto> response = subject.listar(paginableDTO);

        //Entonces
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void eliminar() throws RegistroNoEncontradoException, RegistroExistenteException, NombreProductoExisteExcepcion {
        //Dado
        ProvinciaDto provinciaDto = new ProvinciaDto();
        provinciaDto.setProvincia("Provincia Test 2");
        provinciaDto.setIndicadorHabilitado(true);

        //Cuando
        ProvinciaDto provinciaCreada = subject.crear(provinciaDto);
        subject.eliminar(provinciaCreada.getIdProvincia());

        ProvinciaDto response = subject.buscarPorId(provinciaCreada.getIdProvincia()).orElse(null);

        //Entonces
        assert response != null;
        Assertions.assertFalse(response.getIndicadorHabilitado());

    }

    @Test
    public void buscarPorId() throws RegistroExistenteException, NombreProductoExisteExcepcion, RegistroNoEncontradoException {
        //Dado
        ProvinciaDto provinciaDto = new ProvinciaDto();
        provinciaDto.setProvincia("Provincia Test 3");
        provinciaDto.setIndicadorHabilitado(true);
        ProvinciaDto provinciaCreada = subject.crear(provinciaDto);

        //Cuando
        ProvinciaDto response = subject.buscarPorId(provinciaCreada.getIdProvincia()).orElse(null);

        //Entonces
        Assertions.assertNotNull(response);
    }
}