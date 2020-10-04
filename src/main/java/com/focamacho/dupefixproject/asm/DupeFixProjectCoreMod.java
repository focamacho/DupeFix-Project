package com.focamacho.dupefixproject.asm;

import com.focamacho.dupefixproject.DupeFixProject;
import com.focamacho.dupefixproject.config.DupeFixProjectConfig;
import com.focamacho.dupefixproject.util.LoadedFixes;
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

        if(loadJar(DupeFixProjectConfig.bloodMagic, "Blood Magic", "mixins.dupefixproject.bloodmagic.json")) LoadedFixes.bloodMagic = true;
        if(loadJar(DupeFixProjectConfig.netherChest, "Nether Chest", "mixins.dupefixproject.netherchest.json")) LoadedFixes.netherChest = true;
        if(loadJar(DupeFixProjectConfig.spiceOfLife, "Spice of Life", "mixins.dupefixproject.spiceoflife.json")) LoadedFixes.spiceOfLife = true;
        if(loadJar(DupeFixProjectConfig.thaumcraft, "Thaumcraft", "mixins.dupefixproject.thaumcraft.json")) {
            LoadedFixes.thaumcraft = true;
            if (loadJar(DupeFixProjectConfig.enderio, "EnderIO", "mixins.dupefixproject.thaumcraft.enderio.json")) LoadedFixes.enderio = true;
        }
        if(loadJar(DupeFixProjectConfig.tinyProgressions, "Tiny Progressions", "mixins.dupefixproject.tp.json")) LoadedFixes.tinyProgressions = true;
        if(loadJar(DupeFixProjectConfig.industrialForegoing, "Industrial Foregoing", "mixins.dupefixproject.industrialforegoing.json")) LoadedFixes.industrialForegoing = true;
        if(loadJar(DupeFixProjectConfig.actuallyAdditions, "Actually Additions", "mixins.dupefixproject.actuallyadditions.json")) LoadedFixes.actuallyAdditions = true;
        if(loadJar(DupeFixProjectConfig.extraUtilities, "Extra Utilities", "mixins.dupefixproject.extrautilities.json")) LoadedFixes.extraUtilities = true;
        if(loadJar(DupeFixProjectConfig.theFarlanders, "The Farlanders", "mixins.dupefixproject.thefarlanders.json")) LoadedFixes.theFarlanders = true;
        if(loadJar(DupeFixProjectConfig.thaumicWonders, "Thaumic Wonders", "mixins.dupefixproject.thaumicwonders.json")) LoadedFixes.thaumicWonders = true;
        if(loadJar(DupeFixProjectConfig.forestry, "Forestry", "mixins.dupefixproject.forestry.json")) LoadedFixes.forestry = true;

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

    private boolean loadJar(String jar, String name, String mixin) {
        if(jar.isEmpty()) {
            DupeFixProject.logger.error("Couldn't load fixes for " + name + ". If the mod is present, check your configuration file.");
            return false;
        }
        File file = new File("mods", jar);
        if(!file.exists()) {
            DupeFixProject.logger.error("Couldn't load fixes for " + name + ". If the mod is present, check your configuration file.");
            return false;
        }
        try {
            loadJar(file);
            DupeFixProject.logger.info("Initializing " + name + " Fixes");
            Mixins.addConfiguration("mixins/" + mixin);
            return true;
        } catch(Exception e) {
            DupeFixProject.logger.error("Couldn't load fixes for " + name + ". If the mod is present, check your configuration file.");
            return false;
        }
    }

    private void loadJar(File jar) throws MalformedURLException {
        ((LaunchClassLoader) this.getClass().getClassLoader()).addURL(jar.toURI().toURL());
        CoreModManager.getReparseableCoremods().add(jar.getName());
    }
}
