package com.focamacho.dupefixproject.mixin.forestry;

import forestry.core.inventory.ItemInventory;
import forestry.storage.inventory.ItemInventoryBackpack;
import forestry.storage.items.ItemBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ItemInventoryBackpack.class, remap = false)
public abstract class ItemInventoryBackpackMixin extends ItemInventory {

    public ItemInventoryBackpackMixin(EntityPlayer player, int size, ItemStack parent) {
        super(player, size, parent);
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return player.getHeldItemMainhand().getItem() instanceof ItemBackpack;
    }

}