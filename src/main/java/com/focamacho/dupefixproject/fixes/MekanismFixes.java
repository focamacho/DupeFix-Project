package com.focamacho.dupefixproject.fixes;

import com.google.common.collect.Lists;
import mekanism.common.MekanismBlocks;
import mekanism.common.inventory.container.ContainerPersonalChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class MekanismFixes {

	//Personal Chest Dupe Fix
	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onMoveItem(PlayerTickEvent event) {
		if(event.phase == TickEvent.Phase.START && event.side == Side.SERVER) {
			EntityPlayer player = event.player;
			if(player.openContainer != null && event.player.openContainer instanceof ContainerPersonalChest) {
				if(!ItemStack.areItemsEqual(player.getHeldItemMainhand(), new ItemStack(MekanismBlocks.MachineBlock, 1, 13))) {
					player.closeScreen();
					if(player.getEntityData().hasKey("PersonalChestDupeFix")) player.getEntityData().removeTag("PersonalChestDupeFix");
				} else if(player.getEntityData().getBoolean("PersonalChestDupeFixFirst")) {
					if(player.getEntityData().hasKey("PersonalChestDupeFix")) player.getEntityData().removeTag("PersonalChestDupeFix");
					List<Slot> inventorySlots = player.inventoryContainer.inventorySlots;
					List<Integer> slotsChest = new ArrayList<Integer>();
					for(Slot slot : inventorySlots) {
						if(ItemStack.areItemsEqual(slot.getStack(), new ItemStack(MekanismBlocks.MachineBlock, 1, 13))) {
							slotsChest.add(slot.slotNumber);
						}
					}
					int[] slotsChestArray = slotsChest.stream()
							.mapToInt(Integer::intValue)
							.toArray();
					player.getEntityData().setIntArray("PersonalChestDupeFix", slotsChestArray);
					player.getEntityData().setBoolean("PersonalChestDupeFixFirst", false);
				}
				for(Integer slotPouch : player.getEntityData().getIntArray("PersonalChestDupeFix")) {
					if(!ItemStack.areItemsEqual(player.inventoryContainer.getSlot(slotPouch).getStack(), new ItemStack(MekanismBlocks.MachineBlock, 1, 13))) {
						player.closeScreen();
						if(player.getEntityData().hasKey("PersonalChestDupeFix")) player.getEntityData().removeTag("PersonalChestDupeFix");
						break;
					}
				}
			} else {
				player.getEntityData().setBoolean("PersonalChestDupeFixFirst", true);
			}
		}
	}

	//Bin Recipe Dupe Fix
	public static void init() {
		ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
		ArrayList<IRecipe> recipes = Lists.newArrayList(recipeRegistry.getValuesCollection());
		for(IRecipe recipe : recipes) {
			if(recipe instanceof mekanism.common.recipe.BinRecipe) {
				recipeRegistry.remove(recipe.getRegistryName());
			}
		}
	}

	//I tried
	/*
	@SubscribeEvent
	public void unloadChunkFix(BlockEvent.NeighborNotifyEvent event) {
		if(event.getState() != null && event.getState().getBlock() instanceof BlockTransmitter) {
			if(event.getWorld().getTileEntity(event.getPos()) != null && event.getWorld().getTileEntity(event.getPos()) instanceof TileEntityLogisticalTransporter) {
				NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);

				TileEntityLogisticalTransporter tileEntity = (TileEntityLogisticalTransporter) event.getWorld().getTileEntity(event.getPos());
				NBTTagCompound nbt = new NBTTagCompound();

				tileEntity.writeToNBT(nbt);
				ItemStackHelper.loadAllItems(nbt, items);
				for(EntityItem item : event.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(event.getPos()))) {
					if(items.contains(item.getItem())) event.getWorld().removeEntity(item);
				}
			}
		}
	}*/
}
