package com.example.demo.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

/**
 * The type App json utils.
 */
public class AppJsonUtils {

    /**
     * Create gson gson.
     *
     * @return the gson
     */
    public static Gson createPrettyGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

    /**
     * Create gson gson.
     *
     * @return the gson
     */
    public static Gson createGson() {
        return new GsonBuilder()
                .serializeNulls()
                .create();
    }

    /**
     * Convert string to json json object.
     *
     * @param message the message
     * @return the json object
     */
    public static JsonObject convertStringToJson(String message) {
        return new JsonParser().parse(message).getAsJsonObject();
    }

    /**
     * Convert message to class object.
     *
     * @param json   the json
     * @param object the object
     * @return the object
     */
    public static Object convertMessageToClass(String json, Class object) {
        return createGson().fromJson(json, (Type) object);
    }

    /**
     * Convert to json string string.
     *
     * @param object the object
     * @return the string
     */
    public static String convertToJsonString(Object object) {
        return createGson().toJson(object);
    }


    /**
     * Convert to pretty json string string.
     *
     * @param object the object
     * @return the string
     */
    public static String convertToPrettyJsonString(Object object) {
        return createPrettyGson().toJson(object);
    }
}
