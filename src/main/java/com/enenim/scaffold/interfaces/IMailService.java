package com.enenim.scaffold.interfaces;

public interface IMailService {

    String TEMPLATE_TRANSACTION = "mail/transaction-template";
    String TEMPLATE_EXCEPTION = "mail/exception-template";
    String TEMPLATE_SIGNUP = "mail/signup-template";
    String TEMPLATE_SIGNUP_BILLER = "mail/signup-vendor-template";

    void send(Object obj);

}