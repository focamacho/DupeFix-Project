package com.focamacho.dupefixproject.fixes;

import cazador.furnaceoverhaul.blocks.BlockIronFurnace;
import cazador.furnaceoverhaul.blocks.BlockTranslucentFurnace;
import cazador.furnaceoverhaul.inventory.ContainerFO;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class FurnaceOverhaulFixes {

	//Furnace Break Dupe Fix
	@SubscribeEvent
	public void onBreakBlock(BlockEvent.BreakEvent event) {
		if(event.getPlayer() != null) {
			Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
			if(block instanceof BlockIronFurnace || block instanceof BlockTranslucentFurnace) {
				List<EntityPlayer> players = event.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(event.getPos().getX() - 32, event.getPos().getY() - 32, event.getPos().getZ() - 32, event.getPos().getX() + 32, event.getPos().getY() + 32, event.getPos().getZ() + 32));
				for(EntityPlayer player : players) {
					if(player.openContainer instanceof ContainerFO) {
						player.closeScreen();
					}
				}
			}
		}
	}
}
