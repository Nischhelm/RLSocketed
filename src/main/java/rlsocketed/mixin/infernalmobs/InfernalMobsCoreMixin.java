package rlsocketed.mixin.infernalmobs;

import atomicstryker.infernalmobs.common.InfernalMobsCore;
import atomicstryker.infernalmobs.common.MobModifier;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlsocketed.loot.ModdedCreationContexts;
import socketed.api.util.SocketedUtil;

@Mixin(InfernalMobsCore.class)
public abstract class InfernalMobsCoreMixin {
    @Inject(
            method = "dropRandomEnchantedItems",
            at = @At(value = "INVOKE", target = "Latomicstryker/infernalmobs/common/InfernalMobsCore;enchantRandomly(Ljava/util/Random;Lnet/minecraft/item/ItemStack;II)V"),
            remap = false
    )
    private void rlsocketed_addSocketsToInfernalDrops(
            EntityLivingBase mob, MobModifier mods,
            CallbackInfo ci,
            @Local(name = "itemStack") ItemStack stack,
            @Local(name = "prefix") int tier
    ){
        SocketedUtil.addSocketsToStack(stack,
                tier == 0 ? ModdedCreationContexts.INFERNALMOB_ELITE :
                tier == 1 ? ModdedCreationContexts.INFERNALMOB_ULTRA :
                            ModdedCreationContexts.INFERNALMOB_INFERNAL
        );
    }
}
