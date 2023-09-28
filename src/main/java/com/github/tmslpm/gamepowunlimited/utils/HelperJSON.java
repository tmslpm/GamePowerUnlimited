package com.github.tmslpm.gamepowunlimited.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class HelperJSON {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Use type generic for pass any object type
     *
     * @param outputPathJsonFile the path for the output file
     * @param object the object
     * @param <T> the object type
     */
    public static <T> void toFile(String outputPathJsonFile, T object) {
        try (Writer writer = new FileWriter(outputPathJsonFile)) {
            GSON.toJson(object, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param inputPathJsonFile the path for the input json file
     * @param clazz the class of T
     * @return <T> the object
     * @param <T> the object type
     */
    public static <T> T fromFile(String inputPathJsonFile, Class<T> clazz) {
        try (Reader reader = new FileReader(inputPathJsonFile)) {
            return GSON.fromJson(reader, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean contains() {

        return false;
    }

}

