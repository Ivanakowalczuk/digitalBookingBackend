package com.app.pi.digitalbooking.service;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, I> {
    T crear(T t) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException;
    List<T> listar(PaginableDTO pageable);
    void eliminar(I id);
    T actualizar(T t) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException;
    Optional<T> buscarPorId(I id) throws RegistroNoEncontradoException;
}
