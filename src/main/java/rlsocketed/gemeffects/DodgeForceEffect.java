package rlsocketed.gemeffects;

import com.elenai.elenaidodge.api.DodgeEvent;
import com.google.gson.annotations.SerializedName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import rlsocketed.callback.GenericEventCallback;
import socketed.Socketed;
import socketed.common.socket.gem.effect.activatable.ActivatableGemEffect;
import socketed.common.socket.gem.effect.activatable.activator.GenericActivator;
import socketed.common.socket.gem.effect.activatable.callback.IEffectCallback;
import socketed.common.socket.gem.effect.activatable.target.GenericTarget;
import socketed.common.socket.gem.effect.slot.ISlotType;

import javax.annotation.Nullable;
import java.util.List;

public class DodgeForceEffect extends ActivatableGemEffect {
    public static final String TYPE_NAME = "Dodge Force";
    @SerializedName("Force Multiplier")
    private final Double forceMultiplier;

    public DodgeForceEffect(ISlotType slotType, GenericActivator activator, List<GenericTarget> targets, double forceMultiplier) {
        super(slotType, activator, targets);
        this.forceMultiplier = forceMultiplier;
    }

    @Override
    public void performEffect(@Nullable IEffectCallback callback, EntityPlayer playerSource, EntityLivingBase effectTarget) {
        if (playerSource != null && effectTarget != null && !playerSource.world.isRemote && callback instanceof GenericEventCallback ) {
            GenericEventCallback eventCallback = (GenericEventCallback) callback;
            if(eventCallback.event instanceof DodgeEvent.ServerDodgeEvent) {
                DodgeEvent.ServerDodgeEvent event = (DodgeEvent.ServerDodgeEvent) eventCallback.event;
                event.setForce(event.getForce() * forceMultiplier);
            }
        }
    }

    @Override
    public String getTooltipString(boolean b) {
        return "";
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    public boolean validate() {
        if (super.validate()) {
            if (this.forceMultiplier == null) {
                Socketed.LOGGER.warn("Invalid " + this.getTypeName() + " Effect, force multiplier must be defined");
            } else {
                return true;
            }
        }

        return false;
    }
}
