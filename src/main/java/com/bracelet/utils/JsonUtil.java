package com.bracelet.utils;

//import com.auth0.jwt.internal.com.fasterxml.jackson.databind.JavaType;
//import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
   //未使用
    private static ObjectMapper objectMapper;

    static{
        objectMapper = new ObjectMapper();
    }

    public static <T> String toJson(T obj){
        String result = null;
        try {
            result = objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            return null;
        }
        return result;
    }

    public static <T> T toObject(String json,Class<T> classT){
        T result=null;
        try {
            result = objectMapper.readValue(json,classT);
        } catch (IOException e) {
            return null;
        }
        return result;
    }

    public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (IOException e) {
            System.out.println("Parse String to Object error");
            e.printStackTrace();
            return null;
        }
    }
}