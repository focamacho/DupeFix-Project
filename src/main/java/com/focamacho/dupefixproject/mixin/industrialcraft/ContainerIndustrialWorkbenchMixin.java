package com.focamacho.dupefixproject.mixin.industrialcraft;

import ic2.core.block.machine.container.ContainerIndustrialWorkbench;
import net.minecraft.inventory.IInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ContainerIndustrialWorkbench.class, remap = false)
public class ContainerIndustrialWorkbenchMixin {

    @Shadow @Final protected IInventory craftResult;

    @Inject(method = "onContainerEvent", at = @At("HEAD"))
    public void onContainerEvent(String event, CallbackInfo ci) {
        if(event.equalsIgnoreCase("clear")) this.craftResult.clear();
    }

}
