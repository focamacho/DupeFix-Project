package com.focamacho.dupefixproject.fixes;

import furgl.stupidThings.common.StupidThings;
import furgl.stupidThings.common.item.ItemImprovedHoe;
import furgl.stupidThings.common.item.ModItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Method;

public class StupidThingsFixes {


    //NOT READY YET
    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void blockBreak(PlayerInteractEvent.RightClickBlock event) {
        if(event.getEntityPlayer() != null && event.getHand() != null && event.getItemStack() != null && event.getEntityPlayer().getHeldItem(event.getHand()).getItem() instanceof ItemImprovedHoe) {
            if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockCrops && !((BlockCrops) event.getWorld().getBlockState(event.getPos()).getBlock()).canGrow(event.getWorld(), event.getPos(), event.getWorld().getBlockState(event.getPos()), false)) {
                BlockCrops crop = (BlockCrops) event.getWorld().getBlockState(event.getPos()).getBlock();
                World worldIn = event.getWorld();
                EntityPlayer playerIn = event.getEntityPlayer();
                BlockPos pos = event.getPos();
                crop.harvestBlock(worldIn, playerIn, pos, worldIn.getBlockState(pos), null, event.getItemStack());
                try {
                    Method method = crop.getClass().getDeclaredMethod("getSeed");
                    method.setAccessible(true);
                    Item seed = (Item) method.invoke(crop);
                    if (playerIn.capabilities.isCreativeMode || playerIn.inventory.clearMatchingItems(seed, -1, 1, null) == 1) {
                        playerIn.inventoryContainer.detectAndSendChanges();
                        worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), crop.getStateFromMeta(0), 0);
                        worldIn.setBlockState(pos, crop.getStateFromMeta(0), 0);
                    } else worldIn.setBlockToAir(pos);
                }
                catch(Exception e) {
                    try {
                        Method method = crop.getClass().getDeclaredMethod("func_149866_i");
                        method.setAccessible(true);
                        Item seed = (Item) method.invoke(crop);
                        if (playerIn.capabilities.isCreativeMode || playerIn.inventory.clearMatchingItems(seed, -1, 1, null) == 1) {
                            playerIn.inventoryContainer.detectAndSendChanges();
                            worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), crop.getStateFromMeta(0), 0);
                            worldIn.setBlockState(pos, crop.getStateFromMeta(0), 0);
                        }
                        else worldIn.setBlockToAir(pos);
                    }
                    catch(Exception ex) {
                        StupidThings.logger.warn("Error during onPlayerInteractEvent(): ", ex);
                    }
                }
            }
        }
    }

    private int getRangeFromItem(Item item) {
        if(item.equals(ModItems.IMPROVED_WOOD_HOE)) {
            return 0;
        } else if(item.equals(ModItems.IMPROVED_STONE_HOE)) {
            return 1;
        } else if(item.equals(ModItems.IMPROVED_IRON_HOE)) {
            return 2;
        } else if(item.equals(ModItems.IMPROVED_GOLD_HOE)) {
            return 2;
        } else if(item.equals(ModItems.IMPROVED_DIAMOND_HOE)) {
            return 3;
        }

        return 0;
    }

}
