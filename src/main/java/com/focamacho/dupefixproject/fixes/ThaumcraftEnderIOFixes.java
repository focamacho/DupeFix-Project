package com.focamacho.dupefixproject.fixes;

import crazypants.enderio.base.handler.darksteel.gui.DSUContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import thaumcraft.api.items.ItemsTC;

public class ThaumcraftEnderIOFixes {

	//Primordial Pearl Dupe Fix EnderIO Anvils
	@SubscribeEvent
	public void anvilCheck(PlayerTickEvent event) {
		if(event.player.openContainer == null) return;
		if(event.player.openContainer instanceof DSUContainer) {
			DSUContainer container = (DSUContainer) event.player.openContainer;
			if(container.getSlot(0).getStack().getItem().equals(ItemsTC.primordialPearl) || container.getSlot(1).getStack().getItem().equals(ItemsTC.primordialPearl)) {
				event.player.closeScreen();
				return;
			}
		}
	}
}
