package com.focamacho.dupefixproject.mixin.ironbackpacks;

import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import gr8pefish.ironbackpacks.container.ContainerBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Mixin(ContainerBackpack.class)
public class ContainerBackpackMixin extends Container {

    @Shadow(remap = false) @Final @Nonnull private ItemStack backpackStack;

    @Override
    @ParametersAreNonnullByDefault
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.backpackStack.getItem() instanceof IBackpack && (playerIn.getHeldItemMainhand() == this.backpackStack || playerIn.getHeldItemOffhand() == this.backpackStack);
    }

}
