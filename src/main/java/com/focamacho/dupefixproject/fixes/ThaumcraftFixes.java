package com.focamacho.dupefixproject.fixes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.container.ContainerArcaneWorkbench;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
import thaumcraft.common.lib.utils.InventoryUtils;

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
	
	//Collector Enchantment Fix
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void removeTag(BlockEvent.HarvestDropsEvent event) {
		if(event.getHarvester() != null) {
			ItemStack heldItem = event.getHarvester().getHeldItem(event.getHarvester().getActiveHand());
			if(heldItem.hasTagCompound() && heldItem.getTagCompound().hasKey("infench")) {
				NBTTagList nbtList = EnumInfusionEnchantment.getInfusionEnchantmentTagList(heldItem);
				for(int i = 0; i < nbtList.tagCount(); i++) {
					if(nbtList.getCompoundTagAt(0).getShort("id") == 0) {
						nbtList.removeTag(i);
						heldItem.setTagInfo("infench", nbtList);
						heldItem.getTagCompound().setBoolean("fixCollector", true);
						break;
					}
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void dropsCheck(BlockEvent.HarvestDropsEvent event) {
		if(event.getHarvester() != null) {
			ItemStack heldItem = event.getHarvester().getHeldItem(event.getHarvester().getActiveHand());
			if(heldItem != null && !heldItem.isEmpty()) {
				if(heldItem.hasTagCompound() && heldItem.getTagCompound().hasKey("fixCollector")) {
					InventoryUtils.dropHarvestsAtPos(event.getWorld(), event.getPos(), event.getDrops(), true, 10, (Entity)event.getHarvester());
		            event.getDrops().clear();
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void removeTagB(LivingDropsEvent event) {
		if(event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
			ItemStack heldItem = ((EntityPlayer)event.getSource().getTrueSource()).getHeldItem(((EntityPlayer)event.getSource().getTrueSource()).getActiveHand());
			if(heldItem != null && !heldItem.isEmpty()) {
				if(heldItem.hasTagCompound() && heldItem.getTagCompound().hasKey("infench")) {
					NBTTagList nbtList = EnumInfusionEnchantment.getInfusionEnchantmentTagList(heldItem);
					for(int i = 0; i < nbtList.tagCount(); i++) {
						if(nbtList.getCompoundTagAt(0).getShort("id") == 0) {
							nbtList.removeTag(i);
							heldItem.setTagInfo("infench", nbtList);
							heldItem.getTagCompound().setBoolean("fixCollector", true);
							break;
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void livingDrops(LivingDropsEvent event) {
		if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
			ItemStack heldItem = ((EntityPlayer)event.getSource().getTrueSource()).getHeldItem(((EntityPlayer)event.getSource().getTrueSource()).getActiveHand());
			if (heldItem != null && !heldItem.isEmpty()) {
				if (heldItem.hasTagCompound() && heldItem.getTagCompound().hasKey("fixCollector")) {
					List<ItemStack> itemsDrop = new ArrayList<ItemStack>();
					for(EntityItem drop : event.getDrops()) {
						itemsDrop.add(drop.getItem());
					}
					InventoryUtils.dropHarvestsAtPos(event.getEntity().world, new BlockPos(event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ), itemsDrop, true, 10, event.getSource().getTrueSource());
					event.getDrops().clear();
				}
			} 
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltipFix(ItemTooltipEvent event) {
		if(event.getItemStack().hasTagCompound() && event.getItemStack().getTagCompound().hasKey("fixCollector")) {
        	event.getToolTip().add(1, TextFormatting.GOLD + I18n.translateToLocal("enchantment.infusion.COLLECTOR"));
		}
	}
}
