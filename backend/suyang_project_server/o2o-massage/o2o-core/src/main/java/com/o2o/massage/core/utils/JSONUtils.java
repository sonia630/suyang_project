package com.o2o.massage.core.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 *
 */
public abstract class JSONUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @SuppressWarnings("unchecked")
    public static <T> T parse(String data, TypeReference<T> typeRef)
            throws IOException {
        return (T) objectMapper.readValue(data, typeRef);
    }

    public static <T> T parseQuietly(String data, TypeReference<T> typeRef) {
        try {
            return parse(data, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parse(String data, Class<T> clazz) throws IOException {
        return (T) objectMapper.readValue(data, clazz);
    }

    public static JsonNode parse(String data) throws IOException {
        return objectMapper.readTree(data);
    }

    public static String writeValue(Object value) throws IOException {
        return objectMapper.writeValueAsString(value);
    }

    public static TreeNode toJsonNode(Object value) throws IOException {
        String toStr = writeValue(value);
        JsonNode jsonNode = objectMapper.valueToTree(toStr);
        return jsonNode;
    }

    public static ObjectMapper getObjectMapperInstance() {
        return objectMapper;
    }
}
