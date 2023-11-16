package com.app.pi.digitalbooking.service;

import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;

public interface IResumible<R> {
    R resumirInformacion(Integer id) throws RegistroNoEncontradoException;
}
