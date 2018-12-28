package com.enenim.scaffold.shared;

import lombok.Data;

@Data
public class Channel {
    private String key;
    private String app;
    private String code;
    private String value;
    public Channel(String code, String app){
        setCode(code);
        setApp(app);
    }
}