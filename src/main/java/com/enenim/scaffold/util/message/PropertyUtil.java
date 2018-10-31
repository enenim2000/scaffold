package com.enenim.scaffold.util.message;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class PropertyUtil {

    @Value("${lang}")
    private static String lang;

    private static Properties getProperties(String fileName){
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
            if(input==null){
                System.out.println("Sorry, unable getTo find " + fileName);
                return null;
            }
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return prop;
    }

    static String msg(String lang, String key, String fileName){
        Properties property;
        String value;
        property = PropertyUtil.getProperties(fileName + "_" + lang + ".properties");
        if(property != null){
            value = property.getProperty(key);
            if(value != null) {
                return value;
            }
        }
        return "Message not found";
    }

    static String msg(String key){
        Properties property;
        String value;
        property = PropertyUtil.getProperties("application.properties");
        if(property != null){
            String env = property.getProperty("spring.profiles.active");
            if("dev".equalsIgnoreCase(env)){
                property = PropertyUtil.getProperties("application-dev.properties");
            }else if("production".equalsIgnoreCase(env)){
                property = PropertyUtil.getProperties("application-production.properties");
            }
        }

        if(property != null){
            value = property.getProperty(key);
            if(value != null){
                return value;
            }
        }
        return "Message not found";
    }
}

