package com.focamacho.dupefixproject.util;

import com.focamacho.dupefixproject.DupeFixProject;

public class LoadedFixes {

    public static boolean actuallyAdditions = false;
    public static boolean bloodMagic = false;
    public static boolean extraUtilities = false;
    public static boolean industrialForegoing = false;
    public static boolean netherChest = false;
    public static boolean spiceOfLife = false;
    public static boolean thaumcraft = false;
    public static boolean tinyProgressions = false;
    public static boolean enderio = false;
    public static boolean theFarlanders = false;
    public static boolean thaumicWonders = false;
    public static boolean forestry = false;
    public static boolean thermalExpansion = false;
    public static boolean mekanism = false;
    public static boolean projectRedExploration = false;
    public static boolean tconstruct = false;
    public static boolean arcaneArchives = false;
    public static boolean tcomplement = false;

    public static void sendLoadedFixesLog() {
        sendLog(actuallyAdditions, "Actually Additions");
        sendLog(bloodMagic, "Blood Magic");
        sendLog(extraUtilities, "Extra Utilities");
        sendLog(industrialForegoing, "Industrial Foregoing");
        sendLog(netherChest, "Nether Chest");
        sendLog(spiceOfLife, "Spice of Life");
        sendLog(thaumcraft, "Thaumcraft");
        sendLog(tinyProgressions, "Tiny Progressions");
        sendLog(enderio, "EnderIO");
        sendLog(theFarlanders, "The Farlanders");
        sendLog(thaumicWonders, "Thaumic Wonders");
        sendLog(forestry, "Forestry");
        sendLog(thermalExpansion, "Thermal Expansion");
        sendLog(mekanism, "Mekanism");
        sendLog(projectRedExploration, "Project Red: World");
        sendLog(tconstruct, "Tinkers' Construct");
        sendLog(arcaneArchives, "Arcane Archives");
        sendLog(tcomplement, "Tinkers' Complement");
    }

    private static void sendLog(boolean loaded, String modName) {
        if(loaded) {
            DupeFixProject.logger.info("Loaded fixes for " + modName);
        }
    }

}
