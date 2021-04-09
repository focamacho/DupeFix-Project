package com.focamacho.dupefixproject.mixin.thermalexpansion;

import cofh.core.gui.container.ContainerCore;
import cofh.thermalexpansion.gui.container.storage.ContainerSatchelFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ContainerSatchelFilter.class, remap = false)
public abstract class ContainerSatchelFilterMixin extends ContainerCore {

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        if(clickTypeIn == ClickType.SWAP) {
            final ItemStack stack = player.inventory.getStackInSlot(dragType);
            final ItemStack currentItem = player.inventory.getCurrentItem();

            if (!currentItem.isEmpty() && stack == currentItem) {
                return ItemStack.EMPTY;
            }
        }
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

}
