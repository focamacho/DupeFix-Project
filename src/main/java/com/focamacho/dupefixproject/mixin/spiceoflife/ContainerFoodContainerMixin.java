package com.focamacho.dupefixproject.mixin.spiceoflife;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import squeek.spiceoflife.inventory.ContainerGeneric;
import squeek.spiceoflife.items.ItemFoodContainer;

import javax.annotation.Nonnull;

@Mixin(targets = "squeek/spiceoflife/inventory/ContainerFoodContainer", remap = false)
public abstract class ContainerFoodContainerMixin extends ContainerGeneric {

    @Shadow @Nonnull public abstract ItemStack getItemStack();

    public ContainerFoodContainerMixin(IInventory inventory) {
        super(inventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return inventory.isUsableByPlayer(playerIn) && playerIn.getHeldItemMainhand().getItem() instanceof ItemFoodContainer;
    }

}
