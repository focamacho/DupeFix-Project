package com.focamacho.dupefixproject.mixin.thaumicwonders;

import com.verdantartifice.thaumicwonders.common.entities.EntityFlyingCarpet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityFlyingCarpet.class, remap = false)
public abstract class EntityFlyingCarpetMixin extends Entity {

    public EntityFlyingCarpetMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "func_184230_a", at = @At("HEAD"), cancellable = true)
    private void processInitialInteract(EntityPlayer player, EnumHand hand, CallbackInfoReturnable<Boolean> info) {
        if(this.isDead) info.setReturnValue(true);
    }

}
