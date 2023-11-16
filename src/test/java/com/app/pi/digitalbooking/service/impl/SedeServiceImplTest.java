package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.RolDto;
import com.app.pi.digitalbooking.model.dto.SedeDto;
import com.app.pi.digitalbooking.model.entity.Sede;
import com.app.pi.digitalbooking.repository.RolRepository;
import com.app.pi.digitalbooking.repository.SedeRepository;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SedeServiceImplTest {

    @Autowired
    private SedeRepository repository;
    private SedeServiceImpl subject;

    @Before
    public void setUp() {
        subject = new SedeServiceImpl(repository, new MapperObject());
    }


    @Test
    public void crear() throws RegistroExistenteException, NombreProductoExisteExcepcion {
        //Dado
        SedeDto sedeDto = new SedeDto();
        sedeDto.setSede("Sede Prueba");
        sedeDto.setIdProvincia(20); // Cordoba
        sedeDto.setIdCiudad(16); // Cordoba
        sedeDto.setDireccion("Direccion");
        sedeDto.setLatitud("Latitud");
        sedeDto.setLongitud("Longitud");
        sedeDto.setIndicadorHabilitado(true);

        //Cuando
        SedeDto response = subject.crear(sedeDto);

        //Entonces
        Assertions.assertEquals(sedeDto.getSede(), response.getSede());
    }

    @Test
    public void listar() {
        //Dado
        PaginableDTO paginableDTO = new PaginableDTO();
        paginableDTO.setCantidad(2);
        paginableDTO.setPagina(0);

        //Cuando
        List<SedeDto> response = subject.listar(paginableDTO);

        //Entonces
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void eliminar() throws RegistroExistenteException, NombreProductoExisteExcepcion, RegistroNoEncontradoException {
        //Dado
        SedeDto sedeDto = new SedeDto();
        sedeDto.setSede("Sede Prueba 2");
        sedeDto.setIdProvincia(20); // Cordoba
        sedeDto.setIdCiudad(16); // Cordoba
        sedeDto.setDireccion("Direccion");
        sedeDto.setLatitud("Latitud");
        sedeDto.setLongitud("Longitud");
        sedeDto.setIndicadorHabilitado(true);
        SedeDto sedeAEliminar = subject.crear(sedeDto);

        //Cuando
        subject.eliminar(sedeAEliminar.getIdSede());
        SedeDto response = subject.buscarPorId(sedeAEliminar.getIdSede()).orElse(null);

        //Entonces
        assert response != null;
        Assertions.assertFalse(response.getIndicadorHabilitado());
    }

    @Test
    public void buscarPorId() throws RegistroNoEncontradoException {
        //Dado
        // (1,'La Floresta',27,19,'Entre Ríos 172','-32.88587220000001','-68.83571479999999',1)


        //Cuando
        SedeDto response = subject.buscarPorId(1).orElse(null);

        //Entonces
        Assertions.assertNotNull(response);
    }

    @Test
    public void buscarUnico() {
        //Dado
        // (1,'La Floresta',27,19,'Entre Ríos 172','-32.88587220000001','-68.83571479999999',1)

        //Cuando
        SedeDto response = subject.buscarUnico("La Floresta", 27, 19);

        //Entonces
        Assertions.assertNotNull(response);
    }

    @Test
    public void buscarTodosPorIdCiudad() {

        //Dado
        PaginableDTO paginableDTO = new PaginableDTO();
        paginableDTO.setCantidad(2);
        paginableDTO.setPagina(0);

        //Cuando
        List<SedeDto> response = subject.buscarTodosPorIdCiudad(19, paginableDTO);

        //Entonces
        Assertions.assertFalse(response.isEmpty());
    }
}