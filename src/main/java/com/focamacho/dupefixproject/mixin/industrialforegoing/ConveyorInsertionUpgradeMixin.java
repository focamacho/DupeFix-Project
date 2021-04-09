package com.focamacho.dupefixproject.mixin.industrialforegoing;

import com.buuz135.industrial.proxy.block.upgrade.ConveyorInsertionUpgrade;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ConveyorInsertionUpgrade.class, remap = false)
public class ConveyorInsertionUpgradeMixin {

    @Inject(method = "handleEntity", at = @At("HEAD"), cancellable = true)
    private void handleEntity(Entity entity, CallbackInfo info) {
        if(entity.isDead) info.cancel();
    }

}
