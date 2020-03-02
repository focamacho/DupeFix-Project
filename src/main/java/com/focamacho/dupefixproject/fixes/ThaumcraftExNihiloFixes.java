package com.focamacho.dupefixproject.fixes;

import exnihilocreatio.items.tools.HammerBase;
import exnihilocreatio.registries.manager.ExNihiloRegistryManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ThaumcraftExNihiloFixes {

	//Hammer Collector Enchantment Dupe Fix
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void blockBreakCheck(BlockEvent.HarvestDropsEvent event) {
		if(event.getHarvester() != null) {
			ItemStack item = event.getHarvester().getHeldItemMainhand();
			if(item.getItem() instanceof HammerBase) {
				if(item.hasTagCompound() && item.getTagCompound().hasKey("infench")) {
					if(ExNihiloRegistryManager.HAMMER_REGISTRY.isRegistered(event.getState())) {
						event.getDrops().clear();
					}
				}
			}
		}
	}
	
}
