package rlsocketed.gemeffects;

import com.elenai.elenaidodge.api.DodgeEvent;
import com.google.gson.annotations.SerializedName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import socketed.Socketed;
import socketed.api.socket.gem.effect.activatable.ActivatableGemEffect;
import socketed.api.socket.gem.effect.activatable.activator.GenericActivator;
import socketed.api.socket.gem.effect.activatable.callback.GenericEventCallback;
import socketed.api.socket.gem.effect.activatable.callback.IEffectCallback;
import socketed.api.socket.gem.effect.activatable.target.GenericTarget;
import socketed.api.socket.gem.effect.slot.ISlotType;

import javax.annotation.Nullable;
import java.util.List;

public class DodgeForceEffect extends ActivatableGemEffect {
    public static final String TYPE_NAME = "Dodge Force";
    @SerializedName("Force Multiplier")
    private final Double forceMultiplier;

    public DodgeForceEffect(ISlotType slotType, GenericActivator activator, List<GenericTarget> targets, double forceMultiplier, String tooltipKey) {
        super(slotType, activator, targets, tooltipKey);
        this.forceMultiplier = forceMultiplier;
    }

    @Override
    public void performEffect(@Nullable IEffectCallback callback, EntityPlayer playerSource, EntityLivingBase effectTarget) {
        if (playerSource != null && effectTarget != null && !playerSource.world.isRemote && callback instanceof GenericEventCallback ) {
            if(((GenericEventCallback<?>) callback).getEvent() instanceof DodgeEvent.ServerDodgeEvent) {
                DodgeEvent.ServerDodgeEvent event = (DodgeEvent.ServerDodgeEvent) ((GenericEventCallback<?>) callback).getEvent();
                event.setForce(event.getForce() * forceMultiplier);
            }
        }
    }

    //TODO
    @SideOnly(Side.CLIENT)
    @Override
    public String getTooltipString() {
        return "";
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    /**
     * Force Multiplier: required
     */
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
