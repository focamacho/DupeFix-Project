package com.focamacho.dupefixproject.asm;

import com.focamacho.dupefixproject.util.LoadedFixes;
import com.focamacho.dupefixproject.util.ModHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.SortingIndex(731)
@IFMLLoadingPlugin.MCVersion("1.12.2")
public class DupeFixProjectCoreMod implements IFMLLoadingPlugin {

    public DupeFixProjectCoreMod() {
        //Blood Magic
        LoadedFixes.bloodMagic = loadMixin("mixins.dupefixproject.bloodmagic.json", "bloodmagic");

        //Nether Chest
        LoadedFixes.netherChest = loadMixin("mixins.dupefixproject.netherchest.json", "netherchest");

        //Spice of Life
        LoadedFixes.spiceOfLife = loadMixin("mixins.dupefixproject.spiceoflife.json", "spiceoflife");

        //Thaumcraft
        LoadedFixes.thaumcraft = loadMixin("mixins.dupefixproject.thaumcraft.json", "thaumcraft");
        if(LoadedFixes.thaumcraft) LoadedFixes.enderio = loadMixin("mixins.dupefixproject.thaumcraft.enderio.json", "enderio");

        //Tiny Progressions
        LoadedFixes.tinyProgressions = loadMixin("mixins.dupefixproject.tp.json", "tp");

        //Industrial Foregoing
        LoadedFixes.industrialForegoing = loadMixin("mixins.dupefixproject.industrialforegoing.json", "industrialforegoing");

        //Actually Additions
        LoadedFixes.actuallyAdditions = loadMixin("mixins.dupefixproject.actuallyadditions.json", "actuallyadditions");

        //Extra Utilities
        LoadedFixes.extraUtilities = loadMixin("mixins.dupefixproject.extrautilities.json", "extrautils2");

        //The Farlanders
        LoadedFixes.theFarlanders = loadMixin("mixins.dupefixproject.thefarlanders.json", "farlanders");

        //Thaumic Wonders
        LoadedFixes.thaumicWonders = loadMixin("mixins.dupefixproject.thaumicwonders.json", "thaumicwonders");

        //Forestry
        LoadedFixes.forestry = loadMixin("mixins.dupefixproject.forestry.json", "forestry");

        //Thermal Expansion
        LoadedFixes.thermalExpansion = loadMixin("mixins.dupefixproject.thermalexpansion.json", "thermalexpansion");

        //Arcane Archives
        LoadedFixes.arcaneArchives = loadMixin("mixins.dupefixproject.arcanearchives.json", "arcanearchives");

        //Tinkers' Complement
        if(ModHandler.load("chisel")) LoadedFixes.tcomplement = loadMixin("mixins.dupefixproject.chisel.tcomplement.json", "tcomplement");

        //Botania
        LoadedFixes.botania = loadMixin("mixins.dupefixproject.botania.json", "botania");

        //Industrial Craft 2
        LoadedFixes.industrialCraft = loadMixin("mixins.dupefixproject.industrialcraft.json", "ic2");

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
