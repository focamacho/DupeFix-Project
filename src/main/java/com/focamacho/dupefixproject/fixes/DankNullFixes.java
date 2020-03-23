package com.focamacho.dupefixproject.fixes;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import p455w0rd.danknull.container.ContainerDankNull;
import p455w0rd.danknull.container.ContainerDankNullDock;
import p455w0rd.danknull.items.ItemDankNull;

public class DankNullFixes {

    @SubscribeEvent
    public void TickEvent(final TickEvent.PlayerTickEvent e) {

        if (e.player.openContainer instanceof ContainerDankNull && !(e.player.openContainer instanceof ContainerDankNullDock)) {
            if (!(e.player.getHeldItemMainhand().getItem() instanceof ItemDankNull)) {
                e.player.closeScreen();
            }
        }
    }
}
