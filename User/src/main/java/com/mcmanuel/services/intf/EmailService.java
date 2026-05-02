package com.mcmanuel.services.intf;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;

import static java.nio.charset.StandardCharsets.UTF_8;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine engine;


    public void sendEmail(String email,String name,String templateName,String code,String subject,String url) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED,UTF_8.name());

        Context context = new Context();

        HashMap<String,Object> properties = new HashMap<>();
        properties.put("username",name);
        properties.put("code",code);
        properties.put("url",url);

        context.setVariables(properties);


        helper.setFrom("mcmanuel755@gmail,com");
        helper.setTo(email);
        helper.setSubject(subject);

        String template = engine.process(templateName,context);

        helper.setText(template,true);

        mailSender.send(message);
    }

}
