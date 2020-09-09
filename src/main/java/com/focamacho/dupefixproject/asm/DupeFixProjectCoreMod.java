package com.focamacho.dupefixproject.asm;

import com.focamacho.dupefixproject.DupeFixProject;
import com.focamacho.dupefixproject.config.DupeFixProjectConfig;
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
            DupeFixProject.logger.info("Initialized Blood Magic Fixes");
            Mixins.addConfiguration("mixins/mixins.dupefixproject.bloodmagic.json");
        }
        if(loadJar(DupeFixProjectConfig.netherChest)) {
            DupeFixProject.logger.info("Initialized Nether Chest Fixes");
            Mixins.addConfiguration("mixins/mixins.dupefixproject.netherchest.json");
        }
        if(loadJar(DupeFixProjectConfig.spiceOfLife)) {
            DupeFixProject.logger.info("Initialized Spice of Life Fixes");
            Mixins.addConfiguration("mixins/mixins.dupefixproject.spiceoflife.json");
        }
        if(loadJar(DupeFixProjectConfig.thaumcraft)) {
            DupeFixProject.logger.info("Initialized Thaumcraft Fixes");
            Mixins.addConfiguration("mixins/mixins.dupefixproject.thaumcraft.json");
            if(loadJar(DupeFixProjectConfig.enderio)) {
                DupeFixProject.logger.info("Initialized EnderIO Fixes");
                Mixins.addConfiguration("mixins/mixins.dupefixproject.thaumcraft.enderio.json");
            }
        }
        if(loadJar(DupeFixProjectConfig.tinyProgressions))  {
            DupeFixProject.logger.info("Initialized Tiny Progressions Fixes");
            Mixins.addConfiguration("mixins/mixins.dupefixproject.tp.json");
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
