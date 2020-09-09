package com.focamacho.dupefixproject.mixin.thaumcraft.enderio;

import crazypants.enderio.base.handler.darksteel.gui.DSUContainer;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.api.items.ItemsTC;

@Mixin(targets = "crazypants/enderio/base/handler/darksteel/gui/DSUContainer", remap = false)
public class DSUContainerMixin {

    @Inject(method = "canInteractWith", at = @At("HEAD"), cancellable = true)
    private void canInteractWith(EntityPlayer player, CallbackInfoReturnable<Boolean> info) {
        if(((DSUContainer)(Object)this).getSlot(1).getStack().getItem().equals(ItemsTC.primordialPearl)) info.setReturnValue(false);
    }


}
