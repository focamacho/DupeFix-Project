package com.focamacho.dupefixproject.fixes;

import com.google.common.collect.Lists;
import mekanism.common.recipe.BinRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.ArrayList;

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