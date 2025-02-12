package rlsocketed.mixin.lycanitesmobs;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.item.equipment.ItemEquipmentPart;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import socketed.common.capabilities.socketable.CapabilitySocketableHandler;
import socketed.common.capabilities.socketable.ICapabilitySocketable;
import socketed.common.socket.TieredSocket;

@Mixin(BaseCreatureEntity.class)
public class BaseCreatureEntityMixin {
    @ModifyArg(
            method = "dropFewItems",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;dropItem(Lnet/minecraft/item/ItemStack;)V", remap = false)
    )
    private ItemStack rlsocketed_lycanitesMobsBaseCreatureEntity_dropFewItems(ItemStack itemStack){
        if(itemStack.getItem() instanceof ItemEquipmentPart){
            ICapabilitySocketable sockets = itemStack.getCapability(CapabilitySocketableHandler.CAP_SOCKETABLE, null);
            //Always give lyca tool parts one tier 2 socket
            if(sockets != null) sockets.addSocket(new TieredSocket(2));
        }

        return itemStack;
    }
}