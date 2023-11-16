package com.app.pi.digitalbooking.service;

import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.util.email.impl.DatosCorreo;

import javax.mail.MessagingException;

public interface NotificacionesService {
    void enviarNotificacion(DatosCorreo codigo, String correo) throws RegistroNoEncontradoException, MessagingException;
}
