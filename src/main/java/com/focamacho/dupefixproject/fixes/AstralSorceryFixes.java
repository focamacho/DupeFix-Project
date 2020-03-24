package com.focamacho.dupefixproject.fixes;

import hellfirepvp.astralsorcery.common.container.ContainerAltarBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class AstralSorceryFixes {

	//Altar Dupe Fix
	@SubscribeEvent
	public void onTickPlayer(PlayerTickEvent event) {
		if(event.player.openContainer != null && event.player.openContainer instanceof ContainerAltarBase) {
			ContainerAltarBase container = (ContainerAltarBase) event.player.openContainer;
			BlockPos altarPos = container.tileAltar.getPos();
			if(!(event.player.getDistanceSq(altarPos) <= 64.0D)) event.player.closeScreen();
		}
	}
	
}
