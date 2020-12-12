package com.focamacho.dupefixproject;

import com.focamacho.dupefixproject.event.*;
import com.focamacho.dupefixproject.util.LoadedFixes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = DupeFixProject.MODID, name = DupeFixProject.NAME, version = DupeFixProject.VERSION, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "1.12.2")
public class DupeFixProject {
	
    public static final String MODID = "dupefixproject";
    public static final String NAME = "DupeFix Project";
    public static final String VERSION = "3.1.0";

    public static Logger logger = LogManager.getLogger(NAME);

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new VanillaFixes());

        if(Loader.isModLoaded("mekanism")) {
            MinecraftForge.EVENT_BUS.register(new MekanismFixes());
            LoadedFixes.mekanism = true;
        }
        if(Loader.isModLoaded("projectred-exploration")) {
            MinecraftForge.EVENT_BUS.register(new ProjectRedWorldFixes());
            LoadedFixes.projectRedExploration = true;
        }
        if(LoadedFixes.bloodMagic) {
            MinecraftForge.EVENT_BUS.register(new BloodMagicFixes());
        }
        if(Loader.isModLoaded("tconstruct")) {
            MinecraftForge.EVENT_BUS.register(new TConstructFixes());
            LoadedFixes.tconstruct = true;
        }
        if(LoadedFixes.thaumcraft) {
            MinecraftForge.EVENT_BUS.register(new ThaumcraftFixes());
        }

        LoadedFixes.sendLoadedFixesLog();
    }

}
