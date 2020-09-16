package com.focamacho.dupefixproject.event;

import com.focamacho.dupefixproject.util.LoadedFixes;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.item.ItemShears;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.ArrayList;
import java.util.List;

public class VanillaFixes {

    //Missing Fixes Message
    @SubscribeEvent
    public void onPlayerJoinWorld(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.player instanceof EntityPlayerMP) {
            EntityPlayerMP entity = (EntityPlayerMP) event.player;
            if(entity.canUseCommand(2, "")) {
                String modFixesNotLoaded = LoadedFixes.getModFixesNotLoaded();
                if(!modFixesNotLoaded.isEmpty()) {
                    List<TextComponentString> message = new ArrayList<>();
                    message.add(new TextComponentString("§bDupeFix Project §cfailed to load some fixes."));
                    message.add(new TextComponentString("§cThe following mods have their fixes not loaded:"));
                    message.add(new TextComponentString("§4" + modFixesNotLoaded));
                    message.add(new TextComponentString("§cVisit the mod wiki to learn how to configure it."));

                    TextComponentString clickableUrl = new TextComponentString("§bWiki: §ohttps://github.com/Focamacho/DupeFix-Project/wiki");
                    clickableUrl.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Focamacho/DupeFix-Project/wiki"));
                    message.add(clickableUrl);

                    message.forEach(entity::sendMessage);
                }
            }
        }
    }

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
