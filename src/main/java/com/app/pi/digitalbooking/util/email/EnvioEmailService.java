package com.app.pi.digitalbooking.util.email;

import com.app.pi.digitalbooking.util.email.impl.DatosCorreo;

import javax.mail.MessagingException;
import java.io.File;
import java.util.Map;

public interface EnvioEmailService {
    void enviarMail(DatosCorreo datosCorreo, Map<String, Object> persona) throws MessagingException;
    void enviarEmailConAdjunto(String destinatario, String asunto, String mensaje, File adjunto) throws MessagingException;
}
