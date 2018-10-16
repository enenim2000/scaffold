package com.enenim.scaffold.interfaces;

public interface IMailService {
    String TEMPLATE_TRANSACTION = "mail/transaction-template";
    String TEMPLATE_EXCEPTION = "mail/exception-template";
    String TEMPLATE_SIGNUP = "mail/signup-template";

    void send(Object obj);
}