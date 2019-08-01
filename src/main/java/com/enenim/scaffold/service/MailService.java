package com.enenim.scaffold.service;

import com.enenim.scaffold.shared.Mail;
import com.enenim.scaffold.util.message.SpringMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class MailService {

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;

    @Autowired
    public MailService(@Qualifier("springTemplateEngine") SpringTemplateEngine templateEngine, JavaMailSender emailSender) {
        this.templateEngine = templateEngine;
        this.emailSender = emailSender;
    }

    public void sendMail(Mail mail){
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            //helper.addAttachment("logo.png", new ClassPathResource("static/img/billspay.png"));

            mail.setData(mail.getData());

            Context context = new Context();
            context.setVariables(mail.getData());
            String html = templateEngine.process(mail.getTemplate(), context);

            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(SpringMessage.msg("spring.mail.username"));
            //helper.setFrom(mail.getFrom());

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        emailSender.send(message);
    }

}