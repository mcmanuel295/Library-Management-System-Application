package com.mcmanuel.domain.email;

import static java.nio.charset.StandardCharsets.UTF_8;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
     private final JavaMailSender mailSender;
    private final TemplateEngine engine;

    @Async
    public void sendEmail(String email, String name, String templateName, String code, String subject, String url)
            throws MessagingException {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED, UTF_8.name());

            Context context = new Context();

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("username", name);
            properties.put("code", code);
            properties.put("url", url);

            context.setVariables(properties);

            helper.setFrom("mcmanuel755@gmail.com");
            helper.setTo(email);
            helper.setSubject(subject);

            String template = engine.process(templateName, context);

            helper.setText(template, true);

            mailSender.send(message);
        }
        catch (MessagingException ex){
            log.error(ex.getMessage());
            throw new MessagingException(ex.getMessage());
        }
    }
}
