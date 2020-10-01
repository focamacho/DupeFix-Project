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
    public static String industrialForegoing = "";
    public static String actuallyAdditions = "";
    public static String extraUtilities = "";
    public static String theFarlanders = "";
    public static String comment = "Visit the wiki to learn how to configure it: https://github.com/Focamacho/DupeFix-Project/wiki";

    public static void initConfigs() throws IOException {
        JSONObject json = JsonHandler.getOrCreateJson(configFile);

        spiceOfLife = JsonHandler.getOrCreateString(configFile, "spiceOfLife", "");
        thaumcraft = JsonHandler.getOrCreateString(configFile, "thaumcraft", "");
        netherChest = JsonHandler.getOrCreateString(configFile, "netherChest", "");
        bloodMagic = JsonHandler.getOrCreateString(configFile, "bloodMagic", "");
        tinyProgressions = JsonHandler.getOrCreateString(configFile, "tinyProgressions", "");
        enderio = JsonHandler.getOrCreateString(configFile, "enderio", "");
        industrialForegoing = JsonHandler.getOrCreateString(configFile, "industrialForegoing", "");
        actuallyAdditions = JsonHandler.getOrCreateString(configFile, "actuallyAdditions", "");
        extraUtilities = JsonHandler.getOrCreateString(configFile, "extraUtilities", "");
        theFarlanders = JsonHandler.getOrCreateString(configFile, "theFarlanders", "");
        comment = JsonHandler.getOrCreateString(configFile, "_comment", comment);
    }

}
