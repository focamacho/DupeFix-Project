package com.focamacho.dupefixproject.mixin.arcanearchives;

import com.aranaira.arcanearchives.inventory.ContainerGemCuttersTable;
import com.aranaira.arcanearchives.tileentities.GemCuttersTableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "com/aranaira/arcanearchives/inventory/ContainerGemCuttersTable", remap = false)
public abstract class ContainerGemCuttersTableMixin extends Container {
	
	public GemCuttersTableTileEntity tile;
	
    public ContainerGemCuttersTableMixin(IItemHandlerModifiable tileInventory, GemCuttersTableTileEntity tile, EntityPlayer player) {
        super();
    }

	@Override
    public boolean canInteractWith(EntityPlayer player) {
		BlockPos pos = this.tile.getPos();
		if (this.tile.getWorld().getTileEntity(pos) != this.tile) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
		}
	}

}