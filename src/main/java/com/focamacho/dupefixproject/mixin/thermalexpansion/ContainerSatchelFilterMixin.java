package com.focamacho.dupefixproject.mixin.thermalexpansion;

import cofh.core.gui.container.ContainerCore;
import cofh.core.gui.slot.SlotLocked;
import cofh.thermalexpansion.gui.container.storage.ContainerSatchelFilter;
import cofh.thermalexpansion.item.ItemSatchel;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ContainerSatchelFilter.class)
public abstract class ContainerSatchelFilterMixin extends ContainerCore {

    @Override
    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {

        int xOffset = getPlayerInventoryHorizontalOffset();
        int yOffset = getPlayerInventoryVerticalOffset();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                if(inventoryPlayer.getStackInSlot(j + i * 9 + 9).getItem() instanceof ItemSatchel) addSlotToContainer(new SlotLocked(inventoryPlayer, j + i * 9 + 9, xOffset + j * 18, yOffset + i * 18));
                else addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, xOffset + j * 18, yOffset + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            if (i == inventoryPlayer.currentItem || inventoryPlayer.getStackInSlot(i).getItem() instanceof ItemSatchel) {
                addSlotToContainer(new SlotLocked(inventoryPlayer, i, xOffset + i * 18, yOffset + 58));
            } else {
                addSlotToContainer(new Slot(inventoryPlayer, i, xOffset + i * 18, yOffset + 58));
            }
        }
    }

}
