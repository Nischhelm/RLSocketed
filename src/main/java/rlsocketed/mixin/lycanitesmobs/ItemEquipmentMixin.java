package rlsocketed.mixin.lycanitesmobs;

import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEquipment.class)
public abstract class ItemEquipmentMixin extends Item {
    @Redirect(
            method = "getAttributeModifiers",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/item/ItemBase;getItemAttributeModifiers(Lnet/minecraft/inventory/EntityEquipmentSlot;)Lcom/google/common/collect/Multimap;")
    )
    private Multimap<String, AttributeModifier> rlsocketed_lycanitesMobsItemEquipment_getAttributeModifiers(ItemBase instance, EntityEquipmentSlot entityEquipmentSlot, @Local(argsOnly = true) ItemStack stack){
        //LycanitesMobs calls super.getAttributeModifiers(slot) instead of super.getAttributeModifiers(slot,stack),
        // which will not get any gem attribute modifiers for atk dmg/speed/range since those are defined in there
        // the new call also will call super.getAttributeModifiers(slot) afterwards
        return super.getAttributeModifiers(entityEquipmentSlot, stack);
    }
}