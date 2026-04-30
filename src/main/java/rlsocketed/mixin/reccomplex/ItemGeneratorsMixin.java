package rlsocketed.mixin.reccomplex;

import com.llamalad7.mixinextras.sugar.Local;
import ivorius.reccomplex.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import rlsocketed.compat.ReccomplexCompat;
import rlsocketed.loot.ModdedCreationContexts;
import socketed.api.util.SocketedUtil;

@Mixin(value = {ItemArtifactGenerator.class, ItemLootGenMultiTag.class, ItemLootGenSingleTag.class, ItemLootTableComponentTag.class})
public abstract class ItemGeneratorsMixin extends Item {
    @ModifyArg(
            method = "generateInInventory",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/items/IItemHandlerModifiable;setStackInSlot(ILnet/minecraft/item/ItemStack;)V"),
            remap = false
    )
    private ItemStack rlsocketed_addSocketsToReccomplexLoot(ItemStack stack, @Local(argsOnly = true) ItemStack generatingStack) {
        if(ReccomplexCompat.currStructure == null) return stack;
        if(stack.isEmpty()) return stack;

        ReccomplexCompat.currLootTable = ItemLootGenerationTag.lootTableKey(generatingStack);
        SocketedUtil.addSocketsToStack(stack, ModdedCreationContexts.RECCOMPLEX);
        ReccomplexCompat.currLootTable = null;

        return stack;
    }
}
