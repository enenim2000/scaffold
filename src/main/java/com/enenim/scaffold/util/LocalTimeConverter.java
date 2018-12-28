package com.enenim.scaffold.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
@ConfigurationPropertiesBinding
public class LocalTimeConverter implements Converter<String, Time> {

    @NotNull
    @Override
    public Time convert(@NotNull String source) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long ms = 0;
        try {
            ms = sdf.parse(source).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Time(ms);
    }
}