package com.enenim.scaffold.interfaces;

public interface IPasswordEncoder {

    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
