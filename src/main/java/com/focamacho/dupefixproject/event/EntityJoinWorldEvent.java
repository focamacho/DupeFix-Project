package com.focamacho.dupefixproject.event;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityJoinWorldEvent {

    @SubscribeEvent
    public void onPlayerJoinWorld(net.minecraftforge.event.entity.EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof EntityPlayerMP) {
            EntityPlayerMP entity = (EntityPlayerMP) event.getEntity();
            if(FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getOppedPlayers().getEntry(entity.getGameProfile()) != null) {

            }
        }
    }

}
