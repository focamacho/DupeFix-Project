package com.focamacho.dupefixproject;

import com.focamacho.dupefixproject.event.*;
import com.focamacho.dupefixproject.util.LoadedFixes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
        if(LoadedFixes.bloodMagic) {
            MinecraftForge.EVENT_BUS.register(new BloodMagicFixes());
        }
        if(Loader.isModLoaded("tconstruct")) {
            MinecraftForge.EVENT_BUS.register(new TConstructFixes());
        }
        if(LoadedFixes.thaumcraft) {
            MinecraftForge.EVENT_BUS.register(new ThaumcraftFixes());
        }
    }

}
