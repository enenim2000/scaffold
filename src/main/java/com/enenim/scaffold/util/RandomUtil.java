package com.enenim.scaffold.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.Random;

public class RandomUtil {

    private static String getCharacters(int length) {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public static String getCode(){
        return getCharacters(10).toUpperCase();
    }

    public static String getUniqueCharacters(){

        String unique = new BCryptPasswordEncoder().encode(new Date() + RandomUtil.getCharacters());
        unique = unique.replace("/", "")
        .replace("{", "")
        .replace("}", "")
        .replace("(", "")
        .replace(")", "")
        .replace(":", "")
        .replace("-", "")
        .replace(";", "")
        .replace(".", "")
        .replace("*", "")
        .replace("^", "")
        .replace("%", "")
        .replace("$", "")
        .replace("#", "")
        .replace("@", "")
        .replace("!", "")
        .replace(",", "")
        .replace("?", "")
        .replace("<", "")
        .replace(">", "");
        return unique;

    }

    private static String getCharacters(){
        return getCharacters(20);
    }

}
