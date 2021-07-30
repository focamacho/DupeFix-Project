package com.focamacho.dupefixproject.mixin.thaumcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.common.entities.construct.EntityOwnedConstruct;
import thaumcraft.common.golems.EntityThaumcraftGolem;

@Mixin(value = EntityThaumcraftGolem.class, remap = false)
public class EntityThaumcraftGolemMixin extends EntityOwnedConstruct {

    public EntityThaumcraftGolemMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "func_184645_a", at = @At("HEAD"), cancellable = true)
    private void processInteract(EntityPlayer player, EnumHand hand, CallbackInfoReturnable<Boolean> cir) {
        if(getHealth() <= 0.0F) cir.setReturnValue(false);
    }

}
