package com.focamacho.dupefixproject;

import com.focamacho.dupefixproject.event.*;
import com.focamacho.dupefixproject.fixes.MekanismFixes;
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
    public static final String VERSION = "3.1.3";

    public static Logger logger = LogManager.getLogger(NAME);

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new VanillaEvents());

        if(Loader.isModLoaded("projectred-exploration")) {
            MinecraftForge.EVENT_BUS.register(new ProjectRedWorldEvents());
            LoadedFixes.projectRedExploration = true;
        }
        if(Loader.isModLoaded("tconstruct")) {
            MinecraftForge.EVENT_BUS.register(new TConstructEvents());
            LoadedFixes.tconstruct = true;
        }
        if(Loader.isModLoaded("arcanearchives")) {
            MinecraftForge.EVENT_BUS.register(new ArcaneArchivesEvents());
            LoadedFixes.arcaneArchives = true;
        }

        if(LoadedFixes.mekanism) {
            MekanismFixes.fixBinRecipes();
        }
        if(LoadedFixes.thaumcraft) {
            MinecraftForge.EVENT_BUS.register(new ThaumcraftEvents());
        }
        if(LoadedFixes.bloodMagic) {
            MinecraftForge.EVENT_BUS.register(new BloodMagicEvents());
        }

        LoadedFixes.sendLoadedFixesLog();
    }

}
