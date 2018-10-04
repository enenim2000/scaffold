package com.enenim.scaffold.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

@Component
public class JsonTimeSerializer extends JsonSerializer<LocalTime> {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    @Override
    public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        dateFormat.setLenient(true);
        String formattedDate = dateFormat.format(localTime);
        jsonGenerator.writeString(formattedDate);
    }
}