package com.focamacho.dupefixproject.mixin.netherchest;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import netherchest.common.inventory.ExtendedItemStackHandler;
import netherchest.common.tileentity.TileEntityNetherChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TileEntityNetherChest.class, remap = false)
public abstract class TileEntityNetherChestMixin extends TileEntity {

    @Shadow private ExtendedItemStackHandler itemHandler;

    @Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
    private void breakBlock(World world, BlockPos pos, IBlockState state, CallbackInfo info) {
        this.invalidate();
        if (itemHandler != null && !world.isRemote) {
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                ItemStack stack = itemHandler.getStackInSlot(i);
                if (stack != null && !stack.isEmpty()) {
                    if(stack.getCount() > stack.getMaxStackSize()) {
                        ItemStack itemx1 = stack.copy();
                        itemx1.setCount(1);
                        for(int j = 0; j < stack.getCount(); j++) {
                            state.getBlock().spawnAsEntity(world, pos, itemx1.copy());
                        }
                    } else {
                        state.getBlock().spawnAsEntity(world, pos, stack);
                    }
                }
            }
        }
        world.setTileEntity(pos, null);
        info.cancel();
    }

}
