package com.yujiangjun.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    private JsonUtil(){}

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readObject(String json,Class<T> clazz){
        try {
            return objectMapper.readValue(json,clazz);
        } catch (JsonProcessingException e) {
            log.error("json 序列化失败,",e);
            return null;
        }
    }

    public static <T> String writeObject(T data){
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("json反序列化失败",e);
        }
        return null;
    }
}
