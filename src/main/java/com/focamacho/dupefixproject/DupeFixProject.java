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
    public static final String VERSION = "3.0.2";

    public static Logger logger = LogManager.getLogger(NAME);

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new VanillaFixes());

        if(Loader.isModLoaded("mekanism")) {
            MinecraftForge.EVENT_BUS.register(new MekanismFixes());
        }
        if(Loader.isModLoaded("projectred-exploration")) {
            MinecraftForge.EVENT_BUS.register(new ProjectRedWorldFixes());
        }
        if(LoadedFixes.bloodMagic) {
            MinecraftForge.EVENT_BUS.register(new BloodMagicFixes());
        }
        if(Loader.isModLoaded("tconstruct")) {
            MinecraftForge.EVENT_BUS.register(new TConstructFixes());
        }
        if(LoadedFixes.thaumcraft) {
            MinecraftForge.EVENT_BUS.register(new ThaumcraftFixes());
        }

        if(!LoadedFixes.getModFixesNotLoaded().isEmpty()) {
            logger.error("DupeFix Project failed to load some fixes");
            logger.error("The following mods have their fixes not loaded:");
            logger.error(LoadedFixes.getModFixesNotLoaded());
            logger.error("Visit the wiki to learn how to configure it");
            logger.error("Wiki: https://github.com/Focamacho/DupeFix-Project/wiki");
        }
    }

}
