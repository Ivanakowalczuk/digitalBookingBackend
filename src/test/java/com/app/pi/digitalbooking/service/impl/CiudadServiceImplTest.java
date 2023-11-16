package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.CiudadDto;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.repository.CiudadRepository;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CiudadServiceImplTest {

    @Autowired
    private CiudadRepository repository;
    private CiudadServiceImpl subject;

    @Before
    public void setUp() {
        subject = new CiudadServiceImpl(repository, new MapperObject());
    }

    @Test
    public void crearCiudadDeFormaExitosa() throws RegistroExistenteException, NombreProductoExisteExcepcion {

        //Dado
        CiudadDto ciudadDto = new CiudadDto();
        ciudadDto.setCiudad("Bogota");
        ciudadDto.setIndicadorHabilitado(true);
        ciudadDto.setIdProvincia(20);

        //Cuando
        CiudadDto response = subject.crear(ciudadDto);

        //Entonces
        Assertions.assertEquals(ciudadDto.getCiudad(), response.getCiudad());

    }

    @Test
    public void listarCiudadesDeFormaExitosa() {

        //Dado
        PaginableDTO paginableDTO = new PaginableDTO();
        paginableDTO.setCantidad(2);
        paginableDTO.setPagina(0);

        //Cuando
        List<CiudadDto> response = subject.listar(paginableDTO);

        //Entonces
        Assertions.assertEquals(2, response.size());

    }

    @Test
    public void eliminarCategoria() throws RegistroExistenteException, NombreProductoExisteExcepcion, RegistroNoEncontradoException {

        //Dado
        CiudadDto ciudadDto = new CiudadDto();
        ciudadDto.setCiudad("Bogota");
        ciudadDto.setIndicadorHabilitado(true);
        ciudadDto.setIdProvincia(20);

        //Cuando
        CiudadDto ciudad = subject.crear(ciudadDto);
        subject.eliminar(ciudad.getIdCiudad());

        CiudadDto response = subject.buscarPorId(ciudad.getIdCiudad()).orElse(null);

        //Entonces
        assert response != null;
        Assertions.assertFalse(response.getIndicadorHabilitado());

    }

    @Test
    public void buscarCategoriaPorId() throws RegistroNoEncontradoException, RegistroExistenteException, NombreProductoExisteExcepcion {

        //Dado
        CiudadDto ciudadDto = new CiudadDto();
        ciudadDto.setCiudad("Buscada");
        ciudadDto.setIndicadorHabilitado(true);
        ciudadDto.setIdProvincia(20);

        //Cuando
        CiudadDto ciudad = subject.crear(ciudadDto);
        CiudadDto response = subject.buscarPorId(ciudad.getIdCiudad()).orElse(null);

        //Entonces
        assert response != null;
        Assertions.assertEquals(ciudadDto.getCiudad(), response.getCiudad());
    }

}