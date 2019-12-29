package com.focamacho.dupefixproject.fixes;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.tools.common.block.BlockToolForge;
import slimeknights.tconstruct.tools.common.inventory.ContainerToolForge;

public class TConstructFixes {

	//Tool Forge Dupe Fix
	@SubscribeEvent
	public void onRightClickBlock(RightClickBlock event) {
		if(event.getEntityPlayer() != null) {
			if(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockToolForge) {
				List<EntityPlayer> players = event.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(event.getPos().getX() - 32, event.getPos().getY() - 32, event.getPos().getZ() - 32, event.getPos().getX() + 32, event.getPos().getY() + 32, event.getPos().getZ() + 32));
				for(EntityPlayer player : players) {
					if(player.openContainer instanceof ContainerToolForge) {
						ContainerToolForge container = (ContainerToolForge) player.openContainer;
						if(container.getTile().getPos().equals(event.getPos())) {
							event.setCanceled(true);
							event.getEntityPlayer().sendMessage(new TextComponentString("Someone is already using this."));
							break;
						}
					}
				}
			}
		}
	}
}
