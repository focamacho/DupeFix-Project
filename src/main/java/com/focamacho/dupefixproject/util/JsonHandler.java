package com.focamacho.dupefixproject.util;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonHandler {

    public static JSONObject getJson(File jsonFile) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(jsonFile.getPath()));
        return new JSONObject(new String(encoded, StandardCharsets.UTF_8));
    }

    public static JSONObject getOrCreateJson(File jsonFile) throws IOException {
        if(!jsonFile.exists()) {
            jsonFile.getParentFile().mkdirs();
            jsonFile.createNewFile();
            FileUtils.write(jsonFile, "{}", StandardCharsets.UTF_8);
        }
        return getJson(jsonFile);
    }

    public static void saveToJson(File jsonFile, JSONObject toWrite) throws IOException {
        FileUtils.write(jsonFile, toWrite.toString(4), StandardCharsets.UTF_8);
    }

    public static String getOrCreateString(File jsonFile, String key, String value) throws IOException {
        JSONObject json = getOrCreateJson(jsonFile);
        if(json.has(key)) {
            return json.getString(key);
        } else {
            json.put(key, value);
            saveToJson(jsonFile, json);
            return value;
        }
    }

    public static boolean getOrCreateBoolean(File jsonFile, String key, boolean value) throws IOException {
        JSONObject json = getOrCreateJson(jsonFile);
        if(json.has(key)) {
            return json.getBoolean(key);
        } else {
            json.put(key, value);
            saveToJson(jsonFile, json);
            return value;
        }
    }

    public static int getOrCreateInt(File jsonFile, String key, int value) throws IOException {
        JSONObject json = getOrCreateJson(jsonFile);
        if(json.has(key)) {
            return json.getInt(key);
        } else {
            json.put(key, value);
            saveToJson(jsonFile, json);
            return value;
        }
    }

    public static JSONArray getOrCreateJsonArray(File jsonFile, String key) throws IOException {
        JSONObject json = getOrCreateJson(jsonFile);
        if(json.has(key)) {
            return json.getJSONArray(key);
        } else {
            return new JSONArray();
        }
    }

}
