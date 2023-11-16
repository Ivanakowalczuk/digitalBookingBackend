package com.app.pi.digitalbooking.util.email.impl;

import com.app.pi.digitalbooking.util.email.EnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EnvioEmailServiceImpl implements EnvioEmailService {

    private final JavaMailSender envioEmail;
    private final SpringTemplateEngine templateEngine;

    @Value("${email.sender.acount}")
    private String cuentaEnvioEmail;

    @Override
    public void enviarMail(DatosCorreo datosCorreo, Map<String, Object> persona) throws MessagingException {
        MimeMessage email = envioEmail.createMimeMessage();
        MimeMessageHelper emailHelper = new MimeMessageHelper(email,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariables(persona);
        final String template = datosCorreo.getPlantilla();
        String correoFormatoHtml = templateEngine.process(template, context);

        emailHelper.setFrom(cuentaEnvioEmail);
        emailHelper.setTo(persona.get("correo").toString());
        emailHelper.setSubject(datosCorreo.getAsunto());
        emailHelper.setText(correoFormatoHtml, true);
        envioEmail.send(email);
    }

    @Override
    public void enviarEmailConAdjunto(String destinatario, String asunto, String mensaje, File adjunto) throws MessagingException {
        MimeMessage mimeMessage = envioEmail.createMimeMessage();
        MimeMessageHelper emailConAdjunto = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
        emailConAdjunto.setFrom(cuentaEnvioEmail);
        emailConAdjunto.setTo(destinatario);
        emailConAdjunto.setSubject(asunto);
        emailConAdjunto.setText(mensaje);
        emailConAdjunto.addAttachment(adjunto.getName(), adjunto);
        envioEmail.send(mimeMessage);
    }
}
