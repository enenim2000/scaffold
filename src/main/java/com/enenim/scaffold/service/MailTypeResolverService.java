package com.enenim.scaffold.service;

import com.enenim.scaffold.interfaces.IMailService;
import com.enenim.scaffold.model.dao.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailTypeResolverService {

    private final MailExceptionService mailExceptionService;
    private final MailTransactionService mailTransactionService;

    @Autowired
    public MailTypeResolverService(MailExceptionService mailExceptionService, MailTransactionService mailTransactionService) {
        this.mailExceptionService = mailExceptionService;
        this.mailTransactionService = mailTransactionService;
    }

    public void send(Object obj){
        IMailService mailService;

        if(obj instanceof Exception){
            mailService = mailExceptionService;
        }else if(obj instanceof Transaction){
            mailService = mailTransactionService;
        }else {
            return;
        }

        mailService.send(obj);
    }
}