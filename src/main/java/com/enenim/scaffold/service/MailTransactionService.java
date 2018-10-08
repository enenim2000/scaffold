package com.enenim.scaffold.service;

import com.enenim.scaffold.interfaces.IMailService;
import com.enenim.scaffold.shared.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailTransactionService implements IMailService {
    private final MailService mailService;

    @Autowired
    public MailTransactionService(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void send(Object obj){
        Mail mail = new Mail();
        mail.setTo("enenim2000@yahoo.com,basso4real2000@gmail.com");
        mail.setFrom("no-reply@glocurex.com");
        mail.setSubject("Successful Transaction");
        mail.setTemplate(TEMPLATE_TRANSACTION);
        mailService.sendMail(mail);
    }
}