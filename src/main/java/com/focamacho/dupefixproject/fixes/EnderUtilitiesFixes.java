package com.focamacho.dupefixproject.fixes;

import fi.dy.masa.enderutilities.EnderUtilities;
import fi.dy.masa.enderutilities.item.ItemDolly;
import fi.dy.masa.enderutilities.tileentity.TileEntityEnderUtilitiesInventory;
import fi.dy.masa.enderutilities.util.EntityUtils;
import fi.dy.masa.enderutilities.util.PositionUtils;
import fi.dy.masa.enderutilities.util.TileUtils;
import fi.dy.masa.enderutilities.util.nbt.NBTUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class EnderUtilitiesFixes {

    //Dolly Dupe Fix
    //
    //All this code is from Ender Utilities(https://github.com/maruohon/enderutilities/blob/master/src/main/java/fi/dy/masa/enderutilities/item/ItemDolly.java)
    //but with some things modified to work properly
    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if(event.getEntityPlayer() != null && event.getHand() != null && event.getEntityPlayer().getHeldItem(event.getHand()).getItem() instanceof ItemDolly){
            event.setCanceled(true);

            event.getEntityPlayer().swingArm(event.getHand());

            if (!event.getWorld().isRemote) {
                ItemStack stack = event.getEntityPlayer().getHeldItem(event.getHand());

                if (this.isCarryingBlock(stack)) {
                    this.tryPlaceDownBlock(stack, event.getEntityPlayer(), event.getWorld(), event.getPos(), event.getFace());
                }
                else {
                    this.tryPickUpBlock(stack, event.getEntityPlayer(), event.getWorld(), event.getPos(), event.getFace());
                }
            }
        }
    }

    @Nullable
    private String getCarriedBlockName(ItemStack stack) {
        NBTTagCompound tag = NBTUtils.getCompoundTag(stack, "Carrying", false);

        if (tag != null) {
            String name;

            if (tag.hasKey("DisplayName", Constants.NBT.TAG_STRING)) {
                name = tag.getString("DisplayName");
            }
            else {
                name = tag.getString("Block");
            }

            return name;
        }

        return null;
    }

    private boolean shouldTryToPickUpBlock(World world, BlockPos pos, EnumFacing side, EntityPlayer player) {
        if (world.isBlockModifiable(player, pos) == false) {
            return false;
        }

        if (player.capabilities.isCreativeMode) {
            return true;
        }

        TileEntity te = world.getTileEntity(pos);

        // Unbreakable block, player not in creative mode
        if (world.getBlockState(pos).getBlockHardness(world, pos) < 0) {
            if ((te instanceof TileEntityEnderUtilitiesInventory) == false) {
                return false;
            }

            TileEntityEnderUtilitiesInventory teinv = (TileEntityEnderUtilitiesInventory) te;
            // From the unbreakable blocks in this mod, only allow moving non-Creative mode storage blocks.
            return teinv.isCreative() == false && teinv.isMovableBy(player);
        }

        return te instanceof TileEntityEnderUtilitiesInventory || te instanceof IInventory ||
                (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side));
    }

    private boolean tryPickUpBlock(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side) {
        if (this.isCarryingBlock(stack) || this.shouldTryToPickUpBlock(world, pos, side, player) == false) {
            return false;
        }

        IBlockState state = world.getBlockState(pos);
        TileEntity te = world.getTileEntity(pos);
        String name = ForgeRegistries.BLOCKS.getKey(state.getBlock()).toString();
        int meta = state.getBlock().getMetaFromState(state);
        NBTTagCompound tagCarrying = NBTUtils.getCompoundTag(stack, "Carrying", true);
        tagCarrying.setString("Block", name);
        tagCarrying.setByte("Meta", (byte) meta);
        tagCarrying.setString("PickupFacing", EntityUtils.getHorizontalLookingDirection(player).getName());

        ItemStack stackBlock = state.getBlock().getPickBlock(state, EntityUtils.getRayTraceFromPlayer(world, player, false), world, pos, player);

        if (stackBlock.isEmpty() == false) {
            tagCarrying.setString("DisplayName", stackBlock.getDisplayName());
        }

        if (te != null) {
            NBTTagCompound tag = new NBTTagCompound();
            te.writeToNBT(tag);
            tagCarrying.setTag("te", tag);
        }

        world.removeTileEntity(pos);
        world.setBlockToAir(pos);

        return true;
    }

    private boolean tryPlaceDownBlock(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side) {
        pos = pos.offset(side);

        if (this.isCarryingBlock(stack) == false || world.isBlockModifiable(player, pos) == false) {
            return false;
        }

        NBTTagCompound tagCarrying = NBTUtils.getCompoundTag(stack, "Carrying", false);
        String name = tagCarrying.getString("Block");
        int meta = tagCarrying.getByte("Meta");
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));

        try
        {
            if (block != null && block != Blocks.AIR && world.mayPlace(block, pos, false, side, player))
            {
                @SuppressWarnings("deprecation")
                IBlockState state = block.getStateFromMeta(meta);
                EnumFacing pickupFacing = EnumFacing.byName(tagCarrying.getString("PickupFacing"));
                EnumFacing currentFacing = EntityUtils.getHorizontalLookingDirection(player);
                Rotation rotation = PositionUtils.getRotation(pickupFacing, currentFacing);
                state = state.withRotation(rotation);

                if (world.setBlockState(pos, state))
                {
                    TileEntity te = world.getTileEntity(pos);

                    if (te != null && tagCarrying.hasKey("te", Constants.NBT.TAG_COMPOUND))
                    {
                        NBTTagCompound nbt = tagCarrying.getCompoundTag("te");
                        TileUtils.createAndAddTileEntity(world, pos, nbt, rotation, Mirror.NONE);
                    }

                    NBTUtils.removeCompoundTag(stack, null, "Carrying");
                    return true;
                }
            }
        }
        catch (Exception e) {
            EnderUtilities.logger.warn("Failed to place down a block from the Dolly", e);
        }

        return false;
    }

    private boolean isCarryingBlock(ItemStack stack) {
        return NBTUtils.getCompoundTag(stack, "Carrying", false) != null;
    }

}
