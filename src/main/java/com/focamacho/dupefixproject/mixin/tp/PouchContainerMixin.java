package com.focamacho.dupefixproject.mixin.tp;

import com.kashdeya.tinyprogressions.container.PouchContainer;
import com.kashdeya.tinyprogressions.inits.TechItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PouchContainer.class, remap = false)
public class PouchContainerMixin extends Container {

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return player.getHeldItemMainhand().getItem() == TechItems.pouch;
    }

}