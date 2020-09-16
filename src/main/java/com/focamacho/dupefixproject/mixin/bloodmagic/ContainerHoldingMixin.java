package com.focamacho.dupefixproject.mixin.bloodmagic;

import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "WayofTime/bloodmagic/item/inventory/ContainerHolding", remap = false)
public class ContainerHoldingMixin extends Container {

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if(player.getHeldItemMainhand().getItem() != RegistrarBloodMagicItems.SIGIL_HOLDING) return false;
        return true;
    }

}
