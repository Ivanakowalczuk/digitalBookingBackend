package com.app.pi.digitalbooking.service;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.RegistroDto;
import com.app.pi.digitalbooking.model.dto.UsuarioDto;

public interface GestionUsuariosService extends CrudService<UsuarioDto, Integer> {

   UsuarioDto crear(RegistroDto registroDto) throws RegistroExistenteException, RegistroNoEncontradoException;
    UsuarioDto actualizar(Integer id, RegistroDto registroDto) throws RegistroNoEncontradoException;
    UsuarioDto buscarUsuarioPorCorreo(String correo) throws RegistroNoEncontradoException;
}
