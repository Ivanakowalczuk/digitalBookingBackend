package com.app.pi.digitalbooking.controller;

import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.service.NotificacionesService;
import com.app.pi.digitalbooking.util.email.impl.DatosCorreo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionesService notificacionesService;


    @GetMapping("/correo/{correo}/codigo/{datosCorreo}")
    public void enviarCorreo(@PathVariable DatosCorreo datosCorreo, @PathVariable String correo) throws RegistroNoEncontradoException, MessagingException {
        notificacionesService.enviarNotificacion(datosCorreo, correo);
    }
}
