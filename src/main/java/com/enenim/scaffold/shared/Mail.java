package com.enenim.scaffold.shared;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class Mail {
 
    private String from;
 
    private String to;
 
    private String cc;
 
    private String bcc;
 
    private String subject;

    private String template;

    private String content;

    private String contentType;
 
    private List< Object > attachments;
 
    private HashMap< String, Object > data = new HashMap<>();
 
    public Mail() {
        contentType = "text/plain";
    }
}