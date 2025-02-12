package rlsocketed.mixin.lycanitesmobs;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import socketed.common.capabilities.socketable.CapabilitySocketableHandler;
import socketed.common.capabilities.socketable.ICapabilitySocketable;
import socketed.common.socket.GenericSocket;

@Mixin(ItemEquipment.class)
public class ItemEquipmentMixin {
    @Inject(
            method = "addEquipmentPart",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/ItemStackHelper;saveAllItems(Lnet/minecraft/nbt/NBTTagCompound;Lnet/minecraft/util/NonNullList;)Lnet/minecraft/nbt/NBTTagCompound;")
    )
    private void rlsocketed_lycanitesMobsItemEquipment_addEquipmentPart(ItemStack equipmentStack, ItemStack equipmentPartStack, int slotIndex, CallbackInfo ci){
        ICapabilitySocketable equipSockets = equipmentStack.getCapability(CapabilitySocketableHandler.CAP_SOCKETABLE, null);
        ICapabilitySocketable partSockets = equipmentPartStack.getCapability(CapabilitySocketableHandler.CAP_SOCKETABLE, null);
        if(equipSockets == null || partSockets == null) return;

        NBTTagCompound stackTags = equipmentStack.getTagCompound();
        if(stackTags == null) stackTags = new NBTTagCompound();
        NBTTagList indexList = stackTags.getTagList("SocketPartIndices", 3);

        for(GenericSocket socket : partSockets.getSockets()) {
            //Add the sockets of the toolpart to the tool
            equipSockets.addSocket(socket);
            //Save what toolpart the socket came from
            indexList.appendTag(new NBTTagInt(slotIndex));
        }

        //Saving which socket belongs to which equipment part of the tool.
        // List has length of how many sockets the tool has
        // each entry is an index pointing to a tool part in the partStackList from which the parts are retrieved in equip station
        stackTags.setTag("SocketPartIndices", indexList);
        equipmentStack.setTagCompound(stackTags);
    }

    @ModifyReturnValue(
            method = "getEquipmentPartStacks",
            at = @At(value = "RETURN"),
            remap = false
    )
    private NonNullList<ItemStack> rlsocketed_lycanitesMobsItemEquipment_getEquipmentPartStacks(NonNullList<ItemStack> partStackList, @Local(argsOnly = true) ItemStack equipmentStack){
        ICapabilitySocketable equipSockets = equipmentStack.getCapability(CapabilitySocketableHandler.CAP_SOCKETABLE, null);
        if(equipSockets == null) return partStackList;

        //Resetting all sockets of the parts
        for(ItemStack stack : partStackList){
            ICapabilitySocketable partSockets = stack.getCapability(CapabilitySocketableHandler.CAP_SOCKETABLE, null);
            if(partSockets == null) continue;
            partSockets.resetCap();
        }

        NBTTagCompound stackTags = equipmentStack.getTagCompound();
        if(stackTags == null) return partStackList;
        NBTTagList indexList = stackTags.getTagList("SocketPartIndices",3);

        //Adding the sockets where they belong
        //There should never be more stored socket indices than actual sockets, otherwise someone removed sockets from the tool somehow
        //if there are, we just ignore the remaining entries in the index list
        for(int i = 0; i < Math.min(indexList.tagCount(), equipSockets.getSocketCount()); i++){
            int partIndex = indexList.getIntAt(i);
            ICapabilitySocketable partSockets = partStackList.get(partIndex).getCapability(CapabilitySocketableHandler.CAP_SOCKETABLE, null);
            if(partSockets == null) continue;
            partSockets.addSocket(equipSockets.getSocketAt(i));
        }
        //If there are ever any remaining sockets (assumption: from adding sockets directly to the tool, not the parts), move those over to the last non empty part in list
        if(indexList.tagCount() < equipSockets.getSocketCount()){
            ItemStack lastNonEmptyPartStack = ItemStack.EMPTY;
            for(ItemStack partStack : partStackList){
                if(!partStack.isEmpty())
                    lastNonEmptyPartStack = partStack;
            }

            ICapabilitySocketable partSockets = lastNonEmptyPartStack.getCapability(CapabilitySocketableHandler.CAP_SOCKETABLE, null);
            if(partSockets == null) return partStackList;

            for(int i = indexList.tagCount(); i < equipSockets.getSocketCount(); i++)
                partSockets.addSocket(equipSockets.getSocketAt(i));
        }

        return partStackList;
    }
}