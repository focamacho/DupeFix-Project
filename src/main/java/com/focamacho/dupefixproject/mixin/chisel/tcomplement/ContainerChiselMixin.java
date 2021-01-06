package com.focamacho.dupefixproject.mixin.chisel.tcomplement;

import knightminer.tcomplement.plugin.chisel.items.ItemChisel;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.chisel.common.inventory.ContainerChisel;
import team.chisel.common.inventory.InventoryChiselSelection;

@Mixin(value = ContainerChisel.class, remap = false)
public abstract class ContainerChiselMixin {

    @Shadow public abstract InventoryPlayer getInventoryPlayer();

    @Shadow @Final protected InventoryChiselSelection inventoryChisel;

    @Shadow @Final protected ItemStack chisel;

    @Inject(method = "onChiselBroken", at = @At("HEAD"), cancellable = true)
    private void onChiselBroken(CallbackInfo info) {
        if (!getInventoryPlayer().player.world.isRemote) {
            if(chisel.getItem() instanceof ItemChisel) {
                inventoryChisel.setStackInSpecialSlot(ItemStack.EMPTY);
                info.cancel();
            }
        }
    }

}
