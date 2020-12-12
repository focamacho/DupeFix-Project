package com.focamacho.dupefixproject.event;

import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.item.ItemShears;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class VanillaFixes {

    //Cow Dupe Fix
    @SubscribeEvent
    public void onSheared(PlayerInteractEvent.EntityInteract event) {
        if(event.getTarget() instanceof EntityMooshroom) {
            if(event.getItemStack().getItem() instanceof ItemShears) {
                if(event.getTarget().isDead) event.setCanceled(true);
            }
        }
    }

    //Donkey Dupe Fix
    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        BlockPos playerPos = event.player.getPosition();
        event.player.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(playerPos.getX() - 32, playerPos.getY() - 32, playerPos.getZ() - 32, playerPos.getX() + 32, playerPos.getY() + 32, playerPos.getZ() + 32)).forEach(player -> {
            if(player.openContainer instanceof ContainerHorseInventory) {
                player.closeScreen();
            }
        });
    }

}
