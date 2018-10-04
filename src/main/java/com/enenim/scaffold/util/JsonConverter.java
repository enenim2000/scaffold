package com.enenim.scaffold.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class JsonConverter {
    public static <T> T getElement(String value, Class<T> clazz) {
        return getGson().fromJson(value, clazz);
    }

    public static <T> T[] getElements(String value, Class<T[]> clazz) {
        return getGson().fromJson(value, clazz);
    }

    public static <T> List<T> getList(String value, Class<List<T>> clazz) {
        return getGson().fromJson(value, clazz);
    }

    public static <T> String getJson(T element) {
        return getGson().toJson(element);
    }

    public static <T> String getJsonRecursive(T element) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString;
        try {
            jsonInString = mapper.writeValueAsString(element);
            return jsonInString;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Gson getGson(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(dateFormat.toPattern());
        gsonBuilder.registerTypeAdapter(new TypeToken<Map<String, Object>>(){}.getType(),  new MapDeserializerDoubleAsIntFix());
        return  gsonBuilder.create();
    }
}