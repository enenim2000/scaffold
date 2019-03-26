package com.enenim.scaffold.util;

import com.enenim.scaffold.interfaces.IPasswordEncoder;

public class PasswordEncoder implements IPasswordEncoder {

    private Jargon2PasswordEncoder passwordEncoder = new Jargon2PasswordEncoder();

    @Override
    public String encode(String rawPassword){
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
