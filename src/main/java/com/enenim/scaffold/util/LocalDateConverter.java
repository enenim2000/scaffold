package com.enenim.scaffold.util;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@ConfigurationPropertiesBinding
public class LocalDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        LocalDate date = LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return Date.valueOf(date);
    }
}