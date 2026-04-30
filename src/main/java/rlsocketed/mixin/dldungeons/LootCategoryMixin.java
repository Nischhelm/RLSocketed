package rlsocketed.mixin.dldungeons;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import jaredbgreat.dldungeons.pieces.chests.LootCategory;
import jaredbgreat.dldungeons.pieces.chests.LootResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import rlsocketed.loot.ModdedCreationContexts;
import socketed.api.util.SocketedUtil;

@Mixin(LootCategory.class)
public abstract class LootCategoryMixin {
    @ModifyReturnValue(
            method = "getLoot",
            at = {
                    @At(value = "RETURN", ordinal = 0), // enchanted gear
                    @At(value = "RETURN", ordinal = 3) // special items
            },
            remap = false
    )
    private LootResult rlsocketed_addSocketsToSpecialDoomlikeLoot(LootResult original){
        SocketedUtil.addSocketsToStack(original.getLoot(), ModdedCreationContexts.DLDUNGEON_LOOT_SPECIAL);
        return original;
    }

    @ModifyReturnValue(
            method = "getLoot",
            at = @At(value = "RETURN", ordinal = 1), //low lvl enchanted gear
            remap = false
    )
    private LootResult rlsocketed_addSocketsToDoomlikeLoot(LootResult original){
        SocketedUtil.addSocketsToStack(original.getLoot(), ModdedCreationContexts.DLDUNGEON_LOOT);
        return original;
    }
}
