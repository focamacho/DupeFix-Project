package com.focamacho.dupefixproject.fixes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import twilightforest.entity.EntityTFCharmEffect;

public class TwilightForestFixes {

    //Donkey Dupe Fix
    @SubscribeEvent(receiveCanceled = true)
    public void onEntityDeath(LivingDeathEvent event) {
        if(event.getEntity() != null && event.getEntity() instanceof EntityDonkey) {
            if(event.isCanceled()) {
                try {
                    boolean twilightCharm = false;
                    ItemStack item = ItemStack.EMPTY;

                    for(Entity entity : event.getEntity().getEntityWorld().getEntitiesWithinAABB(EntityTFCharmEffect.class, new AxisAlignedBB(new BlockPos(event.getEntity().posX - 10, event.getEntity().posY - 10, event.getEntity().posZ -10), new BlockPos(event.getEntity().posX + 10, event.getEntity().posY + 10, event.getEntity().posZ + 10)))) {
                        item = new ItemStack(Item.getItemById(((EntityTFCharmEffect)entity).getItemID()));
                        event.getEntity().getEntityWorld().removeEntity(entity);
                    }

                    if(!item.isEmpty()) {
                        EntityItem entityItem = new EntityItem(event.getEntity().getEntityWorld(), event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, item);
                        event.getEntity().getEntityWorld().removeEntity(event.getEntity());
                        event.getEntity().getEntityWorld().spawnEntity(entityItem);
                    }
                } catch(Exception e) {}
            }
        }
    }
}