package com.enenim.scaffold.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtil {

    public static boolean isEmpty(Object instance, String fieldName){
        Field field = null;
        Long value = null;
        try {
            field = instance.getClass().getField(fieldName);
        } catch (NoSuchFieldException e) {
            try {
                field = instance.getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            }
        }

        try {
            if(!StringUtils.isEmpty(field)) value = (Long) field.get(instance);
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }

        return StringUtils.isEmpty(value);
    }

    public static  Object getFieldValue(Class type, String fieldName){
        Object fromInstance = null;
        try {
            fromInstance = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return getFieldValue(type, fieldName, fromInstance);
    }

    public static Object getFieldValue(Class type, String fieldName, Object instance){
        Field field = null;
        try {
            field = type.getField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            try {
                field = type.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            }
        }

        try {
            if(!StringUtils.isEmpty(field))return field.get(instance);
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return field;
    }

    /**
     *This set the value of before field to entity, here the type of entity is the same as the type of before
     */
    public static Object setFieldValue(Class type, Object entity){
        return setFieldValue(type, entity, entity);
    }

    /**
     *This set the value of before field to entity, here the type of entity may differ from the type of before
     */
    public static Object setFieldValue(Class type, Object fieldValue, Object instance){
        return setFieldValue(type, "before", fieldValue, instance);
    }

    public static Object setFieldValue(Class type, String fieldName, Object fieldValue, Object instance){
        try {
            Field field = type.getField(fieldName);
            field.setAccessible(true);
            field.set(instance, fieldValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {e.printStackTrace();}
        return instance;
    }

    public static Class getGenericType(Class mainType){
        return (Class) ((ParameterizedType)mainType.getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public static Class[] getGenericTypes(Class mainType){
        return (Class[]) ((ParameterizedType)mainType.getGenericSuperclass()).getActualTypeArguments();
    }

    public static Class getGenericInterfaceType(Class mainType){
        Type type = mainType.getGenericInterfaces()[0];
        Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];
        System.out.println(actualType);
        return (Class) (actualType);
    }

    public static Class[] getGenericInterfaceTypes(Class mainType) {
        Type type = mainType.getGenericInterfaces()[0];
        return (Class[]) ((ParameterizedType) type).getActualTypeArguments();
    }

    public static <ObjectType> ObjectType copy(ObjectType anObject, Class<ObjectType> classInfo) {
        Gson gson = new GsonBuilder().create();
        String text = gson.toJson(anObject);
        return gson.fromJson(text, classInfo);
    }

    public static <ObjectType> Object copy(String jsonObj, Class<ObjectType> classInfo) {
        Gson gson = new GsonBuilder().create();
        //String text = gson.toJson(jsonObj);
        return gson.fromJson(jsonObj, classInfo);
    }
}