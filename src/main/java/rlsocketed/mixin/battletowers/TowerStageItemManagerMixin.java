package rlsocketed.mixin.battletowers;

import atomicstryker.battletowers.common.TowerStageItemManager;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import rlsocketed.loot.ModdedCreationContexts;
import socketed.api.util.SocketedUtil;

@Mixin(TowerStageItemManager.class)
public abstract class TowerStageItemManagerMixin {
    // Not hooking into ChestHook loot tables, those are handled by Socketed's LOOT
    @ModifyArg(
            method = "getStageItemStacks",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"),
            remap = false
    )
    private Object rlsocketed_addSocketsToBattleTowerLoot(Object stack) {
        if(stack instanceof ItemStack) SocketedUtil.addSocketsToStack((ItemStack) stack, ModdedCreationContexts.BATTLETOWER_LOOT);
        return stack;
    }
}
