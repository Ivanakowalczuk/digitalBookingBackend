package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.ProductoSedeDto;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductoSedeServiceImplTest {

    @Autowired
    ProductoSedeServiceImpl subject;

    @Test
    public void crear() throws RegistroExistenteException, NombreProductoExisteExcepcion {
        //Dado
        ProductoSedeDto productoSedeDto = ProductoSedeDto.builder().idProducto(100).idSede(3).indicadorHabilitado(true).build();
        //Cuando
        ProductoSedeDto response = subject.crear(productoSedeDto);

        //Entonces
        Assertions.assertNotNull(response);
    }

    @Test
    public void listar() {
        //Dado
        PaginableDTO paginableDTO = new PaginableDTO();
        paginableDTO.setCantidad(2);
        paginableDTO.setPagina(0);

        //Cuando
        List<ProductoSedeDto> response = subject.listar(paginableDTO);

        //Entonces
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void eliminar() throws RegistroNoEncontradoException {
        //Dado


        //Cuando
        subject.eliminar(6);
        ProductoSedeDto response = subject.buscarPorId(6).orElse(null);

        //Entonces
        assert response != null;
        Assertions.assertFalse(response.getIndicadorHabilitado());
    }

    @Test
    public void buscarPorId() throws RegistroNoEncontradoException {
        //Dado

        //Cuando
        ProductoSedeDto response = subject.buscarPorId(6).orElse(null);

        //Entonces
        assert response != null;
        Assertions.assertEquals(56, response.getIdProducto());
    }

    @Test
    public void buscarUnico() throws RegistroNoEncontradoException {
        //Dado

        //Cuando
        ProductoSedeDto response = subject.buscarUnico(57,2);

        //Entonces
        Assertions.assertNotNull(response);
    }
}