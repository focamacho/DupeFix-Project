package com.focamacho.dupefixproject.fixes;

import crazypants.enderio.base.handler.darksteel.gui.DSUContainer;
import net.minecraft.block.Block;
import net.minecraft.inventory.ContainerRepair;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
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
	
	//Anvil Primordial Pearl Dupe Fix
	@SubscribeEvent
	public void anvilCheck(PlayerTickEvent event) {
		if(event.player.openContainer == null) return;
		if(event.player.openContainer instanceof ContainerRepair) {
			ContainerRepair container = (ContainerRepair) event.player.openContainer;
			if(container.getSlot(0).getStack().getItem().equals(ItemsTC.primordialPearl) || container.getSlot(1).getStack().getItem().equals(ItemsTC.primordialPearl)) {
				event.player.closeScreen();
				return;
			}
		}
	}

}
