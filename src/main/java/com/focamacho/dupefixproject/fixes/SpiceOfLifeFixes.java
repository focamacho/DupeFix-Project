package com.focamacho.dupefixproject.fixes;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import squeek.spiceoflife.ModContent;
import squeek.spiceoflife.inventory.ContainerFoodContainer;

public class SpiceOfLifeFixes {
	
	//Lunch Bag & Lunch Box Dupe Fix
	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void detectItemDrop(PlayerTickEvent event) {
		if(event.phase == TickEvent.Phase.START && event.side == Side.SERVER) {
			if(event.player.openContainer != null && event.player.openContainer instanceof ContainerFoodContainer) {
				if(!event.player.getHeldItemMainhand().getItem().equals(ModContent.lunchBag) && !event.player.getHeldItemMainhand().getItem().equals(ModContent.lunchBox)) {
					event.player.closeScreen();
				}
			}
		}
	}
	
}
