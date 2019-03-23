package com.enenim.scaffold.util;

import lombok.Data;

@Data
public class PasswordEncoder {

    private PasswordEncoder bCryptPasswordEncoder = new PasswordEncoder();

    public String encode(String rawPassword){
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword){
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
