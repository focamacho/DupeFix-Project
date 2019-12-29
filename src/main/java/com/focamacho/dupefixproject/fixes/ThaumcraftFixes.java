package com.focamacho.dupefixproject.fixes;

import net.minecraft.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.container.ContainerArcaneWorkbench;

public class ThaumcraftFixes {

	//Arcane Workbench Dupe Fix
	@SubscribeEvent
	public void onRightClickBlock(RightClickBlock event) {
		Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
		if(block != null && block.isEqualTo(block, BlocksTC.arcaneWorkbench)) {
			if(event.getEntityPlayer() != null) {
				if(event.getEntityPlayer().openContainer instanceof ContainerArcaneWorkbench) {
					event.setCanceled(true);
				}
			}
		}
	}
}
