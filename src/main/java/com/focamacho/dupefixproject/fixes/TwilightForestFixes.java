package com.focamacho.dupefixproject.fixes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.entity.EntityTFCharmEffect;

public class TwilightForestFixes {

    //Donkey Dupe Fix
    @SideOnly(Side.SERVER)
    @SubscribeEvent(receiveCanceled = true)
    public void onEntityDeath(LivingDeathEvent event) {
        if(event.isCanceled()) {
            if (event.getEntity() != null && (event.getEntity() instanceof EntityDonkey || event.getEntity() instanceof EntityMule || event.getEntity() instanceof EntityLlama)) {
                try {
                    ItemStack item = ItemStack.EMPTY;

                    for (Entity entity : event.getEntity().getEntityWorld().getEntitiesWithinAABB(EntityTFCharmEffect.class, new AxisAlignedBB(new BlockPos(event.getEntity().posX - 10, event.getEntity().posY - 10, event.getEntity().posZ - 10), new BlockPos(event.getEntity().posX + 10, event.getEntity().posY + 10, event.getEntity().posZ + 10)))) {
                        item = new ItemStack(Item.getItemById(((EntityTFCharmEffect) entity).getItemID()));
                        event.getEntity().getEntityWorld().removeEntity(entity);
                    }

                    if (!item.isEmpty()) {
                        EntityItem entityItem = new EntityItem(event.getEntity().getEntityWorld(), event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, item);
                        event.getEntity().getEntityWorld().removeEntity(event.getEntity());
                        event.getEntity().getEntityWorld().spawnEntity(entityItem);
                    }
                } catch (Exception e) { }
            }
        }
    }
}