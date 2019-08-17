package com.enenim.scaffold.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import static com.enenim.scaffold.constant.CommonConstant.DATE_FORMAT;

public class JsonConverter {

    public static <T> T getObject(Object obj, Class<T> clazz) {
        String json;
        if(obj instanceof String){
            json = (String)obj;
        }else {
            json = getJsonRecursive(obj);
        }
        return getGson().fromJson(json, clazz);
    }

    public static <T> String getJson(T element) {
        return toJson(element);
    }

    public static <T> String getJsonRecursive(T element) {
        return toJson(element);
    }

    public static Gson getGson(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(dateFormat.toPattern());
        gsonBuilder.setLongSerializationPolicy( LongSerializationPolicy.STRING );
        gsonBuilder.registerTypeAdapter(new TypeToken<Map<String, Object>>(){}.getType(),  new MapDeserializerDoubleAsIntFix());
        gsonBuilder.registerTypeAdapter(Date.class, new GsonDateSerializer());
        gsonBuilder.registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
            if(src == src.longValue())
                return new JsonPrimitive(src.longValue());
            return new JsonPrimitive(src);
        });
        return  gsonBuilder.create();
    }

    private static ObjectMapper getConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static String toJson(Object obj) {
        try {
            return getConverter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return getConverter().readValue(json, clazz);
        } catch (IOException e) {
            return null;
        }
    }
}