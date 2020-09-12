package com.focamacho.dupefixproject.asm;

import com.focamacho.dupefixproject.DupeFixProject;
import com.focamacho.dupefixproject.config.DupeFixProjectConfig;
import com.focamacho.dupefixproject.util.LoadedFixes;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.CoreModManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

@IFMLLoadingPlugin.SortingIndex(731)
@IFMLLoadingPlugin.MCVersion("1.12.2")
public class DupeFixProjectCoreMod implements IFMLLoadingPlugin {

    public DupeFixProjectCoreMod() {
        try {
            DupeFixProjectConfig.initConfigs();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(loadJar(DupeFixProjectConfig.bloodMagic)) {
            LoadedFixes.bloodMagic = true;
            DupeFixProject.logger.info("Initializing Blood Magic Fixes");
            Mixins.addConfiguration("mixins/mixins.dupefixproject.bloodmagic.json");
        } else {
            DupeFixProject.logger.error("Couldn't load fixes for Blood Magic. Check your configuration file.");
        }
        if(loadJar(DupeFixProjectConfig.netherChest)) {
            LoadedFixes.netherChest = true;
            DupeFixProject.logger.info("Initializing Nether Chest Fixes");
            Mixins.addConfiguration("mixins/mixins.dupefixproject.netherchest.json");
        } else {
            DupeFixProject.logger.error("Couldn't load fixes for Nether Chest. Check your configuration file.");
        }
        if(loadJar(DupeFixProjectConfig.spiceOfLife)) {
            LoadedFixes.spiceOfLife = true;
            DupeFixProject.logger.info("Initializing Spice of Life Fixes");
            Mixins.addConfiguration("mixins/mixins.dupefixproject.spiceoflife.json");
        } else {
            DupeFixProject.logger.error("Couldn't load fixes for Spice of Life. Check your configuration file.");
        }
        if(loadJar(DupeFixProjectConfig.thaumcraft)) {
            LoadedFixes.thaumcraft = true;
            DupeFixProject.logger.info("Initializing Thaumcraft Fixes");
            Mixins.addConfiguration("mixins/mixins.dupefixproject.thaumcraft.json");
            if(loadJar(DupeFixProjectConfig.enderio)) {
                LoadedFixes.enderio = true;
                Mixins.addConfiguration("mixins/mixins.dupefixproject.thaumcraft.enderio.json");
            } else {
                DupeFixProject.logger.error("Couldn't load fixes for EnderIO. Check your configuration file.");
            }
        } else {
            DupeFixProject.logger.error("Couldn't load fixes for Thaumcraft. Check your configuration file.");
        }
        if(loadJar(DupeFixProjectConfig.tinyProgressions))  {
            LoadedFixes.tinyProgressions = true;
            DupeFixProject.logger.info("Initializing Tiny Progressions Fixes");
            Mixins.addConfiguration("mixins/mixins.dupefixproject.tp.json");
        } else {
            DupeFixProject.logger.error("Couldn't load fixes for Tiny Progressions. Check your configuration file.");
        }
        if(loadJar(DupeFixProjectConfig.industrialForegoing))  {
            LoadedFixes.industrialForegoing = true;
            DupeFixProject.logger.info("Initializing Industrial Foregoing Fixes");
            Mixins.addConfiguration("mixins/mixins.dupefixproject.industrialforegoing.json");
        } else {
            DupeFixProject.logger.error("Couldn't load fixes for Industrial Foregoing. Check your configuration file.");
        }
        if(loadJar(DupeFixProjectConfig.actuallyAdditions)) {
            LoadedFixes.actuallyAdditions = true;
            DupeFixProject.logger.info("Initializing Actually Additions Fixes");
            Mixins.addConfiguration("mixins/mixins.dupefixproject.actuallyadditions.json");
        } else {
            DupeFixProject.logger.error("Couldn't load fixes for Actually Additions. Check your configuration file.");
        }
        if(loadJar(DupeFixProjectConfig.extraUtilities)) {
            LoadedFixes.extraUtilities = true;
            DupeFixProject.logger.info("Initializing Extra Utilities Fixes");
            Mixins.addConfiguration("mixins/mixins.dupefixproject.extrautilities.json");
        } else {
            DupeFixProject.logger.error("Couldn't load fixes for Extra Utilities. Check your configuration file.");
        }

        MixinBootstrap.init();
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    private boolean loadJar(String jar) {
        if(jar.isEmpty()) return false;
        File file = new File("mods", jar);
        if(!file.exists()) return false;
        try {
            loadJar(file);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    private void loadJar(File jar) throws MalformedURLException {
        ((LaunchClassLoader) this.getClass().getClassLoader()).addURL(jar.toURI().toURL());
        CoreModManager.getReparseableCoremods().add(jar.getName());
    }
}
