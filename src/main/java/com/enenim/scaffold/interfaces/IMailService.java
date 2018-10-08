package com.enenim.scaffold.interfaces;

public interface IMailService {
    String TEMPLATE_TRANSACTION = "transaction-template";
    String TEMPLATE_EXCEPTION = "exception-template";

    void send(Object obj);
}