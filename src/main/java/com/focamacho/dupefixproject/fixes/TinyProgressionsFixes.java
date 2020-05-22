package com.focamacho.dupefixproject.fixes;

import com.kashdeya.tinyprogressions.container.PouchContainer;
import com.kashdeya.tinyprogressions.inits.TechItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class TinyProgressionsFixes {
	
	//Big Pouch Dupe Fix
	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onMoveItem(PlayerTickEvent event) {
		if(event.phase == TickEvent.Phase.START && event.side == Side.SERVER) {
			EntityPlayer player = event.player;
			if(player.openContainer != null && event.player.openContainer instanceof PouchContainer) {
				if(!player.getHeldItemMainhand().getItem().equals(TechItems.pouch)) {
					player.closeScreen();
					if(player.getEntityData().hasKey("BigPouchDupeFix")) player.getEntityData().removeTag("BigPouchDupeFix");
				} else if(player.getEntityData().getBoolean("BigPouchDupeFixFirst")) {
					if(player.getEntityData().hasKey("BigPouchDupeFix")) player.getEntityData().removeTag("BigPouchDupeFix");
					List<Slot> inventorySlots = player.inventoryContainer.inventorySlots;
					List<Integer> slotsPouch = new ArrayList<Integer>();
					for(Slot slot : inventorySlots) {
						if(slot.getStack().getItem().equals(TechItems.pouch)) {
							slotsPouch.add(slot.slotNumber);
						}
					}
					int[] slotsPouchArray = slotsPouch.stream()
							.mapToInt(Integer::intValue)
							.toArray();
					player.getEntityData().setIntArray("BigPouchDupeFix", slotsPouchArray);
					player.getEntityData().setBoolean("BigPouchDupeFixFirst", false);
				}
				for(Integer slotPouch : player.getEntityData().getIntArray("BigPouchDupeFix")) {
					if(!player.inventoryContainer.getSlot(slotPouch).getStack().getItem().equals(TechItems.pouch)) {
						player.closeScreen();
						if(player.getEntityData().hasKey("BigPouchDupeFix")) player.getEntityData().removeTag("BigPouchDupeFix");
						break;
					}
				}
			} else {
				player.getEntityData().setBoolean("BigPouchDupeFixFirst", true);
			}
		}
	}
}
