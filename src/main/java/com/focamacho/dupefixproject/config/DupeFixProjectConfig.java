package com.focamacho.dupefixproject.config;

import com.focamacho.dupefixproject.util.JsonHandler;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class DupeFixProjectConfig {

    public static File configFile = new File("./config/dupefixproject.json");

    public static String spiceOfLife = "";
    public static String thaumcraft = "";
    public static String netherChest = "";
    public static String bloodMagic = "";
    public static String tinyProgressions = "";
    public static String enderio = "";

    public static void initConfigs() throws IOException {
        JSONObject json = JsonHandler.getOrCreateJson(configFile);

        spiceOfLife = JsonHandler.getOrCreateString(configFile, "spiceOfLife", "");
        thaumcraft = JsonHandler.getOrCreateString(configFile, "thaumcraft", "");
        netherChest = JsonHandler.getOrCreateString(configFile, "netherChest", "");
        bloodMagic = JsonHandler.getOrCreateString(configFile, "bloodMagic", "");
        tinyProgressions = JsonHandler.getOrCreateString(configFile, "tinyProgressions", "");
        enderio = JsonHandler.getOrCreateString(configFile, "enderio", "");
    }

}
