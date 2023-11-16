package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.ProductoDto;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductoServiceImplTest {

    @Autowired
    ProductoServiceImpl subject;

    @Test
    public void crearProductoDeFormaExitosa() throws NombreProductoExisteExcepcion {
        //Dado
        ProductoDto productoDto = new ProductoDto();
        productoDto.setNombre("Producto Test");
        productoDto.setDescripcion("Producto de pruebas locales");
        productoDto.setIndicadorHabilitado(true);
        productoDto.setEsElectrico(false);
        productoDto.setMarca("Test");
        productoDto.setModelo("Test");
        productoDto.setIdCategoria(1);
        productoDto.setTieneProtector(true);
        productoDto.setPromedio(0.0);
        productoDto.setPrecio(new BigDecimal("10.80"));
        productoDto.setMarca("Test");
        productoDto.setListaProductosImagenes(new ArrayList<>());
        productoDto.setListaIdsSedes(new ArrayList<>());
        productoDto.setListasIdsImagen(new ArrayList<>());


        //Cuando
        ProductoDto response = subject.crear(productoDto);

        //Entonces
        Assertions.assertEquals(productoDto.getNombre(), response.getNombre());
    }

    @Test(expected = NombreProductoExisteExcepcion.class)
    public void crearProductoDuplicado() throws NombreProductoExisteExcepcion {
        //Dado
        ProductoDto productoDto = new ProductoDto();
        productoDto.setNombre("Producto Test");
        productoDto.setDescripcion("Producto de pruebas locales");
        productoDto.setIndicadorHabilitado(true);
        productoDto.setEsElectrico(false);
        productoDto.setMarca("Test");
        productoDto.setModelo("Test");
        productoDto.setIdCategoria(1);
        productoDto.setTieneProtector(true);
        productoDto.setPromedio(0.0);
        productoDto.setPrecio(new BigDecimal("10.80"));
        productoDto.setMarca("Test");
        productoDto.setListaProductosImagenes(new ArrayList<>());
        productoDto.setListaIdsSedes(new ArrayList<>());
        productoDto.setListasIdsImagen(new ArrayList<>());

        //Cuando
        ProductoDto response = subject.crear(productoDto);

        //Entonces: Se desencadena excepcion
    }

    @Test
    public void listarProductosDeFormaExitosa() {

        //Dado
        PaginableDTO paginableDTO = new PaginableDTO();
        paginableDTO.setCantidad(2);
        paginableDTO.setPagina(0);

        //Cuando
        List<ProductoDto> response = subject.listar(paginableDTO);

        //Entonces
        Assertions.assertEquals(2, response.size());

    }

    @Test
    public void buscarPorId() throws RegistroNoEncontradoException {
        //Dado
        //(70,1,'Micrófono  unidireccional','Carto','XLR',12456.00,1,'Micrófono con condensador, frecuencia 20-20kHz, patrón cardioide  y sensibilidad de 1Hz a 1 kOhm',1,1,4.0)

        //Cuando
        ProductoDto response = subject.buscarPorId(70).orElse(null);

        //Entonces
        assert response != null;
        Assertions.assertEquals("Micrófono  unidireccional", response.getNombre());
        Assertions.assertEquals("Carto", response.getMarca());
        Assertions.assertEquals(new BigDecimal("12456.00"), response.getPrecio());

    }

    @Test
    public void buscarPorNombre() throws RegistroNoEncontradoException {
        //Dado
        //(70,1,'Micrófono  unidireccional','Carto','XLR',12456.00,1,'Micrófono con condensador, frecuencia 20-20kHz, patrón cardioide  y sensibilidad de 1Hz a 1 kOhm',1,1,4.0)

        //Cuando
        ProductoDto response = subject.buscarPorNombre("Micrófono  unidireccional").orElse(null);

        //Entonces
        assert response != null;
        Assertions.assertEquals(70, response.getIdProducto());
        Assertions.assertEquals("Carto", response.getMarca());
        Assertions.assertEquals(new BigDecimal("12456.00"), response.getPrecio());

    }

    @Test
    public void buscarPorCategoriaPaginable() {

        //Dado
        PaginableDTO paginableDTO = new PaginableDTO();
        paginableDTO.setCantidad(2);
        paginableDTO.setPagina(0);

        //Cuando
        List<ProductoDto> response = subject.buscarPorCategoriaPaginable(1, paginableDTO);

        //Entonces
        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals("AUDIO", response.get(0).getCategoria().getCodigo());

    }

    @Test
    public void buscarAleatoriamente() {

        //Dado
        PaginableDTO paginableDTO = new PaginableDTO();
        paginableDTO.setCantidad(2);
        paginableDTO.setPagina(0);

        //Cuando
        List<ProductoDto> response = subject.buscarAleatoriamente(paginableDTO);

        //Entonces
        Assertions.assertEquals(2, response.size());

    }

    @Test
    public void buscarPorSede() {

        //Dado
        PaginableDTO paginableDTO = new PaginableDTO();
        paginableDTO.setCantidad(2);
        paginableDTO.setPagina(0);

        //Cuando
        List<ProductoDto> response = subject.buscarPorSede(paginableDTO, 3);

        //Entonces
        Assertions.assertFalse(response.isEmpty());

    }

}