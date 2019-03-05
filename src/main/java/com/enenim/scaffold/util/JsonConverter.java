package com.enenim.scaffold.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;

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
        return getGson().toJson(element);
    }

    public static <T> String getJsonRecursive(T element) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPropertyInclusion(
                JsonInclude.Value.construct(JsonInclude.Include.ALWAYS, JsonInclude.Include.NON_NULL));
        try {
            return mapper.writeValueAsString(element);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
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
}