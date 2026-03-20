package rlsocketed.gemeffects;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.temperature.ITemperatureCapability;
import com.llamalad7.mixinextras.lib.gson.annotations.SerializedName;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import socketed.Socketed;
import socketed.api.socket.gem.effect.GenericGemEffect;
import socketed.api.socket.gem.effect.slot.ISlotType;

public class TemperatureEffect extends GenericGemEffect {
    public static final String TYPE_NAME = "Temperature";

    @SerializedName("Amount")
    public Integer amount;

    public TemperatureEffect(ISlotType slotType, int amount) {
        super(slotType);
        this.amount = amount;
    }

    @Override
    public String getTooltip(boolean b) {
        return I18n.format("rlsocketed.tooltip.effect.temperature." +(amount > 0 ? "pos" : "neg"), Math.abs(amount));
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    @Override
    public void onEquip(EntityPlayer player, ItemStack stack) {
        ITemperatureCapability cap = SDCapabilities.getTemperatureData(player);
        if(cap == null) return;
        cap.addTemperatureLevel(amount);
    }

    @Override
    public void onUnequip(EntityPlayer player, ItemStack stack) {
        ITemperatureCapability cap = SDCapabilities.getTemperatureData(player);
        if(cap == null) return;
        cap.addTemperatureLevel(-amount);
    }

    @Override
    public boolean validate() {
        if (!super.validate()) return false;
        if (this.amount == null) {
            Socketed.LOGGER.warn("Invalid {} Effect, amount is null", this.getTypeName());
            return false;
        } else {
            return true;
        }
    }
}
