package com.app.pi.digitalbooking.util.correo;

import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.UsuarioDto;
import com.app.pi.digitalbooking.service.GestionUsuariosService;
import com.app.pi.digitalbooking.service.NotificacionesService;
import com.app.pi.digitalbooking.util.email.EnvioEmailService;
import com.app.pi.digitalbooking.util.email.impl.DatosCorreo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.app.pi.digitalbooking.util.email.impl.DatosCorreo.CONFIRMAR;
import static com.app.pi.digitalbooking.util.email.impl.DatosCorreo.VALIDAR;


@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionesService {

    private final EnvioEmailService enviarMail;
    private final GestionUsuariosService usuariosService;

    @Value("${urlValidarCorreo}")
    private String urlValidarCorreoFront;

    @Override
    public void enviarNotificacion(DatosCorreo codigoCorreo, String correo) throws RegistroNoEncontradoException, MessagingException {

        //Se busca info del usuario por correo
        UsuarioDto usuarioNotificado = usuariosService.buscarUsuarioPorCorreo(correo);

        //Se tranforma a un map
        Map<String, Object> usuarioNotificadoMap = new HashMap<>();
        for (Field field : usuarioNotificado.getPersona().getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                usuarioNotificadoMap.put(field.getName(), field.get(usuarioNotificado.getPersona()));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        if (codigoCorreo.equals(VALIDAR)) {
            String urlEndPointValidacion = urlValidarCorreoFront + "/VerifyUser/"
                    + usuarioNotificado.getIdUsuario() + "/" + usuarioNotificado.getTokenValidacionCorreo();
            usuarioNotificadoMap.put("urlEndPointValidacion", urlEndPointValidacion);
        } else if (codigoCorreo.equals(CONFIRMAR)) {
            String urlEndPointIniciarSesion = urlValidarCorreoFront + "/Login/";
            usuarioNotificadoMap.put("urlEndPointIniciarSesion", urlEndPointIniciarSesion);
        }

        //Se envia el correo
        enviarMail.enviarMail(codigoCorreo, usuarioNotificadoMap);
    }
}
