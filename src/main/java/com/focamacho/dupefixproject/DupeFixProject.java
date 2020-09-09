package com.focamacho.dupefixproject;

import com.focamacho.dupefixproject.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = DupeFixProject.MODID, name = DupeFixProject.NAME, version = DupeFixProject.VERSION, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "1.12.2")
public class DupeFixProject {
	
    public static final String MODID = "dupefixproject";
    public static final String NAME = "DupeFix Project";
    public static final String VERSION = "3.0";

    public static Logger logger = LogManager.getLogger(NAME);

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if(Loader.isModLoaded("mekanism")) {
            MinecraftForge.EVENT_BUS.register(new MekanismFixes());
        }
        if(Loader.isModLoaded("projectred-exploration")) {
            MinecraftForge.EVENT_BUS.register(new ProjectRedWorldFixes());
        }
        if(Loader.isModLoaded("bloodmagic")) {
            MinecraftForge.EVENT_BUS.register(new BloodMagicFixes());
        }
        if(Loader.isModLoaded("tconstruct")) {
            MinecraftForge.EVENT_BUS.register(new TConstructFixes());
        }
    }
    
}
