package com.focamacho.dupefixproject.mixin.bloodmagic;

import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.item.inventory.ContainerHolding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ContainerHolding.class, remap = false)
public class ContainerHoldingMixin extends Container {

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if(player.getHeldItemMainhand().getItem() != RegistrarBloodMagicItems.SIGIL_HOLDING) return false;
        return true;
    }

}
