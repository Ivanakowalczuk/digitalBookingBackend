package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.FechasRespuestasDto;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.PeticionFechasDto;
import com.app.pi.digitalbooking.model.dto.ReservaDto;
import com.app.pi.digitalbooking.model.entity.Reserva;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservaServiceImplTest {

    @Autowired
    private ReservaServiceImpl subject;

    @Test
    public void crear() throws RegistroExistenteException, NombreProductoExisteExcepcion, RegistroNoEncontradoException {

        //Dado
        //Producto: (56,3,'Violín','Picasso','14RW',32563.00,1,'Violín de madera sólida con tapa de abeto, fondo y aros de arce.El alquiler incluye funda y arco.',1,0,4.5)
        //Usuario: (2,'lau@correo.edu','$2a$10$hRHFk4BXh2x2Q3P3sUzK5ulebhu1kzQPubfi6pA5Vzy9cPPqaFqFe',9,1,2,0,NULL,NULL)
        ReservaDto reservaDto = new ReservaDto();
        reservaDto.setIdProducto(56);
        reservaDto.setIdUsuario(2);
        reservaDto.setFechaInicioReserva(LocalDate.now());
        reservaDto.setFechaFinReserva(LocalDate.now().plusDays(3));
        reservaDto.setPrecioReserva(new BigDecimal("10.20"));
        //Cuando
        ReservaDto response = subject.crear(reservaDto);

        //Entonces
        Assertions.assertEquals(reservaDto.getIdProducto(), response.getIdProducto());

    }

    @Test
    public void listar() {
        //Dado
        PaginableDTO paginableDTO = new PaginableDTO();
        paginableDTO.setCantidad(2);
        paginableDTO.setPagina(0);

        //Cuando
        List<ReservaDto> response = subject.listar(paginableDTO);

        //Entonces
        Assertions.assertEquals(2, response.size());
    }

    @Test
    @Ignore
    public void eliminar() throws RegistroNoEncontradoException, RegistroExistenteException, NombreProductoExisteExcepcion {

        //Dado
        //Producto: (56,3,'Violín','Picasso','14RW',32563.00,1,'Violín de madera sólida con tapa de abeto, fondo y aros de arce.El alquiler incluye funda y arco.',1,0,4.5)
        //Usuario: (6,'test@correo.com','$2a$10$Uo9pzSGRvTgWrisrAkjD0OLq4G685Y0W2pbiCj4zlDpYOljhvyGMC',13,1,1,0,NULL,NULL)
        ReservaDto reservaDto = new ReservaDto();
        reservaDto.setIdProducto(56);
        reservaDto.setIdUsuario(6);
        reservaDto.setFechaInicioReserva(LocalDate.now().plusDays(8));
        reservaDto.setFechaFinReserva(LocalDate.now().plusDays(16));
        reservaDto.setPrecioReserva(new BigDecimal("10.20"));
        ReservaDto reservaDtoAEliminar = subject.crear(reservaDto);

        //Cuando
        subject.eliminar(reservaDtoAEliminar.getIdReserva());

        ReservaDto response = subject.buscarPorId(reservaDtoAEliminar.getIdReserva()).orElse(null);

        //Entonces
        Assertions.assertNull(response);
    }

    @Test
    @Ignore
    public void actualizar() throws RegistroExistenteException, NombreProductoExisteExcepcion, RegistroNoEncontradoException {
        //Dado
        //Producto: (62,2,'Platillos','SidJia','Zx89',5896.00,1,'Los platillos, platos, latos, címbalos o cimbales son instrumentos de percusión de sonido indeterminado ​, lo que significa que las notas no tienen una altura definida.',0,0,4.0)
        //Usuario: (6,'test@correo.com','$2a$10$Uo9pzSGRvTgWrisrAkjD0OLq4G685Y0W2pbiCj4zlDpYOljhvyGMC',13,1,1,0,NULL,NULL)
        ReservaDto reservaDto = new ReservaDto();
        reservaDto.setIdProducto(62);
        reservaDto.setIdUsuario(6);
        reservaDto.setFechaInicioReserva(LocalDate.now().plusDays(10));
        reservaDto.setFechaFinReserva(LocalDate.now().plusDays(20));
        reservaDto.setPrecioReserva(new BigDecimal("10.20"));
        ReservaDto reservaAModificar = subject.crear(reservaDto);

        reservaAModificar.setPrecioReserva(new BigDecimal("55.55"));

        //Cuando
        subject.actualizar(reservaAModificar);

        ReservaDto response = subject.buscarPorId(reservaAModificar.getIdReserva()).orElse(null);

        //Entonces
        assert response != null;
        Assertions.assertEquals(reservaAModificar.getPrecioReserva(), response.getPrecioReserva());
    }

    @Test
    public void buscarPorId() {
    }

    @Test
    public void buscarUnico() throws RegistroNoEncontradoException {

        //Dado
        // Reserva: (4,57,'2023-07-12','2023-07-24',5689.00,2)

        //Cuando
        ReservaDto reservaDto = subject.buscarUnico(57, 2, LocalDate.of(2023, 7, 12), LocalDate.of(2023, 7, 24)).orElse(null);

        //Entonces
        Assertions.assertNotNull(reservaDto);

    }

    @Test
    public void buscarPorIdUsuario() {
        //Dado
        // Reserva: (4,57,'2023-07-12','2023-07-24',5689.00,2)

        //Cuando
        List<Reserva> reservaDto = subject.buscarPorIdUsuario(2);

        //Entonces
        Assertions.assertEquals(3, reservaDto.size());
    }

    @Test
    public void buscarPorIdProducto() {
        //Dado
        // Reserva: (4,57,'2023-07-12','2023-07-24',5689.00,2)

        //Cuando
        List<ReservaDto> reservaDto = subject.buscarPorIdProducto(57);

        //Entonces
        Assertions.assertEquals(1, reservaDto.size());
    }

    @Test
    public void fechasInhabilitadas() {

        //Dado
        PeticionFechasDto peticionFechasDto = new PeticionFechasDto(57, LocalDate.now(), LocalDate.now().plusDays(3));

        //Cuando
        FechasRespuestasDto response = subject.fechasInhabilitadas(peticionFechasDto);

        //Entonces
        Assertions.assertNotNull(response);

    }
}