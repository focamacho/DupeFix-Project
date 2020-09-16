package com.focamacho.dupefixproject.mixin.tp;

import com.kashdeya.tinyprogressions.handlers.ArmorHandler;
import com.kashdeya.tinyprogressions.handlers.ConfigHandler;
import com.kashdeya.tinyprogressions.inits.TechItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(targets = "com/kashdeya/tinyprogressions/events/EventDrops", remap = false)
public class EventDropsMixin {

    @Inject(method = "onLivingDrops", at = @At("HEAD"), cancellable = true)
    private static void onLivingDrops(LivingDropsEvent event, CallbackInfo info) {
        Entity entity = event.getEntity();
        if (event.getEntity() instanceof EntityDragon && ArmorHandler.dragon_armor) {
            event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(TechItems.dragon_scale, new Random().nextInt(16))));
        } else if (event.getEntity() instanceof EntityWither && ConfigHandler.wither_rib) {
            event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(TechItems.wither_rib, new Random().nextInt(6))));
        }
        info.cancel();
    }

}
