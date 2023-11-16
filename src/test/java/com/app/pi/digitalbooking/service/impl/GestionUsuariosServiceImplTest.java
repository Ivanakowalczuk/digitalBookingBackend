package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GestionUsuariosServiceImplTest {

    @Autowired
    GestionUsuariosServiceImpl subject;

    @Test
    public void listar() {
        //Dado
        PaginableDTO paginableDTO = new PaginableDTO();
        paginableDTO.setCantidad(2);
        paginableDTO.setPagina(0);

        //Cuando
        List<UsuarioDto> response = subject.listar(paginableDTO);

        //Entonces
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void registrarUsuarioDeFormaExitosa() throws RegistroExistenteException, RegistroNoEncontradoException {

        //Dado
        RegistroDto registroDto = new RegistroDto();
        registroDto.setUsuario("test@local.com");
        registroDto.setCorreo("test@local.com");
        registroDto.setIndicadorHabilitado(true);
        registroDto.setRol(2);
        registroDto.setContrasenia("12345678");
        registroDto.setNombre("Local");
        registroDto.setApellido("Test");
        //Cuando
        UsuarioDto response = subject.crear(registroDto);

        //Entonces
        Assertions.assertEquals(registroDto.getUsuario(), response.getUsuario());
    }

    @Test
    public void buscarPorId() throws RegistroExistenteException, RegistroNoEncontradoException {

        //Dado
        RegistroDto registroDto = new RegistroDto();
        registroDto.setUsuario("test1@local.com");
        registroDto.setCorreo("test1@local.com");
        registroDto.setIndicadorHabilitado(true);
        registroDto.setRol(2);
        registroDto.setContrasenia("12345678");
        registroDto.setNombre("Local");
        registroDto.setApellido("Test 1");
        UsuarioDto usuario = subject.crear(registroDto);

        //Cuando
        UsuarioDto response = subject.buscarPorId(usuario.getIdUsuario()).orElse(null);

        //Entonces
        assert response != null;
        Assertions.assertEquals(registroDto.getUsuario(), response.getUsuario());
    }

    @Test
    public void actualizarUsuario() throws RegistroExistenteException, RegistroNoEncontradoException {
        //Dado
        RegistroDto registroDto = new RegistroDto();
        registroDto.setUsuario("test2@local.com");
        registroDto.setCorreo("test2@local.com");
        registroDto.setIndicadorHabilitado(true);
        registroDto.setRol(2);
        registroDto.setContrasenia("12345678");
        registroDto.setNombre("Local");
        registroDto.setApellido("Test 2");
        UsuarioDto usuario = subject.crear(registroDto);

        //Cuando
        registroDto.setRol(1);
        UsuarioDto response = subject.actualizar(usuario.getIdUsuario(), registroDto);

        //Entonces
        assert response != null;
        Assertions.assertEquals("ADMIN", response.getRol().getCodigo());
    }

    @Test
    public void buscarUsuarioPorCorreo() throws RegistroExistenteException, RegistroNoEncontradoException {
        //Dado
        RegistroDto registroDto = new RegistroDto();
        registroDto.setUsuario("test3@local.com");
        registroDto.setCorreo("test3@local.com");
        registroDto.setIndicadorHabilitado(true);
        registroDto.setRol(2);
        registroDto.setContrasenia("12345678");
        registroDto.setNombre("Local");
        registroDto.setApellido("Test 3");
        UsuarioDto usuario = subject.crear(registroDto);

        //Cuando
        UsuarioDto response = subject.buscarUsuarioPorCorreo(registroDto.getCorreo());

        //Entonces
        assert response != null;
        Assertions.assertEquals(registroDto.getCorreo(), response.getUsuario());
    }
}