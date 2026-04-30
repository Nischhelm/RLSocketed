package rlsocketed.mixin.roguelike;

import com.github.fnar.minecraft.item.mapper.BaseItemMapper1_12;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import rlsocketed.loot.ModdedCreationContexts;
import socketed.api.util.SocketedUtil;

@Mixin(BaseItemMapper1_12.class)
public abstract class BaseItemMapperMixin {
    @ModifyReturnValue(method = "enchantItem", at = @At(value = "RETURN", ordinal = 1), remap = false)
    private static ItemStack rlsocketed_addSocketsToRoguelikeLoot(ItemStack original){
        SocketedUtil.addSocketsToStack(original, ModdedCreationContexts.ROGUELIKE_LOOT);
        return original;
    }
}
