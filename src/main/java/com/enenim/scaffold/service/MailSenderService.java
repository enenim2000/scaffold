package com.enenim.scaffold.service;

import com.enenim.scaffold.interfaces.IMailService;
import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.model.dao.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    private final MailExceptionService mailExceptionService;
    private final MailSignUpService mailSignUpService;
    private final MailTransactionService mailTransactionService;

    @Autowired
    public MailSenderService(MailExceptionService mailExceptionService, MailSignUpService mailSignUpService, MailTransactionService mailTransactionService) {
        this.mailExceptionService = mailExceptionService;
        this.mailSignUpService = mailSignUpService;
        this.mailTransactionService = mailTransactionService;
    }

    public void send(Object obj){
        IMailService mailService;

        if(obj instanceof Exception){
            mailService = mailExceptionService;
        }else if(obj instanceof Transaction){
            mailService = mailTransactionService;
        }else if(obj instanceof Consumer){
            mailService = mailSignUpService;
        }else {
            return;
        }

        mailService.send(obj);
    }
}