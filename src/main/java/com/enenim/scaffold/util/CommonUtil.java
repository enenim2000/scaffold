package com.enenim.scaffold.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

public class CommonUtil {

    public static String getCode(){
        StringBuilder code = new StringBuilder();
        int numberOfDigits = 6;

        ArrayList<Integer> list = new ArrayList<>();

        for (int i=1; i<11; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        for (int i=0; i<numberOfDigits; i++) {
            code.append(list.get(i));
        }

        return code.toString().substring(0, numberOfDigits);
    }

    public static <T> List<T> intersect(Collection<T> listA,Collection<T> listB){

        List<T> newList = new ArrayList<>();
        for(T item : listA){
            if(listB.contains(item)){
                newList.add(item);
            }
        }
        return newList;
    }

    public static <T> List<T> difference(Collection<T> listA,Collection<T> listB){
        List<T> newList = new ArrayList<>();
        for(T item : listB){
            if(!listA.contains(item)){
                newList.add(item);
            }
        }
        return newList;
    }

    public static <K, V> List<K> difference(Map<K, V> mapA, Map<K, V> mapB){
        List<K> newList = new ArrayList<>();
        for(Map.Entry<K, V> entry : mapA.entrySet()){
            if(!mapB.containsKey(entry.getKey())){
                newList.add(entry.getKey());
            }
        }
        return newList;
    }

    public static <T> Collection<T> union(Collection<T> listA,Collection<T> listB){

        for(T item : listA){
            if(!listB.contains(item)){
                listA.add(item);
            }
        }
        return listA;
    }

    public static <K,V> Map<K,V> intersect(Map<K,V> mapA, Map<K,V> mapB){

        HashMap<K,V> newMap = new HashMap<>();
        for(Map.Entry<K,V> entry : mapA.entrySet()){

            if(mapB.containsKey(entry.getKey())){
                newMap.put(entry.getKey(),entry.getValue());
            }
        }
        return newMap;
    }

    public static <K,V> Map<K,V> union(Map<K,V> mapA, Map<K,V> mapB){

        for(Map.Entry<K,V> entry : mapA.entrySet()){

            if(!mapB.containsKey(entry.getKey())){
                mapA.put(entry.getKey(),entry.getValue());
            }
        }
        return mapA;
    }

    public static <K,V> Map<V,K> arrayFlip(Map<K,V> map){
        Map<V,K> newMap = new HashMap<>();
        for(Map.Entry<K, V> entry : map.entrySet()){
            newMap.put(entry.getValue(), entry.getKey());
        }
        return newMap;
    }

    public static <V> Map<V, Integer> arrayFlip(List<V> list){
        Map<V, Integer> newMap = new HashMap<>();
        Integer idx = 0;
        for (V v : list){
            newMap.put(v, idx++);
        }
        return newMap;
    }

    public static <K, V> List<V> arrayValues(Map<K, V> map){
        List<V> array = new ArrayList<>();
        for(Map.Entry<K,V> entry : map.entrySet()){
            array.add(entry.getValue());
        }
        return array;
    }

    public static String generateHash() {
        return org.springframework.util.DigestUtils.md5DigestAsHex(String.format("%s_%s", Math.random(), new Date().toString()).getBytes());
    }

    public static Map<String, Object> toMap(Object obj){
        if(obj == null){
            return new HashMap<>();
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
    }

    public static <T> T fromMap(Map map, Class<T> tClass){
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, tClass);
    }

    public static Map<String, Object> toMap(String json){
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        return new Gson().fromJson(json, type);
    }

    public static String capitaliseFirstLetter(String word){
        String[] words = word.split(" ");
        StringBuilder newWord = new StringBuilder();
        for (String word1 : words) {
            newWord.append(" ").append(word1.substring(0, 1).toUpperCase()).append(word1.substring(1));
        }
        return newWord.toString().trim();
    }
}
