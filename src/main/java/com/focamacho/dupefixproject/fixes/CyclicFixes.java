package com.focamacho.dupefixproject.fixes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import com.lothrazar.cyclicmagic.block.workbench.BlockWorkbench;
import com.lothrazar.cyclicmagic.block.workbench.ContainerWorkBench;
import com.lothrazar.cyclicmagic.gui.container.ContainerBaseMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class CyclicFixes {

	//Workbench Dupe Fix
	@SubscribeEvent
	public void onRightClickBlock(RightClickBlock event) {
		if(event.getEntityPlayer() != null) {
			if(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockWorkbench) {
				event.getEntityPlayer().getEntityData().setInteger("CyclicWorkbenchX", event.getPos().getX());
				event.getEntityPlayer().getEntityData().setInteger("CyclicWorkbenchY", event.getPos().getY());
				event.getEntityPlayer().getEntityData().setInteger("CyclicWorkbenchZ", event.getPos().getZ());
				event.getEntityPlayer().getEntityData().setBoolean("CyclicWorkbenchDupeFix", true);
				List<EntityPlayer> players = event.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(event.getPos().getX() - 32, event.getPos().getY() - 32, event.getPos().getZ() - 32, event.getPos().getX() + 32, event.getPos().getY() + 32, event.getPos().getZ() + 32));
				for(EntityPlayer player : players) {
					if(player == event.getEntityPlayer()) continue;
					if(player.openContainer instanceof ContainerWorkBench) {
						if(player.getEntityData().getBoolean("CyclicWorkbenchDupeFix")) {
							NBTTagCompound playerData = player.getEntityData();
							if(playerData.getInteger("CyclicWorkbenchX") == event.getPos().getX() && playerData.getInteger("CyclicWorkbenchY") == event.getPos().getY() && playerData.getInteger("CyclicWorkbenchZ") == event.getPos().getZ()) {
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
	
	@SubscribeEvent
	public void onTickPlayer(PlayerTickEvent event) {
		if(event.player.openContainer != null && !(event.player.openContainer instanceof ContainerWorkBench)) {
			event.player.getEntityData().setBoolean("CyclicWorkbenchDupeFix", false);
		}
	}
}
