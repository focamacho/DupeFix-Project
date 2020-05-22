package com.focamacho.dupefixproject.fixes;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import netherchest.common.Content;
import netherchest.common.blocks.BlockNetherChest;

import java.util.ArrayList;
import java.util.List;

public class NetherChestFixes {

	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void blockBreak(BreakEvent event) {
		if(event.getState() != null && event.getState().getBlock() instanceof BlockNetherChest && event.getWorld().getTileEntity(event.getPos()) != null) {
			NBTTagCompound nbt = new NBTTagCompound();
			event.getWorld().getTileEntity(event.getPos()).writeToNBT(nbt);
			
			NonNullList<ItemStack> list = NonNullList.withSize(27, ItemStack.EMPTY);
			ItemStackHelper.loadAllItems(nbt.getCompoundTag("Items"), list);
			
			List<ItemStack> toDrop = new ArrayList<ItemStack>();
			
			for(ItemStack item : list) {
				if(!item.isEmpty() && !item.isStackable() && item.getCount() > 1) {
					ItemStack itemx1 = item.copy();
					itemx1.setCount(1);
					
					for(int i = 0; i < item.getCount(); i++) {
						toDrop.add(itemx1.copy());
					}
				} else if(!item.isEmpty()) {
					toDrop.add(item);
				}
			}
			
			ItemStack chest = new ItemStack(Content.NETHER_CHEST_ITEM);
			event.getWorld().spawnEntity(new EntityItem(event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), chest));
		
			for(ItemStack drop : toDrop) {
				event.getWorld().spawnEntity(new EntityItem(event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), drop));
			}
			
			event.setCanceled(true);
			event.getWorld().removeTileEntity(event.getPos());
			event.getWorld().setBlockToAir(event.getPos());
		}
	}
}
