package com.focamacho.dupefixproject.asm;

import com.focamacho.dupefixproject.config.DupeFixProjectConfig;
import com.focamacho.dupefixproject.util.LoadedFixes;
import com.focamacho.dupefixproject.util.ModHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.io.IOException;
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

        LoadedFixes.bloodMagic          = loadMixin("mixins.dupefixproject.bloodmagic.json",            "bloodmagic");
        LoadedFixes.netherChest         = loadMixin("mixins.dupefixproject.netherchest.json",           "netherchest");
        LoadedFixes.spiceOfLife         = loadMixin("mixins.dupefixproject.spiceoflife.json",           "spiceoflife");
        LoadedFixes.thaumcraft          = loadMixin("mixins.dupefixproject.thaumcraft.json",            "thaumcraft");      if(LoadedFixes.thaumcraft)
        LoadedFixes.enderio             = loadMixin("mixins.dupefixproject.thaumcraft.enderio.json",    "enderio");
        LoadedFixes.tinyProgressions    = loadMixin("mixins.dupefixproject.tp.json",                    "tp");
        LoadedFixes.industrialForegoing = loadMixin("mixins.dupefixproject.industrialforegoing.json",   "industrialforegoing");
        LoadedFixes.actuallyAdditions   = loadMixin("mixins.dupefixproject.actuallyadditions.json",     "actuallyadditions");
        LoadedFixes.extraUtilities      = loadMixin("mixins.dupefixproject.extrautilities.json",        "extrautils2");
        LoadedFixes.theFarlanders       = loadMixin("mixins.dupefixproject.thefarlanders.json",         "farlanders");
        LoadedFixes.thaumicWonders      = loadMixin("mixins.dupefixproject.thaumicwonders.json",        "thaumicwonders");
        LoadedFixes.forestry            = loadMixin("mixins.dupefixproject.forestry.json",              "forestry");
        LoadedFixes.thermalExpansion    = loadMixin("mixins.dupefixproject.thermalexpansion.json",      "thermalexpansion");

        MixinBootstrap.init();
        ModHandler.clear();
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

    private boolean loadMixin(String mixin, String modid){

        if(ModHandler.load(modid)){
            Mixins.addConfiguration("mixins/" + mixin);
            return true;
        }

        return false;
    }

}
