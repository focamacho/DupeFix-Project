package com.focamacho.dupefixproject.fixes;

import com.google.common.collect.Lists;
import mekanism.common.MekanismBlocks;
import mekanism.common.inventory.container.ContainerPersonalChest;
import mekanism.common.recipe.BinRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class MekanismFixes {

    //Bin Recipe Dupe Fix
    public static void fixBinRecipes() {
        ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
        ArrayList<IRecipe> recipes = Lists.newArrayList(recipeRegistry.getValuesCollection());
        for (IRecipe recipe : recipes) {
            if (recipe instanceof BinRecipe) {
                recipeRegistry.remove(recipe.getRegistryName());
            }
        }
    }

}