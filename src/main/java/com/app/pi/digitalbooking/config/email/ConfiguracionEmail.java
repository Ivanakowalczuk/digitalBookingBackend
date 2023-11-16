package com.app.pi.digitalbooking.config.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Configuration
public class ConfiguracionEmail {

    @Value("${email.sender.acount}")
    private String cuentaEnvioEmail;
    @Value("${email.sender.password}")
    private String contraseniaPass;
    @Value("${email.sender.host}")
    private String host;
    @Value("${email.sender.port}")
    private String port;
    @Value("${mail.transport.protocol}")
    private String protocolo;
    @Value("${mail.smtp.auth}")
    private Boolean salida;
    @Value("${mail.smtp.starttls.enable}")
    private Boolean starttls;
    @Value("${mail.debug}")
    private Boolean debug;

    @Bean
    public JavaMailSender getJavaMailSender() {

        JavaMailSenderImpl envioEmail = new JavaMailSenderImpl();
        envioEmail.setHost(host);
        envioEmail.setPort(Integer.parseInt(port));
        envioEmail.setUsername(cuentaEnvioEmail);
        envioEmail.setPassword(contraseniaPass);

        Properties prop = envioEmail.getJavaMailProperties();
        prop.put("mail.transport.protocol", protocolo);
        prop.put("mail.smtp.starttls.enable", starttls);
        prop.put("mail.smtp.auth", salida);
        prop.put("mail.debug", debug);
        prop.put("mail.session", true);

        return envioEmail;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver htmlTemplateResolver(){
        SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();
        emailTemplateResolver.setPrefix("classpath:/templates/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return emailTemplateResolver;
    }
}
