package rlsocketed.gemeffects;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import com.llamalad7.mixinextras.lib.gson.annotations.SerializedName;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import socketed.Socketed;
import socketed.api.socket.gem.effect.activatable.ActivatableGemEffect;
import socketed.api.socket.gem.effect.activatable.activator.GenericActivator;
import socketed.api.socket.gem.effect.activatable.callback.IEffectCallback;
import socketed.api.socket.gem.effect.activatable.target.GenericTarget;
import socketed.api.socket.gem.effect.slot.ISlotType;

import javax.annotation.Nullable;
import java.util.List;

public class ThirstEffect extends ActivatableGemEffect {
    public static final String TYPE_NAME = "Thirst";

    @SerializedName("Amount")
    public Integer amount;

    public ThirstEffect(ISlotType slotType, GenericActivator activator, List<GenericTarget> targets, String tooltipKey, int amount) {
        super(slotType, activator, targets, tooltipKey);
        this.amount = amount;
    }

    @Override
    public String getTooltipString() {
        return I18n.format("rlsocketed.tooltip.effect.thirst." + (amount > 0 ? "pos" : "neg"), Math.abs(amount));
    }

    @Override
    public void performEffect(@Nullable IEffectCallback iEffectCallback, EntityPlayer entityPlayer, EntityLivingBase entityLivingBase) {
        IThirstCapability cap = SDCapabilities.getThirstData(entityPlayer);
        if(cap == null) return;
        cap.addThirstLevel(amount);
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
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
