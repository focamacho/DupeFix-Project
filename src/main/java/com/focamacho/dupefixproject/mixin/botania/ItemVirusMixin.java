package com.focamacho.dupefixproject.mixin.botania;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.item.ItemVirus;

@Mixin(value = ItemVirus.class, remap = false)
public class ItemVirusMixin {

    @Inject(method = "func_111207_a", at = @At("HEAD"), cancellable = true)
    public void itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase living, EnumHand hand, CallbackInfoReturnable<Boolean> cir) {
        //For some reason if the entity is tamed it is not set as dead????????????
        if(living.isDead || living.getHealth() <= 0) cir.setReturnValue(false);
    }

}
