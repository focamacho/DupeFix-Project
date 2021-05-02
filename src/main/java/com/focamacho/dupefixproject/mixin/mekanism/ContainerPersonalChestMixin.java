package com.focamacho.dupefixproject.mixin.mekanism;

import mekanism.common.block.states.BlockStateMachine;
import mekanism.common.inventory.InventoryPersonalChest;
import mekanism.common.inventory.container.ContainerMekanism;
import mekanism.common.inventory.container.ContainerPersonalChest;
import mekanism.common.tile.TileEntityPersonalChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ContainerPersonalChest.class, remap = false)
public abstract class ContainerPersonalChestMixin extends ContainerMekanism<TileEntityPersonalChest> {

    @Shadow private boolean isBlock;

    @Shadow private IInventory itemInventory;

    protected ContainerPersonalChestMixin(TileEntityPersonalChest tileEntityPersonalChest, InventoryPlayer inventory) {
        super(tileEntityPersonalChest, inventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if(isBlock) return super.func_75145_c(player);

        final ItemStack currentItem = player.getHeldItemMainhand();
        if (itemInventory instanceof InventoryPersonalChest) {
            final ItemStack stack = ((InventoryPersonalChest) itemInventory).getStack();
            return super.func_75145_c(player) && !stack.isEmpty() && currentItem == stack && BlockStateMachine.MachineType.get(stack) == BlockStateMachine.MachineType.PERSONAL_CHEST;
        }
        return false;
    }

}
