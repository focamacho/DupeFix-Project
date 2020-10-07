package com.focamacho.dupefixproject.mixin.thaumcraft;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.container.ContainerHandMirror;

@Mixin(ContainerHandMirror.class)
public class ContainerHandMirrorMixin {
    @Redirect(method = "<init>", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;getCurrentItem()Lnet/minecraft/item/ItemStack;"))
    private ItemStack getCurrentItem(InventoryPlayer iinventory) {
        ItemStack mirror = iinventory.player.getHeldItemMainhand();
        if (!mirror.getItem().equals(ItemsTC.handMirror) || !mirror.hasTagCompound())
            mirror = iinventory.player.getHeldItemOffhand();
        return mirror;
    }
}
