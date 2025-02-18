package rlsocketed.mixin.lycanitesmobs;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.container.EquipmentForgeContainer;
import com.lycanitesmobs.core.container.EquipmentForgeSlot;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import com.lycanitesmobs.core.tileentity.TileEntityEquipmentForge;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlsocketed.RLSocketed;
import socketed.common.capabilities.socketable.CapabilitySocketableHandler;
import socketed.common.capabilities.socketable.ICapabilitySocketable;
import socketed.common.socket.GenericSocket;

@Mixin(EquipmentForgeContainer.class)
public abstract class EquipmentForgeContainerMixin {
    @Shadow(remap = false) public TileEntityEquipmentForge equipmentForge;

    //TODO: combining 3 tool parts with 1 diamond each gives a tool with both the diamond effects AND the rune word, even though the sockets are NOT set to overridden
    // For whatever reason disassembly runs every tick on client side

    @Redirect(
            method = "onEquipmentPartSlotChanged",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/item/equipment/ItemEquipment;addEquipmentPart(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;I)V"),
            remap = false
    )
    private void rlsocketed_lycanitesMobsItemEquipment_addEquipmentPart(ItemEquipment instance, ItemStack equipmentStack, ItemStack equipmentPartStack, int slotIndex){
        //Default behavior
        instance.addEquipmentPart(equipmentStack, equipmentPartStack, slotIndex);

        //if(this.equipmentForge.getWorld().isRemote) return;

        //assemble lyca tool from parts
        ICapabilitySocketable equipSockets = equipmentStack.getCapability(CapabilitySocketableHandler.CAP_SOCKETABLE, null);
        ICapabilitySocketable partSockets = equipmentPartStack.getCapability(CapabilitySocketableHandler.CAP_SOCKETABLE, null);
        if(equipSockets == null || partSockets == null) return;

        NBTTagCompound stackTags = equipmentStack.getTagCompound();
        if(stackTags == null) stackTags = new NBTTagCompound();
        NBTTagList indexList = stackTags.getTagList("SocketPartIndices", 3); //creates a list if it doesn't exist yet

        for(GenericSocket socket : partSockets.getSockets()) {
            //Add the sockets of the toolpart to the tool
            equipSockets.addSocket(socket);
            //Save what tool part the socket came from
            indexList.appendTag(new NBTTagInt(slotIndex));
        }

        //Saving which socket belongs to which equipment part of the tool.
        // List has length of how many sockets the tool has
        // each entry is an index pointing to a tool part in the partStackList from which the parts are retrieved in equip station
        stackTags.setTag("SocketPartIndices", indexList);
        equipmentStack.setTagCompound(stackTags);
    }

    @ModifyExpressionValue(
            method = "onEquipmentPieceSlotChanged",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/item/equipment/ItemEquipment;getEquipmentPartStacks(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/util/NonNullList;"),
            remap = false
    )
    private NonNullList<ItemStack> rlsocketed_lycanitesMobsEquipmentForgeContainer_onEquipmentPieceSlotChanged(NonNullList<ItemStack> partStackList, @Local(argsOnly = true) EquipmentForgeSlot slotEquipment){
        //Need to do this instead of just using the stored part stacks from the tools NBT, bc the sockets/gems in the tool might have gotten changed after it got created

        //Serverside only
        //if(this.equipmentForge.getWorld().isRemote) return partStackList;

        ItemStack equipmentStack = slotEquipment.getStack();
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
            //todo: maybe a problem to not have a copy here?
            // since on part.addSocket the socket inside the tool to be disassembled would get manipulated via part.refreshCombinations
            // only a problem if tool parts can get more than one socket though
            partSockets.addSocket(equipSockets.getSocketAt(i));
        }
        //If there are ever any remaining sockets (assumption: from adding sockets directly to the tool, not the parts), move those over to the last non-empty part in list
        if(indexList.tagCount() < equipSockets.getSocketCount()){
            ItemStack lastNonEmptyPartStack = ItemStack.EMPTY;
            for(ItemStack partStack : partStackList){
                if(!partStack.isEmpty())
                    lastNonEmptyPartStack = partStack;
            }

            ICapabilitySocketable partSockets = lastNonEmptyPartStack.getCapability(CapabilitySocketableHandler.CAP_SOCKETABLE, null);
            if(partSockets == null) return partStackList;

            for(int i = indexList.tagCount(); i < equipSockets.getSocketCount(); i++) {
                //todo: maybe a problem to not have a copy here?
                partSockets.addSocket(equipSockets.getSocketAt(i));
            }
        }

        return partStackList;
    }
}